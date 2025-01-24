package zerobase.commerce.product.controller;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.product.dto.ProductDto;
import zerobase.commerce.product.dto.ReadProductDto;
import zerobase.commerce.product.service.ProductService;
import zerobase.commerce.product.type.ProductCategory;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;


  private String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @PostMapping("/seller/product/register")
  public ProductDto.Response registerProduct(ProductDto.Request productDtoRequest) {
    String username = getUsername();
    return ProductDto.Response.of(productService.registerProduct(productDtoRequest, username), username);
  }

  @PutMapping("/seller/product/modify/{productId}")
  public ProductDto.Response modifyProduct(ProductDto.Request productDtoRequest, @PathVariable Long productId) {
    String username = getUsername();
    return ProductDto.Response.of(productService.modifyProduct(productDtoRequest, productId, username), username);
  }

  @DeleteMapping("/seller/product/delete/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
    String username = getUsername();
    productService.deleteProduct(productId, username);
    return ResponseEntity.ok("상품 삭제 완료");
  }

  @GetMapping("/product/read/{productId}")
  public ReadProductDto readProduct(@PathVariable Long productId) {
    return ReadProductDto.of(productService.readProduct(productId));
  }

  @GetMapping("/product/read/{category}/list")
  public List<ReadProductDto> readProductList(@PathVariable ProductCategory category) {

    return productService.readProductList(category).stream()
        .map(ReadProductDto::of)
        .collect(Collectors.toList());
  }

}
