package com.example.snsfood.Reply.entity;


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
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id; //댓글 id
    String userId; //작성자 id
    long bid; //게시글 고유 id
    @Column(length = 50)
    String contents; //댓글 내용
    boolean openYn; //공개여부
    LocalDateTime regDt; //작성 시간
    LocalDateTime delDt; //수정 시간

}
