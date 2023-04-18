package com.example.snsfood.Reply.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReplyInput {


    long id;
    long bid;
    String userId;

    String contents; //댓글 내용
}
