package zerobase.commerce.product.repository;


import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.type.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findAllByCategory(ProductCategory productCategory, Pageable pageable);

  Optional<Product> findProductById(Long id);

  Page<Product> findAllByCategoryOrderByPrice(ProductCategory category, Pageable pageable);
}
