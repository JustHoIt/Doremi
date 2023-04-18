package com.example.snsfood.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IntroduceController {
    @GetMapping("/introduce/intro")
    public String doInquiry(Model model){

        return "introduce/intro";

    }


}
