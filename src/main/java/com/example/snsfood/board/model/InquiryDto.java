package com.example.snsfood.board.model;

import com.example.snsfood.board.entity.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDto {

    long id; //게시판 id
    String userId; //게시판 작성자 id
    String categoryCode; //게시판 카테고리
    String title; //  게시글 제목;
    String email;
    String contents; //게시클 내용
    String fileName; //파일이름
    String urlFileName; //파일주소
    boolean doYn;

    long seq;
    long totalCount;

    public static InquiryDto of(Inquiry inquiry){
        return InquiryDto.builder()
                .id(inquiry.getId())
                .userId(inquiry.getUserId())
                .email(inquiry.getEmail())
                .categoryCode(inquiry.getCategoryCode())
                .title(inquiry.getTitle())
                .contents(inquiry.getContents())
                .fileName(inquiry.getFileName())
                .urlFileName(inquiry.getUrlFileName())
                .build();
    }

}
