package com.example.snsfood.admin.model;

import lombok.Data;

@Data
public class CategoryInput {
    String categoryName;
    long id;
    int sortValue;
    boolean usingYn;
}
