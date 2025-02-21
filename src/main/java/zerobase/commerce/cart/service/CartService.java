package zerobase.commerce.cart.service;

import aj.org.objectweb.asm.commons.Remapper;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.cart.domain.Cart;
import zerobase.commerce.cart.repository.CartRepository;
import zerobase.commerce.exception.CustomException;
import zerobase.commerce.exception.ErrorCode;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.repository.ProductRepository;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CartService {

  private final CartRepository cartRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  @Transactional
  public void addToCart(Long productId, int quantity, String username) {
    Pair<User, Product> userAndProduct = getUserAndProduct(productId, username);

    User user = userAndProduct.getFirst();
    Product product = userAndProduct.getSecond();

    Cart cart = cartRepository.findByUserAndProduct(user, product)
        .orElse(Cart.builder().user(user).product(product).quantity(0).build());

    cart.updateQuantity(cart.getQuantity() + quantity);
    cartRepository.save(cart);
  }

  @Transactional
  public void deleteFromCart(Long productId, String username) {
    Pair<User, Product> userAndProduct = getUserAndProduct(productId, username);

    User user = userAndProduct.getFirst();
    Product product = userAndProduct.getSecond();

    Cart cart = cartRepository.findByUserAndProduct(user, product).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_IN_CART));

    cartRepository.delete(cart);
  }

  private Pair<User, Product> getUserAndProduct(Long productId, String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

    return Pair.of(user, product);
  }

  @Transactional(readOnly = true)
  public List<Cart> getCartProducts(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    System.out.println(user);
    return cartRepository.findAllByUser(user);

  }
}
