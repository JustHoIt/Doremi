package com.example.snsfood.food.dto;


import com.example.snsfood.food.entity.Food;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FoodDto {
    long id;  //식품 고유키
    String userId; //유저아이디
    String foodNm; //식품이름(제품명)
    String foodQt; //수량
    String foodOt; //식품 원초구분
    String diffDay;

    String foodOt2; //식품 유형 직접 입력
    String foodTp; //식품구분;
    String foodMm;  //제조사 mfrNm
    String barCode; //바코드
    String foodStorage; //보관방법
    boolean useYn; //사용여

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate buyDt; //구매일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate expDt; //유통기한
    LocalDateTime regDt; //작성일
    LocalDateTime udtDt; //수정일

    String filename; //파일이름
    String urlFilename;  //파일주소

    long totalCount;
    long seq;


    public static FoodDto of(Food food) {

        return FoodDto.builder()
                .id(food.getId())
                .foodNm(food.getFoodNm())
                .userId(food.getUserId())
                .foodOt(food.getFoodOt())
                .foodQt(food.getFoodQt())
                .foodOt2(food.getFoodOt2())
                .barCode(food.getBarCode())
                .useYn(food.isUseYn())
                .foodStorage(food.getFoodStorage())
                .foodTp(food.getFoodTp())
                .foodMm(food.getFoodMm())
                .buyDt(food.getBuyDt())
                .udtDt(food.getUdtDt())
                .buyDt(food.getBuyDt())
                .expDt(food.getExpDt())
                .urlFilename(food.getUrlFilename())
                .filename(food.getFilename())
                .build();
    }
}
