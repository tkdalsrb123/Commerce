package zerobase.commerce.product.type;

import lombok.Getter;

@Getter
public enum ProductSortBy {
  CREATED_AT("createdAT"),
  PRICE("price"),
  TOTAL_REVIEWS("totalReviews"),
  AVERAGE_RATING("average_rating"),
  TOTAL_SALES("total_sales");

  private final String field;

  ProductSortBy(String field) {
    this.field = field;
  }

}
