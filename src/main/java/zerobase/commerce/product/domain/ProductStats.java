package zerobase.commerce.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_stats")
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

  // db에 적용하지 않을 때
  @Column(name = "total_rating_sum")
  private int totalRatingSum = 0;

  @Column(name = "average_rating")
  private double averageRating = 0.0;

  @Column(name = "total_sales")
  private int totalSales = 0;

  public void setProduct(Product product) {
    this.product = product;
    this.productId = product.getId();
  }

  public void addReviews(int newRating) {
    this.totalRatingSum += newRating;
    this.totalReviews++;
    updateAverageRating();
  }

  public void updateReviews(int oldRating, int newRating) {
    this.totalRatingSum = totalRatingSum - oldRating + newRating;
    updateAverageRating();
  }

  public void deleteReviews(int oldRating) {
    if (this.totalReviews > 0) {
      this.totalRatingSum -= oldRating;
      this.totalReviews--;
    }
    updateAverageRating();
  }

  private void updateAverageRating() {
    this.averageRating = (this.totalReviews == 0) ? 0 : (double) this.totalRatingSum / this.totalReviews;
  }

  public void updateSales(int salesCount) {
    this.totalSales += salesCount;
  }
}
