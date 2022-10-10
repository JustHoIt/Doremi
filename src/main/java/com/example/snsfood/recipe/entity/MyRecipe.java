package com.example.snsfood.recipe.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class MyRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String rcpNm; //레시피 이름
    String userId;  //유저아이디
    String rcpCookTime; //조리시간
    String rcpKind; //분류
    String categoryName;
//    long categoryId;


    @Column(length = 2000)
    String rcpManual; //조리방법

    String rcpIgdt; //재료
    LocalDateTime regDt; // 등록시간
    LocalDateTime udtDt;  // 수정시간
    String filename; //파일이름
    String urlFilename;  //파일주소

}
