package zerobase.commerce.product.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import zerobase.commerce.product.dto.ProductDto;
import zerobase.commerce.product.dto.ReadProductDto;
import zerobase.commerce.product.service.ProductService;
import zerobase.commerce.product.type.ProductCategory;
import zerobase.commerce.product.type.ProductSortBy;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;


  private String getUsername() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @PostMapping("/seller/products")
  public ProductDto.Response registerProduct(@Valid ProductDto.Request productDtoRequest) {
    String username = getUsername();
    return ProductDto.Response.of(productService.registerProduct(productDtoRequest, username),
        username);
  }

  @PutMapping("/seller/products/{productId}")
  public ProductDto.Response modifyProduct(@Valid ProductDto.Request productDtoRequest,
      @PathVariable Long productId) {
    String username = getUsername();
    return ProductDto.Response.of(
        productService.modifyProduct(productDtoRequest, productId, username), username);
  }

  @DeleteMapping("/seller/products/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
    String username = getUsername();
    productService.deleteProduct(productId, username);
    return ResponseEntity.ok("상품 삭제 완료");
  }

  @GetMapping("/products/{productId}")
  public ReadProductDto readProduct(@PathVariable Long productId) {
    return ReadProductDto.of(productService.readProduct(productId));
  }

  @GetMapping("/products/category/{category}")
  public Page<ReadProductDto> readProductList(@PathVariable ProductCategory category,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") ProductSortBy sortBy,
      @RequestParam(defaultValue = "desc") String sortDirection) {

    Sort.Direction direction = "asc".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;

    Pageable pageable = PageRequest.of(page, size, direction, sortBy.getField());

    return productService.readProductList(category, sortBy, pageable).map(ReadProductDto::of);
  }

}
