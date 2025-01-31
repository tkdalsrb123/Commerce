package zerobase.commerce.product.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.type.ProductCategory;
import zerobase.commerce.validation.ValidEnum;

public class ProductDto {

  @Getter
  @Setter
  public static class Request {
    @NotBlank(message = "상품 이름을 작성하세요")
    private String productName;
    @NotBlank(message = "상품 설명을 작성하세요.")
    private String productDescription;
    @Min(value = 100, message = "가격은 100원 이상이어야 합니다.")
    private Double productPrice;
    @ValidEnum(enumClass = ProductCategory.class)
    private ProductCategory productCategory;
  }

  @Getter
  @Setter
  @Builder
  public static class Response {
    private String userName;
    private String productName;
    private String productDescription;
    private Double productPrice;
    private LocalDateTime registerAt;

    public static Response of(Product product, String userName) {
      return Response.builder()
          .userName(userName)
          .productName(product.getName())
          .productDescription(product.getDescription())
          .productPrice(product.getPrice())
          .registerAt(product.getModifiedAt())
          .build();
    }
  }

}
