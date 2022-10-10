package com.example.snsfood.board.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; //게시판 id
    String userId; //게시판 작성자 id
    String categoryCode; //게시판 카테고리
    String title; //  게시글 제목;

    String contents; //게시클 내용

    long countView; //조회수
    boolean noticeYn; //공지여부

    LocalDateTime regDt; //작성 시간
    LocalDateTime udtDt; //수정 시간
    String fileName; //파일이름
    String urlFileName; //파일주소

}
