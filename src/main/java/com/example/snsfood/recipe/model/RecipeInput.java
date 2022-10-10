package com.example.snsfood.recipe.model;


import lombok.Data;

import java.sql.Time;

@Data
public class RecipeInput {

    long Id; //아이디
//    long categoryId;
    String categoryName;
    String rcpNm;  //조리이름
    String rcpCookTime; //조리시간
    String rcpKind; //조리분류
    String rcpManual; //조리방법
    String rcpIgdt; // 재료
    String userId; //유저아읻


    String filename; //파일이름
    String urlFilename;  //파일주소


    String idList; // 삭제용도


}
