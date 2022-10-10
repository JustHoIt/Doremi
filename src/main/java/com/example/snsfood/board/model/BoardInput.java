package com.example.snsfood.board.model;

import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class BoardInput {

    long id; //게시판 id
    String userId; //게시판 작성자 id
    String categoryCode; //게시판 카테고리
    String title; //  게시글 제목;
    String contents; //게시클 내용

    String fileName; //파일이름
    String urlFileName; //파일주소

}
