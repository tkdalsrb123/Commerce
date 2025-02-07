package zerobase.commerce.redis.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import zerobase.commerce.product.domain.ProductStats;
import zerobase.commerce.product.repository.ProductStatsRepository;

@Service
@RequiredArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ProductStatsRepository productStatsRepository;
  private static final String cacheKey = "product:%s:%s";

  public List<ProductStats> getProductsSortedBy(String sortBy, String order) {
    String key = getCacheKey(sortBy, order);
    List<Object> cachedProducts = redisTemplate.opsForList().range(key, 0, -1);
    if (cachedProducts != null && !cachedProducts.isEmpty()) {
      return cachedProducts.stream()
          .map(product -> (ProductStats) product)
          .collect(Collectors.toList());
    }
    List<ProductStats> products = sortProducts(sortBy, order);
    
    redisTemplate.opsForList().rightPushAll(key, products);
    redisTemplate.expire(key, Duration.ofHours(1));

    return products;
  }

  private String getCacheKey(String sortBy, String order) {
    return String.format(cacheKey, sortBy, order);
  }

  private List<ProductStats> sortProducts(String sortBy, String order) {
    List<ProductStats> productStats = List.of();
    if (sortBy.equals("rating")) {
      productStats = productStatsRepository.findAll().stream()
          .sorted((p1, p2) -> {
            if ("asc".equalsIgnoreCase(order)) {
              return Double.compare(p1.getAverageRating(), p2.getAverageRating());
            } else if ("desc".equalsIgnoreCase(order)) {
              return Double.compare(p2.getAverageRating(), p1.getAverageRating());
            }
            throw new RuntimeException("잘못된 문자입니다.");
          })
          .collect(Collectors.toList());
    } else if (sortBy.equals("sales")) {
      productStats = productStatsRepository.findAll().stream()
          .sorted((p1, p2) -> {
            if ("asc".equalsIgnoreCase(order)) {
              return Double.compare(p1.getTotalSales(), p2.getTotalSales());
            } else if ("desc".equalsIgnoreCase(order)) {
              return Double.compare(p2.getTotalSales(), p1.getTotalSales());
            }
            throw new RuntimeException("잘못된 문자입니다.");
          })
          .collect(Collectors.toList());
    } else if (sortBy.equals("review")) {
      productStats = productStatsRepository.findAll().stream()
          .sorted((p1, p2) -> {
            if ("asc".equalsIgnoreCase(order)) {
              return Double.compare(p1.getTotalReviews(), p2.getTotalReviews());
            } else if ("desc".equalsIgnoreCase(order)) {
              return Double.compare(p2.getTotalReviews(), p1.getTotalReviews());
            }
            throw new RuntimeException("잘못된 문자입니다.");
          })
          .collect(Collectors.toList());
    }
    return productStats;
  }

}
