package zerobase.commerce.product.service;

import java.util.Arrays;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.order.repository.OrderRepository;
import zerobase.commerce.product.domain.ProductStats;
import zerobase.commerce.product.repository.ProductRepository;
import zerobase.commerce.product.repository.ProductStatsRepository;
import zerobase.commerce.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ProductStatsService {

  private final ProductStatsRepository productStatsRepository;
  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;
  private final ProductRepository productRepository;

  @Transactional
  public void updateProductStats() {
    if (productRepository.findAll().isEmpty()) {
      System.out.println("등록된 상품이 없습니다.");
    } else {

      for (Object[] row : reviewRepository.findReviewStatsByProduct()) {
        System.out.println(Arrays.toString(row));
        Long productId = (Long) row[0];
        Long totalReviewCount = (Long) row[1];
        Double averageRating = (Double) row[2];

        int totalReviewInt = totalReviewCount.intValue();
        ProductStats productStats = productStatsRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("상품 집계 정보가 없습니다."));
        productStats.updateReviews(totalReviewInt, averageRating);
        productStats.updateSales(orderRepository.countByProductId(productId));
      }
    }
  }
}
