package com.example.snsfood.recipe.service.Impl;

import com.example.snsfood.recipe.dto.RecipeDto;
import com.example.snsfood.recipe.entity.Recipe;
import com.example.snsfood.recipe.mapper.RecipeMapper;
import com.example.snsfood.recipe.model.RecipeParam;
import com.example.snsfood.recipe.repository.RecipeRepository;
import com.example.snsfood.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    public RecipeDto frontDetail(String rcpSeq) {
        Optional<Recipe> optionalCourse = recipeRepository.findByRcpSeq(rcpSeq);
        if(optionalCourse.isPresent()) {
            return RecipeDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public List<RecipeDto> list(RecipeParam parameter) {
        long totalCount = recipeMapper.selectListCount(parameter);
        List<RecipeDto> list = recipeMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (RecipeDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }

}
