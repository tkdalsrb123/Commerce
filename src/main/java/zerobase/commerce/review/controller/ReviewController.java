package zerobase.commerce.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
  public ReviewDto.Response createReview(@Valid ReviewDto.Request reviewDtoRequest, @PathVariable("orderId") Long orderId) {
    return ReviewDto.Response.of(reviewService.createReview(reviewDtoRequest, orderId));
  }

}
