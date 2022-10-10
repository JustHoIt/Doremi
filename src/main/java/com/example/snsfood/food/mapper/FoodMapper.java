package com.example.snsfood.food.mapper;

import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.model.FoodParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoodMapper {
    List<FoodDto> selectList(FoodParam parameter);
    long selectListCount(FoodParam parameter);
    List<FoodDto> selectMainFoodList(FoodParam foodParam);

    void useFood(long foodId);
}
