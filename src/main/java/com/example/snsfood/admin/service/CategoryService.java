package com.example.snsfood.admin.service;

import com.example.snsfood.admin.dto.CategoryDto;
import com.example.snsfood.admin.model.CategoryInput;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> list();
    boolean add(String categoryName); //카테코리 추가

    boolean update(CategoryInput parameter); //카테고리 업데이트 raw데이터

    boolean delete(long id); //카테고리 삭제

    List<CategoryDto> frontList(CategoryDto parameter); //유저 카테고리 정보


}
