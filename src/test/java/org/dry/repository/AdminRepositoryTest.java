package org.dry.repository;

import org.dry.config.JpaConfig;
import org.dry.entity.Admin;
import org.dry.util.TestUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("AdminRepository TEST")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@Import(JpaConfig.class)
@DataJpaTest
class AdminRepositoryTest {
    private AdminRepository adminRepository;

    @Autowired
    public AdminRepositoryTest(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @DisplayName("Admin Insert Test ")
    @Test
    void givenAdminData_whenInsertFromAdmin_thenWorksFine() {
        // given
        Admin admin = TestUtil.getDummyAdmin();
        long previousCount = adminRepository.count();
        // when
        adminRepository.save(admin);
        // then
        assertThat(adminRepository.count()).isEqualTo(previousCount + 1);

    }

    @DisplayName("Admin Select Test")
    @Test
    void givenAdminNo_whenSelectFromAdmin_thenWorksFine() {
        // given
        // when
        Admin selectAdmin = adminRepository.findById(2).orElseThrow();
        // then
        assertThat(selectAdmin).isNotNull().hasFieldOrPropertyWithValue("nickname", "Volvo");
    }

    @DisplayName("Admin Update Test")
    @Test
    void givenChangeNick_whenUpdateNickname_thenWorksFine() {
        // given
        String changeNick = "Honda";
        Admin updateAdmin = adminRepository.findById(2).orElseThrow();
        updateAdmin.setNickname(changeNick);
        // when
        Admin assertAdmin = adminRepository.save(updateAdmin);
        // then
        assertThat(assertAdmin).isNotNull().hasFieldOrPropertyWithValue("nickname", "Honda");
    }

    @DisplayName("Admin Delete Test")
    @Test
    void givenAdminData_whenDeleteAdmin_thenWorksFine() {
        // given
        long previousCount = adminRepository.count();
        Admin deleteAdmin = adminRepository.findById(2).orElseThrow();
        // when
        adminRepository.delete(deleteAdmin);
        // then
        assertThat(adminRepository.count()).isEqualTo(previousCount - 1);
    }
}