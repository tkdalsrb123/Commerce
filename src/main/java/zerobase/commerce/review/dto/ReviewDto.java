package zerobase.commerce.review.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.commerce.review.domain.Review;
import zerobase.commerce.review.type.Rating;
import zerobase.commerce.validation.ValidEnum;

public class ReviewDto {

  @Getter
  @Setter
  public static class Request {
    @NotNull(message = "제목이 필요합니다.")
    private String title;
    @NotNull(message = "내용이 필요합니다.")
    private String content;
    @ValidEnum(enumClass = Rating.class)
    private Rating rating;
  }

  @Getter
  @Setter
  @Builder
  public static class Response {
    private String title;
    private String content;
    private Rating rating;

    public static ReviewDto.Response of(Review review) {
      return Response.builder()
          .title(review.getTitle())
          .content(review.getContent())
          .rating(review.getRating())
          .build();
    }

  }

}
