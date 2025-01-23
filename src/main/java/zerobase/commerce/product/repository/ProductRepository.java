package zerobase.commerce.product.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.commerce.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
