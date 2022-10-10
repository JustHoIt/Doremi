package com.example.snsfood.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Category {
    @Id
    String categoryName;   // 카테고리명


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id; //Id


    int sortValue;   //정렬값
    boolean usingYn; //사용가능여부

}
