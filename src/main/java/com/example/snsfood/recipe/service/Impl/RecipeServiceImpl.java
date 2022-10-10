package com.example.snsfood.recipe.service.Impl;

import com.example.snsfood.recipe.dto.MyRecipeDto;
import com.example.snsfood.recipe.dto.RecipeDto;
import com.example.snsfood.recipe.entity.MyRecipe;
import com.example.snsfood.recipe.entity.Recipe;
import com.example.snsfood.recipe.mapper.MyRecipeMapper;
import com.example.snsfood.recipe.mapper.RecipeMapper;
import com.example.snsfood.recipe.model.MyRecipeParam;
import com.example.snsfood.recipe.model.RecipeInput;
import com.example.snsfood.recipe.model.RecipeParam;
import com.example.snsfood.recipe.repository.MyRecipeRepository;
import com.example.snsfood.recipe.repository.RecipeRepository;
import com.example.snsfood.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final MyRecipeRepository myRecipeRepository;
    private final RecipeMapper recipeMapper;
    private final MyRecipeMapper myRecipeMapper;




    @Override
    public boolean add(RecipeInput parameter) {
        MyRecipe myRecipe = MyRecipe.builder()

                .rcpNm(parameter.getRcpNm())
                .rcpCookTime(parameter.getRcpCookTime())
                .rcpIgdt(parameter.getRcpIgdt())
                .rcpManual(parameter.getRcpManual())
                .userId(parameter.getUserId())
                .categoryName(parameter.getCategoryName())
                .regDt(LocalDateTime.now())
                .filename(parameter.getFilename())
                .urlFilename(parameter.getUrlFilename())
                .build();
        myRecipeRepository.save(myRecipe);

        return true;
    }

    @Override
    public boolean set(RecipeInput parameter) {
        Optional<MyRecipe> optionalMyRecipe = myRecipeRepository.findById(parameter.getId());
        if(!optionalMyRecipe.isPresent()){
            return false;
        }

        MyRecipe myRecipe = optionalMyRecipe.get();
        myRecipe.setCategoryName(parameter.getCategoryName());
        myRecipe.setRcpCookTime(parameter.getRcpCookTime());
        myRecipe.setRcpNm(parameter.getRcpNm());
        myRecipe.setRcpIgdt(parameter.getRcpIgdt());
        myRecipe.setRcpManual(parameter.getRcpManual());
        myRecipe.setUdtDt(LocalDateTime.now());
        myRecipe.setFilename(parameter.getFilename());
        myRecipe.setFilename(parameter.getFilename());
        myRecipeRepository.save(myRecipe);

        return true;
    }

    @Override
    public RecipeDto frontDetail(String rcpSeq) {
        Optional<Recipe> optionalCourse = recipeRepository.findByRcpSeq(rcpSeq);
        if (optionalCourse.isPresent()) {
            return RecipeDto.of(optionalCourse.get());
        }
        return null;
    }

    @Override
    public MyRecipeDto recipeDetail(long id) {
       Optional<MyRecipe> optionalMyRecipe = myRecipeRepository.findById(id);
       if(optionalMyRecipe.isPresent()) {
           return MyRecipeDto.of(optionalMyRecipe.get());
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

    @Override
    public List<MyRecipeDto> myRecipeList(MyRecipeParam parameter) {
        long totalCount = myRecipeMapper.selectListCount(parameter);
        List<MyRecipeDto> list = myRecipeMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (MyRecipeDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }
        return list;
    }
//    @Override
//    public List<MyRecipeDto> myRecipeList(String userId) {
//
//        MyRecipeParam parameter = new MyRecipeParam();
//        parameter.setUserId(userId);
//        List<MyRecipeDto> list = myRecipeMapper.selectListMyRecipe(parameter);
//
//        return list;
//    }

    @Override
    public boolean del(String idList) {
        if (idList != null && idList.length() > 0) {

            String[] ids = idList.split(",");
            for (String x : ids) {
                long id = 0L;
                try {
                    id = Long.parseLong(x);
                } catch (Exception e) {
                }
                if (id > 0) {
                    myRecipeRepository.deleteById(id);
                }
            }
        }
        return true;
    }

    @Override
    public MyRecipeDto getById(long id) {
        return myRecipeRepository.findById(id).map(MyRecipeDto::of).orElse(null);
    }


}

