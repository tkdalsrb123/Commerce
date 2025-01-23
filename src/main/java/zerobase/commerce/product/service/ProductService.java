package zerobase.commerce.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.dto.ProductDto.Request;
import zerobase.commerce.product.repository.ProductRepository;
import zerobase.commerce.user.domain.User;
import zerobase.commerce.user.repository.UserRepository;
import zerobase.commerce.user.type.UserType;


@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final UserRepository userRepository;

  public Product registerProduct(Request productDtoRequest, String username) {
    User user = canRegister(username);

    return productRepository.save(Product.builder()
        .name(productDtoRequest.getProductName())
        .description(productDtoRequest.getProductDescription())
        .price(productDtoRequest.getProductPrice())
        .category(productDtoRequest.getProductCategory())
        .user(user)
        .build());
  }

  private User canRegister(String username) {
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

    if (!user.getRole().equals(UserType.ROLE_SELLER)) {
      throw new RuntimeException("권한이 없습니다.");
    }

    return user;
  }

}
