package com.example.snsfood.food.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;  //식품 고유키
    String userId; //유저아이디
    String foodNm; //식품이름 (제품명)
    String foodQt; //수량
    String foodOt; //식품 식품유형 (식품유형)
    String foodOt2; //식품 유형 직접 입력
    String foodTp; //식품구분;
    String foodMm;  //제조사 (제조사명)
    String barCode; //바코드
    String foodStorage; //보관방법
    boolean useYn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate buyDt; //구매일자
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate expDt; //유통기한
    LocalDateTime regDt; //작성일
    LocalDateTime udtDt; //수정일

    String filename; //파일이름
    String urlFilename;  //파일주소

}
