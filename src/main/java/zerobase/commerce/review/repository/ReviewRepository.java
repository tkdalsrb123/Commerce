package zerobase.commerce.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.review.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

  Page<Review> findAllByProductId(Long productId, Pageable pageable);
}
