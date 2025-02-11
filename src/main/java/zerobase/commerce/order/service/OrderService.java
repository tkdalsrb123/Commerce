package zerobase.commerce.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  @Transactional
  public Order createOrder(Request orderDtoRequest, String username) {
    User user = validateBuyerAuthority(username);
    Product product = validateProductAuthority(orderDtoRequest.getProductId(), orderDtoRequest.getQuantity());

    product.setStock(product.getStock() - orderDtoRequest.getQuantity());

    Order order = orderRepository.save(Order.builder()
        .product(product)
        .user(user)
        .quantity(orderDtoRequest.getQuantity())
        .totalPrice(product.getPrice() * orderDtoRequest.getQuantity())
        .build());

    ProductStats stats = productStatsRepository.findById(product.getId()).orElseThrow(() -> new RuntimeException("집계 정보가 없습니다."));
    stats.updateSales(orderDtoRequest.getQuantity());
    productStatsRepository.save(stats);

    return order;
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
}
