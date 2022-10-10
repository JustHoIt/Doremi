package com.example.snsfood.member.service.impl;


import com.example.snsfood.admin.dto.MemberDto;
import com.example.snsfood.admin.mailTemplate.entity.MailTemplate;
import com.example.snsfood.admin.mailTemplate.repository.MailTemplateRepository;
import com.example.snsfood.admin.mapper.MemberMapper;
import com.example.snsfood.admin.model.MemberParam;
import com.example.snsfood.compnets.MailComponents;
import com.example.snsfood.member.Mapper.RankingMapper;
import com.example.snsfood.member.Mapper.RegisterMapper;
import com.example.snsfood.member.entity.Member;
import com.example.snsfood.member.entity.MemberCode;
import com.example.snsfood.member.excepion.MemberNotEmailAuthException;
import com.example.snsfood.member.excepion.MemberStopUserException;
import com.example.snsfood.member.model.MemberInput;
import com.example.snsfood.member.model.ResetPasswordInput;
import com.example.snsfood.member.model.ServiceResult;
import com.example.snsfood.member.repository.MemberLoginHistoryRepository;
import com.example.snsfood.member.repository.MemberRepository;
import com.example.snsfood.member.service.MemberService;
import com.example.snsfood.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailComponents mailComponents;

    private final MailTemplateRepository mailTemplateRepository;

    private final MemberMapper memberMapper;

    private final RankingMapper rankingMapper;

    private final RegisterMapper registerMapper;

    private final PasswordUtils passwordUtils;
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;


    //회원가입
    @Override
    public boolean register(MemberInput parameter) {

        Optional<Member> optionalMember = memberRepository.findById(parameter.getUserId());
        if (optionalMember.isPresent()) {
            //데이터베이스에 이미 입력한 userId의 값이 존재
            return false;
        }

        String encPassword = BCrypt.hashpw(parameter.getUserPw(), BCrypt.gensalt());
        String uuid = UUID.randomUUID().toString();

        Member member = Member.builder()
                .userId(parameter.getUserId())
                .userPw(encPassword)
                .nickname(parameter.getNickname())
                .userName(parameter.getUserName())
                .userPhoneNumber(parameter.getUserPhoneNumber())
                .regDt(LocalDateTime.now())
                .emailAuthYn(false)
                .emailAuthKey(uuid)
                .userStatus(Member.MEMBER_STATUS_REQ)
                .zipcode(parameter.getZipcode())
                .address(parameter.getAddress())
                .addressDetail(parameter.getAddressDetail())
                .build();
        memberRepository.save(member);


        mailTemplateRepository.findByMailTemplateKey(MailTemplate.MAIL_TEMPLATE_KEY_MEMBER_REGISTER).ifPresent(e -> {
            String email = parameter.getUserId();
            String userId = parameter.getUserId();
            String subject = e.getTitle();
            String text = e.getContents()
                    .replace("####URL_AUTH####","<div><a target='_blank' href='http://localhost:8080/member/email-auth?id=" + uuid + "'> 이메일 인증 </a></div>");

            mailComponents.sendMail(email, subject, text);
        });

        return true;
    }


    //이메일인증
    @Override
    public boolean emailAuth(String uuid) {

        Optional<Member> optionalMember = memberRepository.findByEmailAuthKey(uuid);
        if (!optionalMember.isPresent()) { //정
            return false;
        }
        Member member = optionalMember.get();

        if (member.isEmailAuthYn()) { //이미 인증한경우에 false
            return false;
        }
        member.setUserStatus(member.MEMBER_STATUS_ING);
        member.setEmailAuthYn(true);
        member.setEmailAuthDt(LocalDateTime.now());
        memberRepository.save(member);
        return true;
    }

    //재설정 이메일 보내기
    @Override
    public boolean sendResetPassword(ResetPasswordInput parameter) {
        Optional<Member> optionalMember = memberRepository
                .findByUserIdAndUserName(parameter.getUserId()
                        , parameter.getUserName());
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");

        }

        Member member = optionalMember.get();

        String uuid = UUID.randomUUID().toString();
        member.setResetPasswordKey(uuid);
        member.setResetPasswordLimitDt(LocalDateTime.now().plusHours(1)); //1시간동안만 유효
        memberRepository.save(member);




        mailTemplateRepository.findByMailTemplateKey(MailTemplate.MAIL_TEMPLATE_KEY_MEMBER_RESET).ifPresent(e -> {
            String email = parameter.getUserId();
            String subject = e.getTitle();
            String text = e.getContents().replace("####URL_AUTH####", "<div><a target='_blank' href='http://localhost:8080/member/find/password_reset?id=" + uuid + "'> 비밀번호 변경 </a></div>");

            mailComponents.sendMail(email, subject, text);
        });

        return true;
    }


    //패스워드 재설정(유저)
    @Override
    public boolean resetPassword(String uuid, String password) {
        System.out.println("resetPassword 실행됨 " + password);
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);


        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }

        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }


        System.out.println("resetPassword 저장준비");
        String encPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        member.setUserPw(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        memberRepository.save(member);

        return true;
    }


    //재설정 비밀번호 사이트 유효검사
    @Override
    public boolean checkResetPassword(String uuid) {
        Optional<Member> optionalMember = memberRepository.findByResetPasswordKey(uuid);
        if (!optionalMember.isPresent()) {
            return false;
        }

        Member member = optionalMember.get();

        if (member.getResetPasswordLimitDt() == null) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        if (member.getResetPasswordLimitDt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("유효한 날짜가 아닙니다.");
        }
        return true;
    }


    //회원리스트(어드민)
    @Override
    public List<MemberDto> list(MemberParam parameter) {

        long totalCount = memberMapper.selectListCount(parameter);
        List<MemberDto> list = memberMapper.selectList(parameter);

        if (!CollectionUtils.isEmpty(list)) {
            int i = 0;
            for (MemberDto x : list) {
                x.setTotalCount(totalCount);
                x.setSeq(totalCount - parameter.getPageStart() - i);
                i++;
            }
        }

        return list;
    }


    //detail
    @Override
    public MemberDto detail(String userId) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return null;
        }
        Member member = optionalMember.get();
        MemberDto memberDto = MemberDto.of(member);
        memberLoginHistoryRepository.findByUserId(member.getUserId()).ifPresent(e -> {
            memberDto.setLoginHistoryList(e);
        });

        return memberDto;
    }

    @Override
    public boolean updateStatus(String userId, String userStatus) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        member.setUserStatus(userStatus);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean updatePassword(String userId, String parameter) {

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("No matching user found.");
        }

        Member member = optionalMember.get();

        String encPassword = BCrypt.hashpw(parameter, BCrypt.gensalt());
        member.setUserPw(encPassword);
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null); //오류생기면 여기가 문제.
        memberRepository.save(member);

        return true;

    }

    @Override
    public ServiceResult updateMemberInfo(MemberInput parameter) {
        //회원정보 가져오는 영역<
        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();
        // 여기까지>

        member.setUserPhoneNumber(parameter.getUserPhoneNumber());
        member.setUdtDt(LocalDateTime.now());
        member.setZipcode(parameter.getZipcode());
        member.setAddress(parameter.getAddress());
        member.setAddressDetail(parameter.getAddressDetail());
        memberRepository.save(member);

        return new ServiceResult();

    }

    @Override
    public ServiceResult useFood(MemberInput memberInput) {
        String userId = memberInput.getUserId();
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }
        memberMapper.givePoint(userId);
        return new ServiceResult();
    }

    @Override
    public ServiceResult updateMemberPassword(MemberInput parameter) {

        String userId = parameter.getUserId();

        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (!PasswordUtils.equals(parameter.getUserPw(), member.getUserPw())) {
            return new ServiceResult(false, "현재 비밀번호가 일치하지 않습니다.");
        }

        String encPassword = PasswordUtils.encPassword(parameter.getNewPassword());
        member.setUserPw(encPassword);
        memberRepository.save(member);

        return new ServiceResult(true);
    }

    @Override
    public ServiceResult withdraw(String userId, String password) {
        Optional<Member> optionalMember = memberRepository.findById(userId);
        if (!optionalMember.isPresent()) {
            return new ServiceResult(false, "회원정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(!PasswordUtils.equals(password, member.getUserPw())){
            return new ServiceResult(false, "비밀번호가 일치하지 않습니다.");
        }

        member.setUserName("삭제회원");
        member.setUserPhoneNumber("");
        member.setUserPw(null);
        member.setRegDt(null);
        member.setUdtDt(null);
        member.setEmailAuthYn(false);
        member.setEmailAuthDt(null);
        member.setEmailAuthKey("");
        member.setResetPasswordKey("");
        member.setResetPasswordLimitDt(null);
        member.setUserStatus(MemberCode.MEMBER_STATUS_WITHDRAW);
        member.setZipcode("");
        member.setAddress("");
        member.setAddressDetail("");
        memberRepository.save(member);

        return  new ServiceResult();

    }

    @Override
    public int idCheck(String id) {
        int cnt = registerMapper.idCheck(id);
        System.out.println("cnt : " + cnt);

        return cnt;
    }

    @Override
    public int nicknameCheck(String nickname) {
        int cnt = registerMapper.nicknameCheck(nickname);
        System.out.println("cnt : " + cnt);

        return cnt;
    }

    @Override
    public List<MemberDto> memberList(MemberParam memberParam) {
        List<MemberDto> list = rankingMapper.selectList(memberParam);



        return list;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> optionalMember = memberRepository.findById(username);
        if (!optionalMember.isPresent()) {
            throw new UsernameNotFoundException("회원 정보가 존재하지 않습니다.");
        }

        Member member = optionalMember.get();

        if (Member.MEMBER_STATUS_REQ.equals(member.getUserStatus())) {
            throw new MemberNotEmailAuthException("이메일 활성화 이후에 로그인을 해주세요.");
        }

        if (Member.MEMBER_STATUS_STOP.equals(member.getUserStatus())) {
            throw new MemberStopUserException("정지된 회원 입니다.");
        }

        if (Member.MEMBER_STATUS_WITHDRAW.equals(member.getUserStatus())) {
            throw new MemberStopUserException("탈퇴된 회원 입니다.");
        }

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        if (member.isAdminYn()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        return new User(member.getUserId(), member.getUserPw(), grantedAuthorities);
    }

//    @Override
//    public Map<String, Object> findId( Map<String, Object> param) {
//        Map<String, Object> rs = new HashMap<>();
//        String name = (String) param.get("userName");
//        String email = (String) param.get("userEmail");
//
//        Member member = FindIdMapper.findByUserNameAndUserEmail(name, email);
//
//        if(member == null){
//            rs.put("message","일치하는 회원이 없습니다");
//            return rs;
//        }
//        rs.put("message", "회원님의 아이디는(" + member.getUserId() + ")입니다.");
//        return  rs;
//    }
}
