package zerobase.commerce.order.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.cart.domain.Cart;
import zerobase.commerce.cart.repository.CartRepository;
import zerobase.commerce.order.domain.Order;
import zerobase.commerce.order.dto.OrderDto.Request;
import zerobase.commerce.order.repository.OrderRepository;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.domain.ProductStats;
import zerobase.commerce.product.repository.ProductRepository;
import zerobase.commerce.product.repository.ProductStatsRepository;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.repository.UserRepository;
import zerobase.commerce.user.type.UserType;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final ProductStatsRepository productStatsRepository;
  private final CartRepository cartRepository;

  @Transactional
  public Order createOrder(Request orderDtoRequest, String username) {
    User user = validateBuyerAuthority(username);
    Product product = validateProductAuthority(orderDtoRequest.getProductId(),
        orderDtoRequest.getQuantity());

    ProductStats stats = productStatsRepository.findById(product.getId())
        .orElseThrow(() -> new RuntimeException("집계 정보가 없습니다."));
    stats.updateSales(orderDtoRequest.getQuantity());
    productStatsRepository.save(stats);

    product.setStock(product.getStock() - orderDtoRequest.getQuantity());

    return orderRepository.save(Order.builder()
        .product(product)
        .user(user)
        .quantity(orderDtoRequest.getQuantity())
        .totalPrice(product.getPrice() * orderDtoRequest.getQuantity())
        .build());
  }

  private User validateBuyerAuthority(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    if (user.getRole() != UserType.ROLE_BUYER) {
      throw new RuntimeException("권한이 없습니다.");
    }

    return user;
  }

  private Product validateProductAuthority(Long productId, int quantity) {

    Product product = productRepository.findProductById(productId)
        .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));

    if (product.getStock() < quantity) {
      throw new RuntimeException("재고가 부족합니다.");
    }

    return product;
  }

  @Transactional
  public List<Order> orderFromCart(String username) {
    User user = validateBuyerAuthority(username);

    List<Cart> cartProducts = cartRepository.findAllByUser(user);
    if (cartProducts.isEmpty()) {
      throw new RuntimeException("장바구니가 비어 있습니다.");
    }

    List<Long> productIds = cartProducts.stream()
        .map(cart -> cart.getProduct().getId())
        .toList();
    List<Product> products = productRepository.findAllById(productIds);

    List<Order> orders = new ArrayList<>();
    Map<Long, Product> productMap = products.stream()
        .collect(Collectors.toMap(Product::getId, product -> product));

    for (Cart cart : cartProducts) {
      Product product = productMap.get(cart.getProduct().getId());
      if (product.getStock() < cart.getQuantity()) {
        throw new RuntimeException("재고가 부족한 상품이 있습니다: " + product.getName());
      }

      product.setStock(product.getStock() - cart.getQuantity());

      Order order = Order.builder().user(user).product(product).quantity(cart.getQuantity())
          .totalPrice(product.getPrice() * cart.getQuantity()).build();
      orders.add(order);

    }

    List<ProductStats> statsToUpdate = productStatsRepository.findAllById(productIds);
    Map<Long, ProductStats> statsMap = statsToUpdate.stream()
        .collect(Collectors.toMap(ProductStats::getProductId, stats -> stats));

    for (Cart cart : cartProducts) {
      ProductStats stats = statsMap.get(cart.getProduct().getId());
      stats.updateSales(cart.getQuantity());
    }


    orderRepository.saveAll(orders);
    productRepository.saveAll(products);

    cartRepository.deleteAll(cartProducts);

    return orders;
  }
}
