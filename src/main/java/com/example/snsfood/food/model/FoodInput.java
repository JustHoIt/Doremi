package com.example.snsfood.food.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class FoodInput {

    long id;  //식품 고유키
    String userId; //유저아이디
    String foodNm; //식품이름
    String foodQt; //수량
    String foodOt; //식품 원초구분
    String foodOt2; //식품 유형 직접 입력
    String foodTp; //식품구분;
    String foodMm;  //제조사 mfrNm
    String barCode; //바코드
    String foodStorage; //식품저장방법

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate buyDt; //구매일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate expDt; //유통기한

    LocalDateTime regDt; //작성일
    LocalDateTime udtDt; //수정일

    String filename; //파일이름
    String urlFilename;  //파일주소

    String idList; // 삭제용도
}
