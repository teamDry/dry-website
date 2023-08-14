package org.dry.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.dry.entity.Member;
import org.dry.service.MemberService;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("MemberRestControllerTest")
@WebMvcTest(MemberRestController.class)
public class MemberRestControllerTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    // MvcTest에는 Service layer를 포함하지 않아 Mockbean을 이용해 가짜 Service를 만들어 주입한다.
    @MockBean
    private final MemberService memberService;



    // 생성자가 하나만 있는 경우 자동으로 @Autowired 적용 Spring Framework 4.3v ~
    @Autowired
    public MemberRestControllerTest(MockMvc mockMvc, ObjectMapper objectMapper, MemberService memberService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.memberService = memberService;
    }

    @DisplayName("MemberRestController sign-up Test")
    @Test
    void givenTestData_whenHandOverInsertMember_thenWorksFine() throws Exception {
        // given
        Member newMember = Member.of("Test", "1234", "Tester", "test@naver.com");
        // 가짜 서비스를 만들어 insertMember를 구현 후 newMember를 반환하도록 설정
        when(memberService.insertMember(newMember)).thenReturn(newMember);

        // when
        mockMvc.perform(post("/member/sign-up")         // /member/sign-up 을 post로 요청
                .contentType(MediaType.APPLICATION_JSON)           // 요청 본문은 json 타입으로 설정
                .content(objectMapper.writeValueAsString(newMember)))   // newMeber 객체를 json형태로 변환하여 요청 본문에 포함
                .andExpect(status().isOk())                      // 응답 상태 확인 (200: Ok, 404: error)
                .andExpect(content().json(objectMapper.writeValueAsString(newMember))); // when에서 응답을 newMember로 설정 > json으로 변환한 newMember와 일치하는지 확인
        // then

    }

    @DisplayName("MemberRestController login Test")
    @Test
    void givenTestData_whenHandOverToLogin_thenWorksFine() throws Exception {
        // given
        Member member = Member.of("Test", "1234", "Tester", "test@naver.com");
        IdAndPassword idAndPassword = new IdAndPassword("Test", "1234");
        when(memberService.login(idAndPassword)).thenReturn(member);

        // when
        mockMvc.perform(post("/member/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(idAndPassword)))
                .andExpect(status().isOk());            // 응답상태 확인 까지

        // then
    }
}
