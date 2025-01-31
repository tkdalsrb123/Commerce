package zerobase.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.dto.ProductDto.Request;
import zerobase.commerce.product.repository.ProductRepository;
import zerobase.commerce.product.type.ProductCategory;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.repository.UserRepository;
import zerobase.commerce.user.type.UserType;


@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public Product registerProduct(Request productDtoRequest, String username) {
    User user = validateSellerAuthority(username);

    return productRepository.save(Product.builder()
        .name(productDtoRequest.getProductName())
        .description(productDtoRequest.getProductDescription())
        .price(productDtoRequest.getProductPrice())
        .category(productDtoRequest.getProductCategory())
        .user(user)
        .build());
  }

  private User validateSellerAuthority(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    if (user.getRole() != UserType.ROLE_SELLER) {
      throw new RuntimeException("권한이 없습니다.");
    }

    return user;
  }

  private Product validateProductAuthority(User user, Long productId) {
    Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("등록된 상품이 없습니다."));
    if (!product.getUser().getUsername().equals(user.getUsername())) {
      throw new RuntimeException("권한이 없습니다.");
    }
    return product;
  }

  @Transactional
  public Product modifyProduct(Request productDtoRequest, Long productId, String username) {
    User user = validateSellerAuthority(username);
    Product product = validateProductAuthority(user, productId);

    product.setName(productDtoRequest.getProductName());
    product.setDescription(productDtoRequest.getProductDescription());
    product.setPrice(productDtoRequest.getProductPrice());
    product.setCategory(productDtoRequest.getProductCategory());

    return product;
  }

  @Transactional
  public void deleteProduct(Long productId, String username) {
    User user = validateSellerAuthority(username);
    Product product = validateProductAuthority(user, productId);
    productRepository.delete(product);
  }

  public Product readProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(() -> new RuntimeException("등록된 상품이 없습니다."));
  }

  public Page<Product> readProductList(ProductCategory category, Pageable pageable) {
    Page<Product> products = productRepository.findAllByCategory(category, pageable);
    if (products.isEmpty()) {
      throw new RuntimeException("해당 카테고리에 등록된 상품이 없습니다.");
    }
    return products;
  }
}
