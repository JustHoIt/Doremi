package com.example.snsfood.configuration;

import com.example.snsfood.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.pattern.PathPattern;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final MemberService memberService;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final UserAuthenticationSuccessHandler userAuthenticationSuccessHandler;


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserAuthenticationFailureHandler getFailureHandler() {
        return new UserAuthenticationFailureHandler();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico", "/files/**");
        super.configure(web);
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();


        //비로그인시 이용가능한  도메인
        http.authorizeRequests()
                .antMatchers("/member/register"
                        , "/member/email-auth"
                        , "/member/find/password"
                        , "/member/find/password_result"
                        , "/member/find/password_reset"
                        , "/member/find/id"
                        , "/member/find/id_result"
                        , "/${pageContext.request.contextPath}/member/idCheck"
                        , "/member/idCheck"
                        , "/member/nicknameCheck"
                        , "/${pageContext.request.contextPath}/member/nicknameCheck"
                        , "/css/**")
                .permitAll(); //권한허용

        //어드민 계정에만 admin 도메인을 사용가능
        http.authorizeRequests()
                .antMatchers("/admin/**")
                .hasAuthority("ROLE_ADMIN");


        http.formLogin()
                .loginPage("/member/login")
                .successHandler(userAuthenticationSuccessHandler)
                .failureHandler(getFailureHandler())
                .permitAll();

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

        http.exceptionHandling()
                .accessDeniedPage("/error/denied");
        super.configure(http);


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService)
                .passwordEncoder(getPasswordEncoder());

        super.configure(auth);
    }

}
