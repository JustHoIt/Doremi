package com.example.snsfood.service.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ServiceController {

    @GetMapping("/service/main")
    public String register() {

        System.out.println("로그인에 성공");

        return "service/main";
    }

}
