package com.example.snsfood.Reply.controller;


import com.example.snsfood.Reply.model.ReplyInput;
import com.example.snsfood.Reply.model.ReplyParam;
import com.example.snsfood.Reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
@Controller
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/write")
    public String replyWrite(Model model, HttpServletRequest request, Principal principal, ReplyInput parameter){
        System.out.println(parameter.toString());

        boolean result = replyService.add(parameter);
        String bid = String.valueOf(parameter.getBid());
        return "redirect:/board/detailBoard?id=" + bid;
    }




}
