package com.example.snsfood.member.repository;



import com.example.snsfood.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {


    //이메일 인증키 생성
    Optional<Member> findByEmailAuthKey(String emailAuthkey);


    //비밀번호찾기
    Optional<Member> findByUserIdAndUserName(String userId, String userName);

    //비밀번호 리셋키 생성
    Optional<Member> findByResetPasswordKey(String resetPasswordKey);
}
