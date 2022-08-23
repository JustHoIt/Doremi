package com.example.snsfood.course.controller;


import com.example.snsfood.admin.service.CategoryService;
import com.example.snsfood.course.model.ServiceResult;
import com.example.snsfood.course.model.TakeCourseInput;
import com.example.snsfood.course.service.CourseService;
import com.example.snsfood.model.ResponseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class ApiCourseController extends BaseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    //User 수강신청 axios api
    @PostMapping("/api/course/req.api")
    public ResponseEntity<?> courseReq(Model model
            , @RequestBody TakeCourseInput parameter
            , Principal principal) {

        parameter.setUserId(principal.getName());

        ServiceResult result = courseService.req(parameter);
        if (!result.isResult()) {
            ResponseResult responseResult = new ResponseResult(false, result.getMessage());
            return ResponseEntity.ok().body(responseResult);
        }

        ResponseResult responseResult = new ResponseResult(true);
        return ResponseEntity.ok().body(responseResult);
    }
}


