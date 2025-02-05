package zerobase.commerce.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.review.domain.ReadReviewDto;
import zerobase.commerce.review.dto.ReviewDto;
import zerobase.commerce.review.service.ReviewService;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ReviewController {

  private final ReviewService reviewService;

  private String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @PostMapping("/reviews/{orderId}")
  public ReviewDto.Response createReview(@Valid ReviewDto.Request reviewDtoRequest,
      @PathVariable("orderId") Long orderId) {
    return ReviewDto.Response.of(reviewService.createReview(reviewDtoRequest, orderId));
  }

  @DeleteMapping("/reviews/{reviewId}")
  public ResponseEntity<String> deleteReview(@PathVariable("reviewId") Long reviewId) {
    String username = getUsername();
    reviewService.deleteReview(reviewId, username);
    return ResponseEntity.ok("리뷰 삭제 완료.");
  }

  @PutMapping("/reviews/{reviewId}")
  public ReviewDto.Response modifyReview(@Valid ReviewDto.Request reviewDtoRequest,
      @PathVariable("reviewId") Long reviewId) {
    String username = getUsername();
    return ReviewDto.Response.of(reviewService.modifyReview(reviewDtoRequest, reviewId, username));
  }

  @GetMapping("/{productId}")
  public Page<ReadReviewDto> readReviewList(@PathVariable("productId") Long productId,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
    return reviewService.readReviewList(productId, pageable).map(ReadReviewDto::of);
  }


}
