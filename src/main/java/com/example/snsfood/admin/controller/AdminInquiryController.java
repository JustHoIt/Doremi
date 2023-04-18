package com.example.snsfood.admin.controller;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.AnswerParam;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.admin.service.InquiryService;
import com.example.snsfood.board.model.InquiryDto;
import com.example.snsfood.board.model.InquiryParam;
import com.example.snsfood.main.BaseController;
import com.example.snsfood.member.model.ServiceResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminInquiryController extends BaseController {
    private final InquiryService inquiryService;

    @GetMapping("/admin/inquiry/list.do{solved}")
    public String list(Model model, InquiryParam parameter, @RequestParam(required = false) String solved){
        List<InquiryDto> inquiryDtos = inquiryService.adminList(parameter, solved);
        long totalCount = 0;
        if (inquiryDtos != null && inquiryDtos.size() > 0) {
            totalCount = inquiryDtos.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());


        model.addAttribute("list", inquiryDtos);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager",pagerHtml);

        return "/admin/inquiry/list";

    }

    @GetMapping("/admin/inquiry/detail{id}")
    public String detail(Model model, InquiryParam parameter){
        InquiryDto detail = inquiryService.detail(parameter.getId());

        model.addAttribute("detail", detail);

        return  "admin/inquiry/detail";
    }

    @PostMapping("/admin/inquiry/detail{id}")
    public String answer(Model model, AnswerParam parameter){
        log.info(parameter.toString());
        ServiceResult result = inquiryService.answer(parameter);


        if(!result.isResult()){
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }
        return "redirect:/admin/inquiry/list.do";
    }

}
