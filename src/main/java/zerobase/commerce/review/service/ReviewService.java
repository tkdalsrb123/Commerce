package zerobase.commerce.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.order.domain.Order;
import zerobase.commerce.order.repository.OrderRepository;
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

  public Review createReview(Request reviewDtoRequest, Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new RuntimeException("주문 정보가 없습니다."));

    return reviewRepository.save(Review.builder()
        .product(order.getProduct())
        .user(order.getUser())
        .title(reviewDtoRequest.getTitle())
        .content(reviewDtoRequest.getContent())
        .rating(reviewDtoRequest.getRating())
        .build());
  }

  @Transactional
  public void deleteReview(Long reviewId, String username) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new RuntimeException("리뷰가 없습니다."));

    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

    // 리뷰 작성자가 아니거나 상품 등록자가 아닌 경우
    if (!review.getUser().equals(user) || !review.getProduct().getUser().equals(user)) {
      throw new RuntimeException("권한이 없습니다.");
    }

    reviewRepository.deleteById(reviewId);
  }

  public Review modifyReview(Request reviewDtoRequest, Long reviewId, String username) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new RuntimeException("리뷰가 없습니다."));

    User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("사용자가 존재하지 않습니다."));

    if (!review.getUser().equals(user)) {
      throw new RuntimeException("권한이 없습니다.");
    }

    review.setTitle(reviewDtoRequest.getTitle());
    review.setContent(reviewDtoRequest.getContent());
    review.setRating(reviewDtoRequest.getRating());

    return review;
  }

  public Page<Review> readReviewList(Long productId, Pageable pageable) {
    Page<Review> reviews = reviewRepository.findAllByProductId(productId, pageable);
    if (reviews.isEmpty()) {
      throw new RuntimeException("해당 상품에는 리뷰가 없습니다.");
    }
    return reviews;
  }
}
