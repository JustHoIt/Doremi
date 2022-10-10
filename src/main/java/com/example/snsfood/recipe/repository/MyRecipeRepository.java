package com.example.snsfood.recipe.repository;

import com.example.snsfood.recipe.entity.MyRecipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRecipeRepository extends JpaRepository<MyRecipe, Long> {
}
