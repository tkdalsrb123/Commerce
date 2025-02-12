package zerobase.commerce.product.type;

import lombok.Getter;

@Getter
public enum ProductSortBy {
  CREATED_AT("createdAt"),
  PRICE("price"),
  TOTAL_REVIEWS("totalReviews"),
  AVERAGE_RATING("averageRating"),
  TOTAL_SALES("totalSales");

  private final String field;

  ProductSortBy(String field) {
    this.field = field;
  }

}
