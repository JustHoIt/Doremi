package com.example.snsfood.recipe.mapper;

import com.example.snsfood.recipe.dto.RecipeDto;
import com.example.snsfood.recipe.model.RecipeParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecipeMapper {
    List<RecipeDto> selectList(RecipeParam parameter);
    long selectListCount(RecipeParam parameter);
}
