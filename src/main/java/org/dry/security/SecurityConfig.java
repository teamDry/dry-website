package org.dry.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorizeRequests ->     // 권한설정
                        authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/member/login")).permitAll()   // login, logout은 권한없이 접근 가능
                                .requestMatchers(new AntPathRequestMatcher("/member/**")).authenticated()  // 그 외 모든 접근은 인증 후 접근가능
                )
                .formLogin(formLogin ->      // 로그인 페이지 설정
                        formLogin
                                .loginPage("/member/login")
                                .usernameParameter("id")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/member/index")                        // 로그인 성공 시 리다이렉트될 url 설정
                                .failureUrl("/member/login?error=true") // 로그인 실패 시 리다이렉트될 url 설정
                )
                .logout(logout ->           // 로그아웃 설정
                        logout
                                .invalidateHttpSession(true)                                            // 로그아웃 시 http 세션 무효화 여부를 true로 설정하여 세션 정보 제거
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))    // 로그아웃 요청, 이 url 요청이 들어오면 자동으로 로그아웃 처리
                                .logoutSuccessUrl("/member/login?=success=200")                         // 로그아웃 성공 후 리다이렉트될 url 설정
                )
                .csrf(AbstractHttpConfigurer::disable)         // CSRF 보안 설정 비활성화 (설정유지가 좋다고 함)
                .userDetailsService(userDetailsService);

        return http.build();
    }


    @Bean
    public PasswordEncoder encoder() {
        // 비밀번호 암호화
        return NoOpPasswordEncoder.getInstance();
    }

}
