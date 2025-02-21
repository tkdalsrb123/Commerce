package zerobase.commerce.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.exception.CustomException;
import zerobase.commerce.exception.ErrorCode;
import zerobase.commerce.order.domain.Order;
import zerobase.commerce.order.repository.OrderRepository;
import zerobase.commerce.product.domain.ProductStats;
import zerobase.commerce.product.repository.ProductStatsRepository;
import zerobase.commerce.review.domain.Review;
import zerobase.commerce.review.dto.ReviewDto.Request;
import zerobase.commerce.review.repository.ReviewRepository;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;
  private final UserRepository userRepository;
  private final ProductStatsRepository productStatsRepository;

  @Transactional
  public Review createReview(Request reviewDtoRequest, Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

    Review review = reviewRepository.save(Review.builder()
        .product(order.getProduct())
        .user(order.getUser())
        .title(reviewDtoRequest.getTitle())
        .content(reviewDtoRequest.getContent())
        .rating(reviewDtoRequest.getRating())
        .build());

    ProductStats stats = productStatsRepository.findById(order.getProduct().getId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_STATS_NOT_FOUND));

    stats.addReviews(reviewDtoRequest.getRating());
    productStatsRepository.save(stats);

    return review;
  }

  @Transactional
  public void deleteReview(Long reviewId, String username) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

    User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    // 리뷰 작성자가 아니거나 상품 등록자가 아닌 경우
    if (!review.getUser().equals(user) || !review.getProduct().getUser().equals(user)) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    int rating = review.getRating();

    reviewRepository.deleteById(reviewId);

    ProductStats stats = productStatsRepository.findById(review.getProduct().getId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_STATS_NOT_FOUND));
    stats.deleteReviews(rating);
    productStatsRepository.save(stats);
  }

  @Transactional
  public Review modifyReview(Request reviewDtoRequest, Long reviewId, String username) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));

    User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    if (!review.getUser().equals(user)) {
      throw new CustomException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    int oldRating = review.getRating();

    review.setTitle(reviewDtoRequest.getTitle());
    review.setContent(reviewDtoRequest.getContent());
    review.setRating(reviewDtoRequest.getRating());

    ProductStats stats = productStatsRepository.findById(review.getProduct().getId()).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_STATS_NOT_FOUND));
    stats.updateReviews(oldRating, reviewDtoRequest.getRating());

    return review;
  }

  @Transactional(readOnly = true)
  public Page<Review> readReviewList(Long productId, Pageable pageable) {
    Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageable);
    if (reviews.isEmpty()) {
      throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }
    return reviews;
  }
}
