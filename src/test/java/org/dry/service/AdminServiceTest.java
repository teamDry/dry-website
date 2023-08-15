package org.dry.service;

import jakarta.persistence.Id;
import org.dry.entity.Admin;
import org.dry.mapper.AdminMapper;
import org.dry.repository.AdminRepository;
import org.dry.util.TestUtil;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("AdminServiceTest")
@ExtendWith(MockitoExtension.class)
class AdminServiceTest {
    @Mock
    private AdminRepository adminRepository;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminService adminService;

    private Admin testAdmin;
    private IdAndPassword goodIdAndPassword;
    private IdAndPassword badIdAndPassword;

    public AdminServiceTest() {
        adminService = new AdminServiceImpl(adminMapper, adminRepository);
        testAdmin = TestUtil.getDummyAdmin();
        goodIdAndPassword = new IdAndPassword("testAdmin", "testAdminPw");
        badIdAndPassword = new IdAndPassword("abcd", "1234");
    }

    @BeforeEach
    void whenThenReturns() {
        Mockito.lenient().when(adminMapper.selectByIdAndPassword(goodIdAndPassword)).thenReturn(testAdmin);
        Mockito.lenient().when(adminMapper.selectByIdAndPassword(badIdAndPassword)).thenReturn(null);
        Mockito.lenient().when(adminRepository.save(testAdmin)).thenReturn(testAdmin);
    }

    @DisplayName("Login Test")
    @Test
    void givenIdAndPassword_whenLogin_thenReturnAdmin() {
        // given
        // when
        testAdmin = adminService.login(goodIdAndPassword);
        // then
        assertThat(testAdmin).isNotNull().hasFieldOrPropertyWithValue("id", "testAdmin");
    }

    @DisplayName("Login Fail Test")
    @Test
    void givenWrongIdAndPassword_whenLogin_thenLoginFailed() {
        // given
        // when
        testAdmin = adminService.login(badIdAndPassword);
        // then
        assertThat(testAdmin).isNull();
    }

    @DisplayName("Sign Up Test")
    @Test
    void givenAdminObject_whenSignUp_thenReturnUserId() {
        // given

        // when
        Admin signUpAdmin = adminService.signUp(testAdmin);
        // then
        assertThat(signUpAdmin).isEqualTo(testAdmin);
    }

    @DisplayName("Select By Id and Return true or false")
    @Test
    void givenId_whenSelectById_thenValid() {
        // given
        String existId = "abc"; // 있는거
        // when
        boolean flag = adminService.isIdTaken(existId);
        // then
        assertThat(flag == true);
    }

    @DisplayName("Select By Nickname and Return true or false")
    @Test
    void givenNickname_whenSelectByNickname_thenValid() {
        // given
        String existNickname = "woo"; // 있는거
        // when
        boolean flag = adminService.isNicknameTaken(existNickname);
        // then
        assertThat(flag == true);
    }

    @DisplayName("Select By Email and Return true or false")
    @Test
    void givenEmail_whenSelectByEmail_thenValid() {
        // given
        String existEmail = "abc@naver.com"; // 있는거
        // when
        boolean flag = adminService.isEmailTaken(existEmail);
        // then
        assertThat(flag == true);
    }
}