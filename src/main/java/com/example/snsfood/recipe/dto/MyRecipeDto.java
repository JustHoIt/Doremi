package com.example.snsfood.recipe.dto;


import com.example.snsfood.recipe.entity.MyRecipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MyRecipeDto {
    long id;  //고유값

//    long categoryId;
    String categoryName;
    String rcpNm; //레시피 이름
    String userId;  //유저아이디
    String rcpCookTime; //조리시간
    String rcpKind; //종류
    String rcpManual; //조리방법

    String rcpIgdt; //재료
    LocalDateTime regDt; // 등록시간
    LocalDateTime udtDt; //업데이트 시간.


    String filename; //파일이름
    String urlFilename;  //파일주소

    long totalCount;
    long seq;

    public static MyRecipeDto of(MyRecipe myRecipe) {

        return MyRecipeDto.builder()
                .id(myRecipe.getId())
                .categoryName(myRecipe.getCategoryName())
                .rcpNm(myRecipe.getRcpNm())
                .rcpIgdt(myRecipe.getRcpIgdt())
                .rcpKind(myRecipe.getRcpKind())
                .rcpManual(myRecipe.getRcpManual())
                .rcpCookTime(myRecipe.getRcpCookTime())
                .userId(myRecipe.getUserId())
                .regDt(myRecipe.getRegDt())
                .udtDt(myRecipe.getUdtDt())
                .filename(myRecipe.getFilename())
                .urlFilename(myRecipe.getUrlFilename())
                .build();
    }

}
