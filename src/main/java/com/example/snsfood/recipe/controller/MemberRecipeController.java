package com.example.snsfood.recipe.controller;


import com.example.snsfood.admin.service.CategoryService;
import com.example.snsfood.main.BaseController;
import com.example.snsfood.recipe.dto.MyRecipeDto;
import com.example.snsfood.recipe.dto.RecipeDto;
import com.example.snsfood.recipe.model.MyRecipeParam;
import com.example.snsfood.recipe.model.RecipeInput;
import com.example.snsfood.recipe.model.RecipeParam;
import com.example.snsfood.recipe.service.RecipeService;
import com.example.snsfood.util.PageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberRecipeController extends BaseController {

    private final RecipeService recipeService;
    private final CategoryService categoryService;

    //Api-recipe list Get
    @GetMapping("/recipe/api-recipe")
    public String list(Model model, RecipeParam parameter) {

        List<RecipeDto> recipeDtos = recipeService.list(parameter);
        long totalCount = 0;

        if (!CollectionUtils.isEmpty(recipeDtos)) {
            totalCount = recipeDtos.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", recipeDtos);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "recipe/api-recipe";
    }

    //Api-recipe detail Get
    @GetMapping("/recipe/{id}")
    public String recipeDetail(Model model, RecipeParam parameter) {

        RecipeDto detail = recipeService.frontDetail(parameter.getRcpSeq());
        model.addAttribute("detail", detail);

        return "recipe/detail";
    }

    //My-recipe list Get
    @GetMapping("/recipe/my-recipe")
    public String list2(Model model, Principal principal, MyRecipeParam parameter) {

        String userId = principal.getName();
        parameter.setUserId(userId);
        List<MyRecipeDto> list = recipeService.myRecipeList(parameter);
        long totalCount = 0;

        if (!CollectionUtils.isEmpty(list)) {
            totalCount = list.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", list);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "recipe/my-recipe";
    }

    @GetMapping("/recipe/my-detail")
    public String myRecipeDetail(Model model, MyRecipeParam parameter) {

        MyRecipeDto detail = recipeService.recipeDetail(parameter.getId());
        model.addAttribute("detail", detail);

        return "recipe/my-detail";
    }

    //My-recipe add Get
    @GetMapping(value = {"/recipe/add", "/recipe/edit"})
    public String add(Model model, HttpServletRequest request, RecipeInput parameter) {


        model.addAttribute("category", categoryService.list());

        boolean editMode = request.getRequestURI().contains("/edit");
        MyRecipeDto detail = new MyRecipeDto();

        if (editMode) {
            long id = parameter.getId();
            MyRecipeDto myRecipeDto = recipeService.getById(id);
            if (myRecipeDto == null) {
                model.addAttribute("message", "레시피 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = myRecipeDto;
        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "recipe/add";
    }

    //My Recipe add Post
    @PostMapping(value = {"/recipe/add", "/recipe/edit"})
    public String addSubmit(Model model, HttpServletRequest request,
                            RecipeInput parameter, Principal principal,
    MultipartFile file) {
        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {

            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "/Users/hominbag/Desktop/dev/snsfood/files";
            String baseUrlPath = "/files";

            String[] arrFilename =  getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

            saveFilename = arrFilename[0];
            urlFilename = arrFilename[1];

            try {
                File newFile = new File(saveFilename);
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
            } catch (IOException e) {
                log.info("##################################################");
                log.info(e.getMessage());
            }
        }

        parameter.setFilename(saveFilename);
        parameter.setUrlFilename(urlFilename);




        String userId = principal.getName();
        parameter.setUserId(userId);
        boolean editMode = request.getRequestURI().contains("/edit");

        if (editMode) {
            long id = parameter.getId();
            MyRecipeDto myRecipeDto = recipeService.getById(id);

            if (myRecipeDto == null) {
                model.addAttribute("message", "레시피 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = recipeService.set(parameter);
        } else {
            boolean result = recipeService.add(parameter);
        }

        return "redirect:/recipe/my-recipe";
    }

    //My Recipe delete Post
    @PostMapping("/recipe/delete")
    public String del(Model model, HttpServletRequest request, RecipeInput parameter) {
        boolean result = recipeService.del(parameter.getIdList());

        return "redirect:/recipe/my-recipe";
    }


    private String[] getNewSaveFile(String baseLocalPath, String baseUrlPath, String originalFilename) {

        LocalDate now = LocalDate.now();

        String[] dirs = {
                String.format("%s/%d/", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d/", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())};

        String urlDir = String.format("%s/%d/%02d/%02d/", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File file = new File(dir);
            if (!file.isDirectory()) {
                file.mkdir();
            }
        }

        String fileExtension = "";
        if (originalFilename != null) {
            int dotPos = originalFilename.lastIndexOf(".");
            if (dotPos > -1) {
                fileExtension = originalFilename.substring(dotPos + 1);
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String newFilename = String.format("%s%s", dirs[2], uuid);
        String newUrlFilename = String.format("%s%s", urlDir, uuid);
        if (fileExtension.length() > 0) {
            newFilename += "." + fileExtension;
            newUrlFilename += "." + fileExtension;
        }

        return new String[]{newFilename, newUrlFilename};
    }


}
