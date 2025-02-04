package zerobase.commerce.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.order.domain.Order;

public class OrderDto {

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Request {

    @NotNull(message = "상품 ID는 필수입니다.")
    private Long productId;

    @Min(value = 1, message = "최소 주문 수량은 1개입니다.")
    private int quantity;
  }

  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private Long orderId;
    private String productName;
    private String userName;
    private int quantity;
    private double totalPrice;


    public static OrderDto.Response of(Order order) {
      return Response.builder()
          .orderId(order.getId())
          .productName(order.getProduct().getName())
          .userName(order.getUser().getUsername())
          .quantity(order.getQuantity())
          .totalPrice(order.getTotalPrice())
          .build();
    }
  }

}
