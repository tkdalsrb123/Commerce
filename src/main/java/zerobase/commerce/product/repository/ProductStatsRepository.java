package zerobase.commerce.product.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.domain.ProductStats;
import zerobase.commerce.product.type.ProductCategory;

@Repository
public interface ProductStatsRepository extends JpaRepository<ProductStats, Long> {

  @Query("select ps.product from ProductStats ps " +
      "join ps.product p " +
      "where p.category = :category " +
      "order by ps.totalReviews")
  Page<Product> findAllProductsByCategoryOrderByTotalReviews(ProductCategory category, Pageable pageable);

  @Query("select ps.product from ProductStats ps " +
      "join ps.product p " +
      "where p.category = :category " +
      "order by ps.averageRating  ")
  Page<Product> findAllProductsByCategoryOrderByAverageRating(ProductCategory category, Pageable pageable);

  @Query("select ps.product from ProductStats ps " +
      "join ps.product p " +
      "where p.category = :category " +
      "order by ps.totalSales  ")
  Page<Product> findAllProductsByCategoryOrderByTotalSales(ProductCategory category, Pageable pageable);
}
