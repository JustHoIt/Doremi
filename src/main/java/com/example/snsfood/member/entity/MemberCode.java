package com.example.snsfood.member.entity;


public interface MemberCode {

    String MEMBER_STATUS_REQ = "REQ";//이용불가(이메일미인증)
    String MEMBER_STATUS_ING = "ING"; //이용가능상태
    String MEMBER_STATUS_STOP = "STOP";  //이용불가상태(정지)
    String MEMBER_STATUS_WITHDRAW = "WITHDRAW";  //이용불가상태(탈퇴)

}
