package com.example.snsfood.main;

import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.compnets.MailComponents;
import com.example.snsfood.food.dto.FoodDto;
import com.example.snsfood.food.model.FoodParam;
import com.example.snsfood.food.service.FoodService;
import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;


// MainPage 클래스를 만드는 목적 =>
// Mapping을 하기 위해서 : 주소와(논리적인 주소 인터넷 주소) 물리적인 파일(프로그래밍) 매핑

// http://www.naver.com/new/list.do
// 하나의 주소에 대해서
// 어디서 매핑? 누가 매핑?
// 후보군? 클래스, 속성, 메소드

// http://localhost:8080/

@RequiredArgsConstructor
@Controller
public class MainController {
    // 스프링 -> MVC(View -> 템플릿엔진 화면에 내용을 출력(html)
    // .NET ->  MVC(View -> 출력)
    // python django -> MTV(Template -> 화면출력)
    // 백엔드 과정 -> V(화면에 보여진 부분) -> vmfhsxmdpsem rhkwjd
    // V -> HTML ==>  웹페이지가
    // V -> json ==> API

    //request : WEB -> SERVER
    //response : SERVER -> WEB


    private final MemberService memberService;

    private final MailComponents mailComponents;

    private final FoodService foodService;

    @GetMapping("/")
    public String index(HttpServletRequest httpServletRequest, Model model, FoodParam foodParam, MemberParam memberParam, Principal principal) {
        String userId = principal.getName();
        foodParam.setUserId(userId);
        List<FoodDto> foodDtoList = foodService.mainFoodList(foodParam);
        List<MemberDto> memberDtoList = memberService.memberList(memberParam);
        String searchValue = httpServletRequest.getParameter("searchValue");
        String searchType = httpServletRequest.getParameter("searchType");
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchValue", searchValue);
        model.addAttribute("memberList", memberDtoList);
        model.addAttribute("foodList", foodDtoList);

        return "index";

    }

//    @RequestMapping("/")
//    public String search(HttpServletRequest httpServletRequest, Model model){
//
//        String searchValue = httpServletRequest.getParameter("searchValue");
//        String searchType = httpServletRequest.getParameter("searchType");
//
//        return "/recipe/api-recipe";
//    }


    @RequestMapping("/error/denied")
    public String errorDenied() {

//        String email = "phm3128@naver.com";
//        String subjuect ="안녕하세요. 제로베이스 입니다.";
//        String text = "<p>안녕하세요</p><p>반갑습니다.</p>";
//        mailComponents.sendMail(email, subjuect, text);
//
        return "error/denied";
    }


}
