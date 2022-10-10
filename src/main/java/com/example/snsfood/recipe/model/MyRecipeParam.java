package com.example.snsfood.recipe.model;


import com.example.snsfood.admin.model.CommonParam;
import lombok.Data;

@Data
public class MyRecipeParam extends CommonParam {

    String searchType;
    String searchValue;

    long id;
    String userId;
//    long categoryId;
    long categoryName;
}
