package zerobase.commerce.product.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.product.dto.ProductDto;
import zerobase.commerce.product.service.ProductService;
import zerobase.commerce.user.domain.User;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  @PostMapping("/seller/register_product")
  public ProductDto.Response registerProduct(ProductDto.Request productDtoRequest) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    return ProductDto.Response.of(productService.registerProduct(productDtoRequest, username), username);
  }
}
