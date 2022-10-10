package com.example.snsfood.recipe.repository;

import com.example.snsfood.recipe.entity.MyRecipe;
import com.example.snsfood.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    static void save(MyRecipe myRecipe) {
    }

    Optional<Recipe> findByRcpSeq(String rcpSeq);
}
