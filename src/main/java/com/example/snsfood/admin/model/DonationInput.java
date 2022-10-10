package com.example.snsfood.admin.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DonationInput {

    long id; //게시판 id
    String userId; //게시판 작성자 id
    String title; //  게시글 제목;
    String contents; //게시클 내용
    int point;

    LocalDate expDt;
    String fileName; //파일이름
    String urlFileName; //파일주소

}
