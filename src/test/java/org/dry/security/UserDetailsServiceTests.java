package org.dry.security;

import org.dry.entity.Member;
import org.dry.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceTests {

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @DisplayName("DB에 해당 id가 있을때 정상적으로 UserDetails 받아 오는가?")
    @WithMockUser
    @Test
    void givenMockUser_whenLoadUserByUserNameMethod_thenMemberObjectIsNotNull() throws Exception {
        // given
        // 조회할 id
        String id = "Test";

        // 반환 값으로 가져올 객체정보
        Member member = Member.of("Test", "12345", "tester", "Test@gmail.com");

        // when
        // Mockito를 이용한 실제 로직 구현(DB 접속 및 id를 이용한 조회를 가로채 대신 해줌)
        when(memberMapper.selectMemberById(id)).thenReturn(member);

        CustomUserDetails result = userDetailsService.loadUserByUsername(id);

        // then
        // 구조가 잘못 되었다면, Null을 반환할 것
        assertNotNull("UserDetails is NotNull :" + result, result);
    }

    @DisplayName("DB에 해당 id가 없을때 정상적으로 예외처리를 하는가?")
    @Test
    void givenMockUser_whenLoadUserByUserName_thenThrowsException() {
        // given
        String id = "Test";

        // when
        // DB 조회시 null값을 반환하는 가정
        when(memberMapper.selectMemberById(id)).thenReturn(null);

        // then
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername(id);
        });
    }
}
