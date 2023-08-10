package org.dry.service;

import org.dry.entity.Admin;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AdminServiceTest")
@SpringBootTest
class AdminServiceTest {
    private AdminService adminService;

    @Autowired
    public AdminServiceTest(AdminService adminService) {
        this.adminService = adminService;
    }

    @DisplayName("Login Test")
    @Test
    void givenIdAndPassword_whenLogin_thenReturnAdmin() {
        // given
        IdAndPassword idAndPassword = new IdAndPassword("abc", "1234");
        // when
        Admin loginAdmin = adminService.login(idAndPassword);
        // then
        assertThat(loginAdmin).isNotNull().hasFieldOrPropertyWithValue("admin_no", 5);
    }

    @DisplayName("Login Fail Test")
    @Test
    void givenWrongIdAndPassword_whenLogin_thenLoginFailed() {
        // given
        IdAndPassword idAndPassword = new IdAndPassword("abcd", "1234");
        // when
        Admin loginAdmin = adminService.login(idAndPassword);
        // then
        assertThat(loginAdmin).isNull();
    }
}