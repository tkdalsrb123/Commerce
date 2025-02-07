package zerobase.commerce.review.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zerobase.commerce.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  Page<Review> findAllByProductId(Long productId, Pageable pageable);

  @Query(value = "select r.product.id, count(r), avg(r.rating) " +
      "FROM Review r " +
      "group by r.product.id")
  List<Object[]> findReviewStatsByProduct();
}
