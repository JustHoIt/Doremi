package com.example.snsfood.board.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; //게시판 id
    String userId; //게시판 작성자 id
    String categoryCode; //게시판 카테고리
    String title; //  게시글 제목;
    String email;



    String contents; //게시클 내용
    boolean doYn;

    LocalDateTime regDt; //작성 시간
    String fileName; //파일이름
    String urlFileName; //파일주소

}
