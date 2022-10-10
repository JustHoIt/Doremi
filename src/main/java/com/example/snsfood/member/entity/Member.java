package com.example.snsfood.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode {
    @Id
    private String userId;
    private String userPw;
    private String userName;
    private String userPhoneNumber;
    private LocalDateTime regDt;
    private LocalDateTime udtDt; // 회원정보 수정 날짜

    private String emailAuthKey;
    private boolean emailAuthYn;
    private LocalDateTime emailAuthDt;

    private int point; //보유포인트
    private int donatedPoint; //기부누적
    private String nickname; //별명


    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    private boolean adminYn;
    private String userStatus;
    private LocalDateTime userLoginLog;

    private String zipcode;
    private String address;
    private String addressDetail;



    //관리자 여부를 저장할 것인지.
    //회원에 따른 ROLE을 지정할 것인지.

    public String getRegDtText() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return regDt != null ? regDt.format(formatter) : "";
    }

    public String getUdtText() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        return udtDt != null ? udtDt.format(formatter) : "";
    }


}
