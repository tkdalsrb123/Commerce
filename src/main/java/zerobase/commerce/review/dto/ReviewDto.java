package zerobase.commerce.review.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.commerce.review.domain.Review;

public class ReviewDto {

  @Getter
  @Setter
  public static class Request {
    @NotNull(message = "제목이 필요합니다.")
    private String title;
    @NotNull(message = "내용이 필요합니다.")
    private String content;
    @Min(0)
    @Max(5)
    @NotNull(message = "점수가 필요합니다.")
    private int rating;
  }

  @Getter
  @Setter
  @Builder
  public static class Response {
    private String title;
    private String content;
    private int rating;

    public static ReviewDto.Response of(Review review) {
      return Response.builder()
          .title(review.getTitle())
          .content(review.getContent())
          .rating(review.getRating())
          .build();
    }

  }

}
