package org.dry.controller;

import jakarta.persistence.Id;
import org.dry.entity.Admin;
import org.dry.service.AdminService;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

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
    @MockBean
    private AdminService adminService;

    public AdminRestControllerTest(@Autowired MockMvc mvc, @Autowired AdminRestController adminRestController) {
        this.mvc = mvc;
        this.adminRestController = adminRestController;
    }


    @DisplayName("[rest] [post] Login 요청시 객체 반환?")
    @Test
    void givenAdminObject_whenPostRequest_thenResponseAdminObject() throws Exception {
        // given
        // adminService.login이 실행된다면 결과값으로 저걸 줘라
        when(adminService.login(any(IdAndPassword.class))).thenReturn(Admin.of("abc", "1234", "abcc", "a@b.c"));
        // when & then
        mvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON) // JSON 형식으로 보낼거고
                .content("{ \"id\": \"abc\", \"password\": \"1234\" }")) // 보내는 JSON 객체는 이거임
                .andExpect(status().isOk()) // 응답코드가 OK(200)이냐?
                .andExpect(jsonPath("$.id").value("abc")) // 받은 json의 id 값이 abc인가?
                .andExpect(jsonPath("$.password").value("1234")); // 받은 json의 password 값이 "1234" 인가?
    }
}
