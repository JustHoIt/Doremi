package com.example.snsfood.board.dto;


import com.example.snsfood.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BoardDto {
    long id; //게시판 id
    String userId; //게시판 작성자 id
    String categoryCode; //게시판 카테고리
    String title; //  게시글 제목;

    @Column(length = 9999)
    String contents; //게시클 내용

    long countView; //조회수
    boolean noticeYn; //공지여부

    LocalDateTime regDt; //작성 시간
    LocalDateTime udtDt; //수정 시간
    String fileName; //파일이름
    String urlFileName; //파일주소

    long totalCount;
    long seq;

    public static BoardDto of(Board board){

        return BoardDto.builder()
                .id(board.getId())
                .userId(board.getUserId())
                .categoryCode(board.getCategoryCode())
                .title(board.getTitle())
                .contents(board.getContents())
                .countView(board.getCountView())
                .noticeYn(board.isNoticeYn())
                .regDt(board.getRegDt())
                .udtDt(board.getUdtDt())
                .fileName(board.getFileName())
                .urlFileName(board.getUrlFileName())
                .build();
    }
}
