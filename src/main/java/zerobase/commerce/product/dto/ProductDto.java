package zerobase.commerce.product.dto;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.type.ProductCategory;

public class ProductDto {

  @Getter
  @Setter
  public static class Request {
    private String productName;
    private String productDescription;
    private Double productPrice;
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
    private LocalDateTime createdAt;

    public static Response of(Product product, String userName) {
      return Response.builder()
          .userName(userName)
          .productName(product.getName())
          .productDescription(product.getDescription())
          .productPrice(product.getPrice())
          .createdAt(product.getCreatedAt())
          .build();
    }
  }

}
