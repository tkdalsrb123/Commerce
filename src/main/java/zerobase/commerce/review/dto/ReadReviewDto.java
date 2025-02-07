package zerobase.commerce.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.review.domain.Review;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadReviewDto {
  private String title;
  private String content;
  private String author;
  private int rating;

  public static ReadReviewDto of(Review review) {
    return ReadReviewDto.builder()
        .title(review.getTitle())
        .content(review.getContent())
        .author(review.getUser().getUsername())
        .rating(review.getRating())
        .build();
  }
}
