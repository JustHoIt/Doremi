package com.example.snsfood.food.service;


import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.model.FoodInput;
import com.example.snsfood.food.model.FoodParam;
import com.example.snsfood.member.model.ServiceResult;

import java.util.List;
import java.util.Map;

public interface FoodService {

    List<FoodDto> list(FoodParam parameter);

    List<FoodDto> mainFoodList(FoodParam foodParam);

    boolean del(String idList);

    boolean add(FoodInput parameter);

    boolean set(FoodInput parameter);

    FoodDto getById(long id);

    FoodDto frontDetail(long id);

    Map<String, Object> barCodeCheck(String barCode);


    ServiceResult useFood(FoodInput foodInput);
}
