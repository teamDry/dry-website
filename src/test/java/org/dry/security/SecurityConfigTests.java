package org.dry.security;

import org.dry.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTests {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected AuthenticationManager authenticationManager;

    @DisplayName("로그인 정보를 보낼 시, config에서 정상적으로 처리 되는가?")
    @Test
    @WithMockUser(username = "test", password = "1234", roles = "0")    // 사용자를 임의로 생성
    void givenTestData_whenAuthenticating_thenIsAuthenticated() throws Exception {
        // given
        Member member = Member.of("test","1234","tester","test@gmail.com");
        CustomUserDetails testUserDetails = new CustomUserDetails(member);

        // when
        // 사용자를 모의로 인증되었다고 가정
        when(authenticationManager.authenticate(any())).thenReturn(new TestingAuthenticationToken(testUserDetails, "1234"));

        // then
        // /member/login 호출 시, security config(filterchain) 에서 정보를 가로채 UserDetailsService로 보내 db에서 아이디를 찾음
        // 있을 경우 CustomUserDetails로 사용자 정보를 보내는 과정 까지를 처리하고 있음
        // 해당 테스트는 인증이 되었다고 가정하고 있지만, 실제로 password 대조와 인증 처리 과정은 spring security 내부에서 자동으로 수행함
         mockMvc.perform(post("/member/login")
                        .param("id", "test")    // 사용자가 입력한 id 값
                        .param("password", "1234")) // 사용자가 입력한 password값
                .andExpect(SecurityMockMvcResultMatchers.authenticated());  // 정상적으로 인증 되었는가 확인
    }
}