package com.example.snsfood.member.controller;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.member.model.MemberInput;
import com.example.snsfood.member.model.ResetPasswordInput;
import com.example.snsfood.member.model.ServiceResult;
import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class MemberController {

    @Autowired
    private final MemberService memberService;


    //회원가입
    @GetMapping("/member/register")
    public String register() {

        System.out.println("request get!!!");

        return "member/register";
    }


    @PostMapping("/member/idCheck")
    @ResponseBody
   public int idCheck(@RequestParam("id") String id) {

        System.out.println(id);

        return memberService.idCheck(id);
    }


    @PostMapping("/member/nicknameCheck")
    @ResponseBody
    public int nicknameCheck(@RequestParam("nickname") String nickname) {

        System.out.println(nickname.toString());

        return memberService.nicknameCheck(nickname);
    }





    //회원가입 post
    @PostMapping("/member/register")
    public String registerSubmit(Model model,
                                 HttpServletRequest request, MemberInput parameter) {

        boolean result = memberService.register(parameter);

        model.addAttribute("result", result);


        return "member/register_complete";
    }

    //이메일 인증
    @GetMapping("/member/email-auth")
    public String emilAuth(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");
        System.out.println(uuid);

        boolean result = memberService.emailAuth(uuid);
        model.addAttribute("result", result);

        return "member/email_auth";
    }

    //회원정보
    @GetMapping("/member/info")
    public String memberInfo(Model model, Principal principal) {


        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);
        System.out.println(detail.toString());
        model.addAttribute("detail", detail);

        return "member/info";
    }

    @PostMapping("/member/info")
    public String memberSubmit(Model model, MemberInput parameter, Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);
        ServiceResult result = memberService.updateMemberInfo(parameter);
        if(!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";
    }



    @GetMapping("/member/info-password-update")
    public String memberPassword(Model model, Principal principal) {


        String userId = principal.getName();
        MemberDto detail = memberService.detail(userId);

        model.addAttribute("detail",detail);

        return "member/info-password-update";
    }

    @PostMapping("/member/info-password-update")
    public String memberPasswordSubmit(Model model
            , MemberInput parameter
            , Principal principal) {

        String userId = principal.getName();
        parameter.setUserId(userId);

        ServiceResult result = memberService.updateMemberPassword(parameter);
        if (!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/info";

    }


    //로그인
    @RequestMapping("/member/login")
    public String memberLogin() {



        return "member/login";
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //비밀번호찾기
    @GetMapping("/member/find/password")
    public String findPassword() {

        return "member/find/password";
    }

    @PostMapping("/member/find/password")
    public String findPasswordSubmit(Model model, ResetPasswordInput parameter) {
        boolean result = false;
        try {
            result = memberService.sendResetPassword(parameter);
        } catch (Exception e) {
        }
        model.addAttribute("result", result);

        return "member/find/password_result";
    }


    @GetMapping("/member/find/password_reset")
    public String resetPassword(Model model, HttpServletRequest request) {

        String uuid = request.getParameter("id");

        boolean result = memberService.checkResetPassword(uuid);
        model.addAttribute("result", result);

        return "member/find/password_reset";
    }


    // password_reset 에서 form 태그 데이터 받아오기
    @PostMapping("/member/find/password_reset")
    public String resetPasswordSubmit(Model model, ResetPasswordInput parameter) {
        System.out.println(parameter);
        boolean result = false;
        try {
            result = memberService.resetPassword(parameter.getId(),
                    parameter.getPassword());
        } catch (Exception e) {
        }

        model.addAttribute("result", result);

        return "member/find/password_reset_result";
    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@


    @GetMapping("/member/find/id")
    public String findId() {

        return "member/find/id";
    }
//
//   @RequestMapping("/member/find/id")
//   public String findIdSubmit(@RequestParam Map<String, Object> param, Model model){
//        Map<String, Object> rs = memberService.findId(param);
//
//
//   }



    //회원탈퇴 페이지
    @GetMapping("/member/withdraw")
    public String memberWithdraw(Model model) {


        return "member/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String memberWithdrawSubmit(Model model, MemberInput parameter, Principal principal) {

        String userId = principal.getName();

        ServiceResult result = memberService.withdraw(userId, parameter.getUserPw());
        if(!result.isResult()) {
            model.addAttribute("message", result.getMessage());
            return "common/error";
        }

        return "redirect:/member/logout";
    }


}
