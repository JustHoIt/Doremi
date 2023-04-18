package com.example.snsfood.Reply.dto;

import com.example.snsfood.Reply.entity.Reply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ReplyDto {

    long id;
    long bid;
    String userId;

    String contents; //댓글 내용
    boolean openYn; //공개여부
    LocalDateTime regDt; //작성 시간
    LocalDateTime delDt; //삭제 시간

    long totalCount;


    public static ReplyDto of(Reply reply){
        return ReplyDto.builder()
                .id(reply.getId())
                .bid(reply.getBid())
                .userId(reply.getUserId())
                .openYn(reply.isOpenYn())
                .contents(reply.getContents())
                .regDt(reply.getRegDt())
                .delDt(reply.getDelDt())
                .build();
    }


}
