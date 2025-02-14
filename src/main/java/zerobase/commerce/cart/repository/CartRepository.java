package zerobase.commerce.cart.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.cart.domain.Cart;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.user.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

  Optional<Cart> findByUserAndProduct(User user, Product product);

  List<Cart> findAllByUser(User user);
}
