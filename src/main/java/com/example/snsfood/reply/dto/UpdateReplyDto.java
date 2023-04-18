package com.example.snsfood.reply.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UpdateReplyDto {
    private Long boardId2;
    private Long replyId;
    private String editRepVal;
}
