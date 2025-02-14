package zerobase.commerce.cart.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.cart.dto.CartDto;
import zerobase.commerce.cart.service.CartService;

@Controller
@ResponseBody
@RequiredArgsConstructor
@RequestMapping("/buyer/cart")
public class CartController {

  private final CartService cartService;

  private String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @PostMapping("/add")
  public ResponseEntity<String> addToCart(@RequestParam Long productId,
      @RequestParam int quantity) {
    String username = getUsername();

    cartService.addToCart(productId, quantity, username);
    return ResponseEntity.ok("장바구니에 상품이 추가되었습니다.");
  }

  @DeleteMapping("/delete")
  public ResponseEntity<String> deleteFromCart(@RequestParam Long productId) {
    String username = getUsername();

    cartService.deleteFromCart(productId, username);
    return ResponseEntity.ok("장바구니에 상품이 삭제되었습니다.");
  }

  @GetMapping
  public List<CartDto> getCart() {
    String username = getUsername();

    return cartService.getCartProducts(username).stream().map(CartDto::of).toList();
  }

}
