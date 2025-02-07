package zerobase.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_stats")
//@RedisHash("ProductStats")
public class ProductStats implements Serializable {
  @Id
  @Column(name = "product_id")
  private Long productId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "total_reviews")
  private int totalReviews = 0;

  @Column(name = "average_rating")
  private double averageRating = 0.0;

  @Column(name = "total_sales")
  private int totalSales = 0;

  public void setProduct(Product product) {
    this.product = product;
    this.productId = product.getId();
  }

  public void updateReviews(int reviewCount, double averageRating) {
    this.totalReviews = reviewCount;
    this.averageRating = averageRating;
  }

  public void updateSales(int salesCount) {
    this.totalSales = salesCount;
  }

}
