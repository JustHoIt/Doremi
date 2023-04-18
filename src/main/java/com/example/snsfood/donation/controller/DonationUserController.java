package com.example.snsfood.donation.controller;


import com.example.snsfood.admin.dto.DonationDto;
import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.DonationInput;
import com.example.snsfood.admin.model.DonationParam;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.admin.service.DonationService;
import com.example.snsfood.main.BaseController;
import com.example.snsfood.member.model.MemberInput;
import com.example.snsfood.member.model.ServiceResult;
import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DonationUserController extends BaseController {

    private final DonationService donationService;
    private final MemberService memberService;


    @GetMapping("/donation/list")
    public String list(Model model, DonationParam parameter) {
        List<DonationDto> donationDtos = donationService.list(parameter);
        long totalCount = 0;


        if (!CollectionUtils.isEmpty(donationDtos)) {
            totalCount = donationDtos.get(0).getTotalCount();
        }

        String queryString = parameter.getQueryString();
        String pagerHtml = getPagerHtml(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString, parameter.getSearchType(), parameter.getSearchValue());
        model.addAttribute("list", donationDtos);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pager", pagerHtml);

        return "donation/list";
    }

    @GetMapping("/donation/detail{id}")
    public String donationDetail(Model model, DonationParam parameter, Principal principal, MemberParam memberParam) {
        String userId = principal.getName();
        memberParam.setUserId(userId);
        DonationDto detail = donationService.donationDetail(parameter.getId());
        MemberDto memberDto = memberService.detailInfo(memberParam);
        model.addAttribute("point", memberDto);
        model.addAttribute("detail", detail);
        return "donation/detail";
    }


    @PutMapping("/donation/userPut")
    @ResponseBody
    public String donationUser(@RequestParam("point") Integer point, @RequestParam("donationId") Integer donationId,
                               Principal principal){
        System.out.println(point);
        System.out.println(donationId);
        String userId = principal.getName();
//        memberInput.setUserId(userId);
        ServiceResult result1 = memberService.donation(userId, point);
        ServiceResult result2 = donationService.donation(donationId,point);


        return "redirect:/donation/detail?id="+donationId;
    }
}
