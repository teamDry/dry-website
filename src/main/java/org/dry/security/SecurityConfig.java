package org.dry.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 로그인 페이지 설정
        http.formLogin((formLogin) -> formLogin
                .usernameParameter("id")
                .passwordParameter("password")
                .loginPage("/member/login")
                .defaultSuccessUrl("/member/index") // 로그인 성공 시 리다이렉트될 url 설정
                .failureUrl("/member/login?success=100"));  // 로그인 실패 시 리다이렉트될 url 설정

        // 로그아웃 설정
        http.logout((logout) -> logout
                .invalidateHttpSession(true)    // 로그아웃 시 http 세션 무효화 여부를 true로 설정하여 세션 정보 제거
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))  // 로그아웃 요청, 이 url 요청이 들어오면 자동으로 로그아웃 처리
                .logoutSuccessUrl("/member/login?success=200"));    // 로그아웃 성공 후 리다이렉트될 url 설정

        // 로그인
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder encoder() {
        // 비밀번호 암호화
        return new BCryptPasswordEncoder();
    }

}
