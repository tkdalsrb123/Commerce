package zerobase.commerce.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.commerce.order.domain.Order;
import zerobase.commerce.order.repository.OrderRepository;
import zerobase.commerce.review.domain.Review;
import zerobase.commerce.review.dto.ReviewDto.Request;
import zerobase.commerce.review.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

  private final ReviewRepository reviewRepository;
  private final OrderRepository orderRepository;

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

}
