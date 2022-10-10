package com.example.snsfood.admin.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Donation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String userId;


    String title;

    @Column(length = 9999)
    String contents;

    LocalDate expDt; //종료기간
    LocalDateTime regDt; //작성 일자
    LocalDateTime udtDt;//수정일자

    String fileName;
    String urlFileName;
    int point;

}
