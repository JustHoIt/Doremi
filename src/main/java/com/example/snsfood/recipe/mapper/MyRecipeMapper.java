package com.example.snsfood.recipe.mapper;

import com.example.snsfood.recipe.dto.MyRecipeDto;
import com.example.snsfood.recipe.model.MyRecipeParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MyRecipeMapper {

    List<MyRecipeDto> selectList(MyRecipeParam parameter);
    long selectListCount(MyRecipeParam parameter);
}
