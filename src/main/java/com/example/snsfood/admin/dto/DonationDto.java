package com.example.snsfood.admin.dto;


import com.example.snsfood.admin.entity.Category;
import com.example.snsfood.admin.entity.Donation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonationDto {
    long id;
    String userId;

    String title;

    String contents;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate expDt; //종료기간
    LocalDateTime regDt; //작성 일자
    LocalDateTime udtDt;

    String fileName;
    String urlFileName;
    int point;

    long totalCount;
    long seq;

    public static DonationDto of(Donation donation) {
        return DonationDto.builder()
                .id(donation.getId())
                .userId(donation.getUserId())
                .title(donation.getTitle())
                .contents(donation.getContents())
                .expDt(donation.getExpDt())
                .regDt(donation.getRegDt())
                .fileName(donation.getFileName())
                .urlFileName(donation.getUrlFileName())
                .point(donation.getPoint())
                .build();
    }


}
