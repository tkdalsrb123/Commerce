package zerobase.commerce.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import zerobase.commerce.cart.domain.Cart;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

  private String productName;

  private int quantity;

  private double price;

  public static CartDto of(Cart cart) {
    return CartDto.builder().productName(cart.getProduct().getName()).quantity(cart.getQuantity())
        .price(cart.getProduct().getPrice() * cart.getQuantity()).build();
  }
}
