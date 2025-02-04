package zerobase.commerce.product.type;

import lombok.Getter;

@Getter
public enum ProductCategory {

  ELECTRONICS("전자 제품"),
  FASHION("의류 및 패션"),
  BEAUTY("화장품 및 뷰티"),
  HOME_APPLIANCES("가전제품"),
  FOOD("식품"),
  BOOKS("도서"),
  SPORTS("스포츠 용품"),
  TOYS("장난감"),
  HEALTH("건강 및 헬스 용품"),
  PETS("반려동물 용품"),
  FURNITURE("가구"),
  AUTOMOTIVE("자동차 용품"),
  STATIONERY("문구류"),
  GARDENING("원예 및 정원용품"),
  OTHER("기타");

  private final String description;

  ProductCategory(String description) {
    this.description = description;
  }

}