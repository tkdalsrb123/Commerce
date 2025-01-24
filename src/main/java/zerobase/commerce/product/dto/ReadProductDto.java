package zerobase.commerce.product.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.type.ProductCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadProductDto {
  private String productName;
  private String sellerName;
  private String productDescription;
  private Double productPrice;
  private ProductCategory productCategory;
  private LocalDateTime registeredDate;

  public static ReadProductDto of(Product product) {
    return ReadProductDto.builder()
        .productName(product.getName())
        .sellerName(product.getUser().getUsername())
        .productDescription(product.getDescription())
        .productPrice(product.getPrice())
        .productCategory(product.getCategory())
        .registeredDate(product.getCreatedAt())
        .build();
  }
}
