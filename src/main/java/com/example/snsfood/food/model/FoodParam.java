package com.example.snsfood.food.model;

import com.example.snsfood.admin.model.CommonParam;
import lombok.Data;

@Data
public class FoodParam extends CommonParam {
    String searchType;
    String searchValue;

    String userId;
    long id;


}
