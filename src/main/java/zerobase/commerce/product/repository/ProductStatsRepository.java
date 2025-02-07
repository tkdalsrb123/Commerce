package zerobase.commerce.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.product.domain.ProductStats;

@Repository
public interface ProductStatsRepository extends JpaRepository<ProductStats, Long> {

}
