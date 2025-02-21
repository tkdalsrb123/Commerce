package zerobase.commerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {


  UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "권한이 없습니다."),

  INSUFFICIENT_STOCK(HttpStatus.BAD_REQUEST, "재고가 부족합니다."),
  EMPTY_CART(HttpStatus.BAD_REQUEST, "장바구니가 비어 있습니다."),
  INSUFFICIENT_STOCK_IN_CART(HttpStatus.BAD_REQUEST, "재고가 부족한 상품이 있습니다."),
  PRODUCT_NOT_IN_CART(HttpStatus.BAD_REQUEST, "장바구니에 선택된 상품이 없습니다."),

  PRODUCTS_NOT_FOUND_IN_CATEGORY(HttpStatus.NOT_FOUND, "해당 카테고리에 등록된 상품이 없습니다."),
  PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "상품을 찾을 수 없습니다."),
  PRODUCT_STATS_NOT_FOUND(HttpStatus.NOT_FOUND, "상품 집게 정보가 없습니다."),
  REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "리뷰를 찾을 수 없습니다."),
  ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "주문 정보를 찾을 수 없습니다."),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");

  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
