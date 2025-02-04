package zerobase.commerce.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.order.domain.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
