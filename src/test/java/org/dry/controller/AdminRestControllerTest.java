package org.dry.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Id;
import org.dry.entity.Admin;
import org.dry.service.AdminService;
import org.dry.util.TestUtil;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Rest Controller - Admin")
@WebMvcTest(AdminRestController.class)
class AdminRestControllerTest {
    private final MockMvc mvc;
    private final AdminRestController adminRestController;
    // 객체를 Json화 해주는 객체
    private final ObjectMapper objectMapper;

    @MockBean
    private AdminService adminService;

    public AdminRestControllerTest(
            @Autowired MockMvc mvc,
            @Autowired AdminRestController adminRestController,
            @Autowired ObjectMapper objectMapper) {
        this.mvc = mvc;
        this.adminRestController = adminRestController;
        this.objectMapper = objectMapper;
    }


    @DisplayName("[rest] [post] given - IdAndPassword / request - login / response - AdminObject")
    @Test
    void givenAdminObject_whenRequestLogin_thenResponseAdminObject() throws Exception {
        // given
        // adminService.login이 실행된다면 결과값으로 저걸 줘라
        when(adminService.login(any(IdAndPassword.class))).thenReturn(Admin.of("abc", "1234", "abcc", "a@b.c"));
        // RestController에 넘어갈 json data
        String jsonData = objectMapper.writeValueAsString(new IdAndPassword("abc", "1234"));
        // when & then
        mvc.perform(post("/api/admin/login")
                .contentType(MediaType.APPLICATION_JSON) // JSON 형식으로 보낼거고
                .content(jsonData)) // 보내는 JSON 객체는 이거임
                .andExpect(status().isOk()) // 응답코드가 OK(200)이냐?
                .andExpect(jsonPath("$.id").value("abc")) // 받은 json의 id 값이 abc인가?
                .andExpect(jsonPath("$.password").value("1234")); // 받은 json의 password 값이 "1234" 인가?
    }

    @DisplayName("[rest] [post] given -AdminObject / request - Sign-up / response - id?")
    @Test
    void givenAdminObject_whenRequestSignUp_thenResponseAdminId() throws Exception {
        // given
        when(adminService.signUp(any(Admin.class))).thenReturn(TestUtil.getDummyAdmin());
        String jsonData = objectMapper.writeValueAsString(TestUtil.getDummyAdmin());
        // when & then
        mvc.perform(post("/api/admin/sign-up")
                .contentType(MediaType.APPLICATION_JSON) // JSON 형식으로 보낼거고
                .content(jsonData))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("testAdmin"));
    }

    // id 중복 검사
    @DisplayName("[rest] [Get] given - NonTakenId / request - check-id / response - ok?")
    @Test
    void givenNonTakenAdminId_whenRequestIdCheck_thenResponseIsOk() throws Exception {
        // given
        // Service에 id 물어봤더니 그런 id를 가진 객체가 없다고 하면?
        when(adminService.isIdTaken(any(String.class))).thenReturn(false);
        String queryString = "id=testId1234";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-id?" + queryString))
                .andExpect(status().isOk());
    }

    @DisplayName("[rest] [Get] given - TakenId / request - check-id / response - badRequest?")
    @Test
    void givenTakenAdminId_whenRequestIdCheck_thenResponseIsBadRequest() throws Exception {
        // given
        // Service에 물어봤더니 그런 id를 가진 객체가 있다고 하면?
        when(adminService.isIdTaken(any(String.class))).thenReturn(true);
        String queryString = "id=testId1234";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-id?" + queryString))
                .andExpect(status().isBadRequest());
    }

    // 닉네임 중복 검사
    @DisplayName("[rest] [Get] given - NonTakenNickname / request - check-nickname / response - ok?")
    @Test
    void givenNonTakenAdminNickname_whenRequestNicknameCheck_thenResponseIsOk() throws Exception {
        // given
        // Service에 물어봤더니 그런 nickname을 가진 객체가 없다고 하면?
        when(adminService.isNicknameTaken(any(String.class))).thenReturn(false);
        String queryString = "nickname=testNickname1234";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-nickname?" + queryString))
                .andExpect(status().isOk());
    }

    @DisplayName("[rest] [Get] given - TakenNickname / request - check-nickname / response - badRequest?")
    @Test
    void givenTakenAdminNickname_whenRequestNicknameCheck_thenResponseIsBadRequest() throws Exception {
        // given
        // Service에 물어봤더니 그런 nickname을 가진 객체가 있다고 하면?
        when(adminService.isNicknameTaken(any(String.class))).thenReturn(true);
        String queryString = "nickname=testNickname1234";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-nickname?" + queryString))
                .andExpect(status().isBadRequest());
    }

    // 이메일 중복 검사
    @DisplayName("[rest] [Get] given - NonTakenEmail / request - check-email / response - ok?")
    @Test
    void givenNonTakenAdminEmail_whenRequestEmailCheck_thenResponseIsOk() throws Exception {
        // given
        // Service에 물어봤더니 그런 email을 가진 객체가 없다고 하면?
        when(adminService.isEmailTaken(any(String.class))).thenReturn(false);
        String queryString = "email=testEmail1234@naver.com";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-email?" + queryString))
                .andExpect(status().isOk());
    }

    @DisplayName("[rest] [Get] given - TakenEmail / request - check-email / response - badRequest?")
    @Test
    void givenTakenAdminEmail_whenRequestEmailCheck_thenResponseIsBadRequest() throws Exception {
        // given
        // Service에 물어봤더니 그런 email을 가진 객체가 있다고 하면?
        when(adminService.isEmailTaken(any(String.class))).thenReturn(true);
        String queryString = "email=testEmail1234@naver.com";
        // when & then
        mvc.perform(MockMvcRequestBuilders.get("/api/admin/sign-up/check-email?" + queryString))
                .andExpect(status().isBadRequest());
    }
}
