package com.example.snsfood.admin.mailTemplate.controller;


import com.example.snsfood.admin.mailTemplate.dto.MailTemplateDto;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateInput;
import com.example.snsfood.admin.mailTemplate.model.MailTemplateParam;
import com.example.snsfood.admin.mailTemplate.service.MailTemplateService;
import com.example.snsfood.admin.controller.BaseController;
import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller

public class AdminMailTemplateController extends BaseController {
    
    private final MemberService memberService;
    private final MailTemplateService mailTemplateService;
    
    @GetMapping("/admin/mailTemplate/list.do")
    public String list(Model model, MailTemplateParam parameter) {
        
        parameter.init();
        List<MailTemplateDto> members = mailTemplateService.list(parameter);
        
        long totalCount = 0;
        if (members != null && members.size() > 0) {
            totalCount = members.get(0).getTotalCount();
        }
        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        
        model.addAttribute("list", members);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);
        
        
        return "admin/mailTemplate/list";
    }
    
    @GetMapping(value = {"/admin/mailTemplate/add.do", "/admin/mailTemplate/edit.do"})
    public String add(Model model, HttpServletRequest request
            , MailTemplateInput parameter) {
    
        boolean editMode = request.getRequestURI().contains("/edit.do");
        MailTemplateDto detail = new MailTemplateDto();
    
        if (editMode) {
            long id = parameter.getId();
            MailTemplateDto mailTemplateDto = mailTemplateService.getById(id);
            if (mailTemplateDto == null) {
                // error 처리
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            detail = mailTemplateDto;
        }
    
        model.addAttribute("editMode", editMode);
        model.addAttribute("detail", detail);
    
        return "admin/mailTemplate/add";
    }
    
    @PostMapping(value = {"/admin/mailTemplate/add.do", "/admin/mailTemplate/edit.do"})
    public String addSubmit(Model model, HttpServletRequest request
            , MailTemplateInput parameter) {
        
        boolean editMode = request.getRequestURI().contains("/edit.do");
        
        if (editMode) {
            long id = parameter.getId();
//            MailTemplateDto existMailTemplate = mailTemplateService.getById(id);
            MailTemplateDto mailTemplateDto = mailTemplateService.getById(id);
            if (mailTemplateDto == null) {
                model.addAttribute("message", "강좌정보가 존재하지 않습니다.");
                return "common/error";
            }
            boolean result = mailTemplateService.set(parameter);
            
        } else {
            boolean result = mailTemplateService.add(parameter);
        }
        
        return "redirect:/admin/mailTemplate/list.do";
    }
    
    @PostMapping("/admin/mailTemplate/delete.do")
    public String del(Model model, HttpServletRequest request) {
        
        return "redirect:/admin/mailTemplate/list.do";
    }
    
    
}
