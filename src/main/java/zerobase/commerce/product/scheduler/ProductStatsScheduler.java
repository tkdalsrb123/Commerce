package zerobase.commerce.product.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import zerobase.commerce.product.service.ProductStatsService;

@Component
@RequiredArgsConstructor
public class ProductStatsScheduler {

  private final ProductStatsService productStatsService;

//  @PostConstruct
//  @Scheduled(fixedDelay = 10000)
//  public void init() {
//    System.out.println("상품 정보 집계중..");
//    productStatsService.updateProductStats();
//    System.out.println("상품 정보 집계 완료..");
//  }

  @Scheduled(cron = "0 */1 * * * *")
  public void updateProductStats() {
    System.out.println("Scheduled 실행: Product Stats 업데이트 시작");
    productStatsService.updateProductStats();
    System.out.println("Scheduled 실행: Product Stats 업데이트 종료");
  }

}
