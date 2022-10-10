package com.example.snsfood.admin.controller;


import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.model.DonationInput;
import com.example.snsfood.admin.model.DonationParam;
import com.example.snsfood.admin.service.DonationService;
import com.example.snsfood.board.model.BoardInput;
import com.example.snsfood.food.dto.FoodDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
public class DonationController  extends BaseController {

    private final DonationService donationService;


    @GetMapping("/admin/donation/list.do")
    public String donationList(Model model, DonationParam parameter){
        List<DonationDto>donationDtoList = donationService.list(parameter);
        long totalCount = 0;

        if(donationDtoList != null && donationDtoList.size() > 0) {
            totalCount = donationDtoList.get(0).getTotalCount();

        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", donationDtoList);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);


        return "admin/donation/list";
    }

    @GetMapping("/admin/donation/{id}")
    public String donationDetail(Model model, DonationParam parameter){

        DonationDto detail = donationService.donationDetail(parameter.getId());
        model.addAttribute("detail",detail);
        return "admin/donation/detail";
    }

    @GetMapping(value = {"/admin/donation/write.do", "/admin/donation/edit.do"})
    public String donationAdd(Model model, HttpServletRequest request, DonationInput parameter){
        boolean editMode = request.getRequestURI().contains("/edit");
        DonationDto detail = new DonationDto();

        if(editMode) {
            long id = parameter.getId();
            DonationDto donationDto = donationService.getById(id);
            if(donationDto == null){
                model.addAttribute("message", "기부 게시글 정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = donationDto;
        }
        model.addAttribute("editMode",editMode);
        model.addAttribute("detail", detail);

        return "admin/donation/write";
    }


    @PostMapping(value = {"/admin/donation/write.do", "/admin/donation/edit.do"})
    public String donationAdd(Model model, HttpServletRequest request, DonationInput parameter,
                              Principal principal, MultipartFile file){

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

        parameter.setFileName(saveFilename);
        parameter.setUrlFileName(urlFilename);



        String userId = principal.getName();
        parameter.setUserId(userId);
        boolean editMode = request.getRequestURI().contains("/edit");

        if (editMode) {
            long id = parameter.getId();
            DonationDto donationDto = donationService.getById(id);

            if (donationDto == null) {
                model.addAttribute("message", "레시피 정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = donationService.set(parameter);
        } else {
            boolean result = donationService.add(parameter);
        }


        return "redirect:/admin/donation/list.do";

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
