package com.example.snsfood.recipe.service;

import com.example.snsfood.recipe.dto.MyRecipeDto;
import com.example.snsfood.recipe.dto.RecipeDto;
import com.example.snsfood.recipe.model.MyRecipeParam;
import com.example.snsfood.recipe.model.RecipeInput;
import com.example.snsfood.recipe.model.RecipeParam;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecipeService {



    //마이레시피 등록
    boolean add(RecipeInput parameter);

    boolean set(RecipeInput parameter);


    RecipeDto frontDetail(String rcpSeq);


    MyRecipeDto recipeDetail(long id);


    //공용 레시피 목록 보여주기
    List<RecipeDto> list(RecipeParam parameter);


    //유저 마이레시피 목록 보여주기
    List<MyRecipeDto> myRecipeList(MyRecipeParam parameter);

    //마이레시피 삭제
    boolean del(String idList);

    MyRecipeDto getById(long id);


}
