package zerobase.commerce.product.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.product.domain.Product;
import zerobase.commerce.product.type.ProductCategory;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


  List<Product> findAllByCategoryOrderByCreatedAt(ProductCategory category);
}
