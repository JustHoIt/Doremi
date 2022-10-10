package com.example.snsfood.member.service;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.member.model.FindIdInput;
import com.example.snsfood.member.model.MemberInput;
import com.example.snsfood.member.model.ResetPasswordInput;
import com.example.snsfood.member.model.ServiceResult;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;

public interface MemberService extends UserDetailsService {


    //회원가입
    boolean register(MemberInput parameter);


//   uuid에 해당하는 계정을 활성화,  @param uuid @return
    boolean emailAuth(String uuid);


    //입력한 이메일로 비밀번호 초기화 정보를 전송
    boolean sendResetPassword(ResetPasswordInput parameter);

//    Map<String, Object> findId(Map<String, Object> param);


    //입력받은 uuid 계정의 password로 업데이트 함함
   boolean resetPassword(String uuid, String password);


   //입력받은 uuid값이 유효한지 확인
    boolean checkResetPassword(String uuid);

    //관리자 페이지에서 회원 목록 리턴
    List<MemberDto> list(MemberParam parameter);


    //메인 페이지 랭킹 리스트
    List<MemberDto> memberList(MemberParam memberParam);




    //회원관리 detail
    MemberDto detail(String userId);

    //회원상태 변경
   boolean updateStatus(String userId, String userStatus);


   //회원비밀번호 초기화화
    boolean updatePassword(String userId, String userPw);


    //회원정보창 정보변경
    ServiceResult updateMemberInfo(MemberInput parameter);

    //식품 사용 포인트 지급
    ServiceResult useFood(MemberInput memberInput);

    //회원정보 비밀번호 변경
    ServiceResult updateMemberPassword(MemberInput parameter);


    //회원탈퇴 로직
    ServiceResult withdraw(String userId, String password);


    int idCheck(String id);

    int nicknameCheck(String nickname);



}
