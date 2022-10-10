package com.example.snsfood.food.controller;


import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.model.FoodInput;
import com.example.snsfood.food.model.FoodParam;
import com.example.snsfood.food.service.FoodService;
import com.example.snsfood.main.BaseController;
import com.example.snsfood.member.model.MemberInput;
import com.example.snsfood.member.model.ServiceResult;
import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
@Slf4j
public class FoodController extends BaseController {

    private final FoodService foodService;
    private final MemberService memberService;


    //Food R(list-read)
    @GetMapping("/food/list")
    public String list(Model model, Principal principal, FoodParam parameter) {
        String userId = principal.getName();
        parameter.setUserId(userId);
        List<FoodDto> foodDtos = foodService.list(parameter);
        long totalCount = 0;

        if (!CollectionUtils.isEmpty(foodDtos)) {
            totalCount = foodDtos.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", foodDtos);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        System.out.println(foodDtos);
        return "food/list";
    }

    //Food R(detail-read)
    @GetMapping("/food/detail{id}")
    public String foodDetail(Model model, FoodParam parameter) {

        FoodDto detail = foodService.frontDetail(parameter.getId());
        model.addAttribute("detail", detail);

        return "food/detail";
    }

    @PostMapping("/food/detail")
    public String foodUse(Model model, FoodInput foodInput,FoodParam foodParam, MemberInput memberInput, Principal principal){
        System.out.println("사용버튼 클릭");
        long foodId = foodParam.getId();
        foodInput.setId(foodId);
        ServiceResult result1 = foodService.useFood(foodInput);
        System.out.println(foodId +"이것은 식품 아이디입니다.");
        String userId = principal.getName();
        memberInput.setUserId(userId);
        ServiceResult result2 = memberService.useFood(memberInput);

        if(!result2.isResult()) {
            model.addAttribute("message",result2.getMessage());
            return "common/error";
        }
        if(!result1.isResult()){
            model.addAttribute("message",result1.getMessage());
            return "common/error";
        }

        return "redirect:/food/list";
    }

    //Food C, U(add, update)
    @GetMapping(value = {"/food/add", "/food/edit"})
    public String add(Model model, HttpServletRequest request, FoodInput parameter) {

        boolean editMode = request.getRequestURI().contains("/edit");
        FoodDto detail = new FoodDto();

        System.out.println(parameter.toString());

        if (editMode) {
            long id = parameter.getId();
            FoodDto foodDto = foodService.getById(id);
            if (foodDto == null) {
                model.addAttribute("message", "식품 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = foodDto;
        }
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);

        return "food/add";
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


    @PostMapping(value = {"/food/add", "/food/edit"})
    public String addSubmit(Model model, HttpServletRequest request, FoodInput parameter
            , Principal principal, MultipartFile file) {

        String saveFilename = "";
        String urlFilename = "";

        if (file != null) {

            String originalFilename = file.getOriginalFilename();
            String baseLocalPath = "/Users/hominbag/Desktop/dev/snsfood/files";
            String baseUrlPath = "/files";

            String[] arrFilename = getNewSaveFile(baseLocalPath, baseUrlPath, originalFilename);

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
            FoodDto foodDto = foodService.getById(id);

            if (foodDto == null) {
                model.addAttribute("message", "레시피 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = foodService.set(parameter);
        } else {
            boolean result = foodService.add(parameter);
        }

        return "redirect:/food/list";
    }

    @PostMapping("/food/delete")
    public String del(Model model, HttpServletRequest request, FoodInput parameter) {
        boolean result = foodService.del(parameter.getIdList());

        return "redirect:/food/list";
    }




    @PostMapping("/food/checkBarCode")
    @ResponseBody
    public Map<String, Object> barCodeCheck(@RequestParam("barCode") String barCode) {

        System.out.println(barCode.toString());



        return foodService.barCodeCheck(barCode);
    }


}
