package org.dry.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("View Controller - Admin")
@WebMvcTest(AdminViewController.class)
class AdminViewControllerTest {
    private final MockMvc mvc;


    public AdminViewControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view] [GET] 로그인 화면 - 정상 호출?")
    @Test
    void givenNothing_whenRequestLoginView_thenReturnsLoginView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/admin/login"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/login"));
    }

    @DisplayName("[view] [GET] 로그인 화면 - 정상 호출?")
    @Test
    void givenNothing_whenRequestSignUpView_thenReturnsSignUpView() throws Exception {
        // given

        // when & then
        mvc.perform(get("/admin/sign-up"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("admin/sign-up"));
    }
}