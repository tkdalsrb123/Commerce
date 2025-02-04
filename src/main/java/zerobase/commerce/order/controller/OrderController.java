package zerobase.commerce.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.order.dto.OrderDto;
import zerobase.commerce.order.service.OrderService;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;

  private String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @PostMapping("/buyer/orders")
  public OrderDto.Response createOrder(@Valid OrderDto.Request orderDtoRequest) {
    String username = getUsername();
    return OrderDto.Response.of(orderService.createOrder(orderDtoRequest, username));
  }


}
