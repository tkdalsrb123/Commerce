package zerobase.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zerobase.commerce.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  @Query("select count(o) " +
      "from Order o " +
      "where o.product.id = :productId")
  int countByProductId(@Param("productId") Long productId);
}
