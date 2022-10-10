package com.example.snsfood.configuration;

import com.example.snsfood.member.entity.MemberLoginHistory;
import com.example.snsfood.member.repository.MemberLoginHistoryRepository;
import com.example.snsfood.member.repository.MemberRepository;
import com.example.snsfood.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final MemberRepository memberRepository;
    private final MemberLoginHistoryRepository memberLoginHistoryRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String userId = authentication.getName();

        memberRepository.findById(userId).ifPresent(e -> {
            e.setUserLoginLog(LocalDateTime.now());
            memberRepository.save(e);
        });

        String userAgent = RequestUtils.getUserAgent(request);
        String clientIp = RequestUtils.getClientIP(request);
        memberLoginHistoryRepository.save(MemberLoginHistory.builder()
            .ipAddr(clientIp)
            .userAgent(userAgent)
            .userId(userId)
            .regDt(LocalDateTime.now())
            .build());

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
