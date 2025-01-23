package zerobase.commerce.product.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.product.dto.ProductDto;
import zerobase.commerce.product.service.ProductService;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;
  private final String username = SecurityContextHolder.getContext().getAuthentication().getName();

  @PostMapping("/seller/register_product")
  public ProductDto.Response registerProduct(ProductDto.Request productDtoRequest) {
    return ProductDto.Response.of(productService.registerProduct(productDtoRequest, username), username);
  }

  @PostMapping("/seller/modify_product/{productId}")
  public ProductDto.Response modifyProduct(ProductDto.Request productDtoRequest, @PathVariable Long productId) {
    return ProductDto.Response.of(productService.modifyProduct(productDtoRequest, productId, username), username);
  }

  @DeleteMapping("/seller/delete_product/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
    productService.deleteProduct(productId, username);
    return ResponseEntity.ok("상품 삭제 완료");
  }


}
