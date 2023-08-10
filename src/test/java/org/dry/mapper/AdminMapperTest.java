package org.dry.mapper;

import org.dry.entity.Admin;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Admin Mapper Test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@MybatisTest
class AdminMapperTest {
    private AdminMapper adminMapper;

    @Autowired
    public AdminMapperTest(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    @DisplayName("[Select] By Id And Password")
    @Test
    void givenIdAndPassword_whenSelectByIdAndPassword_thenWorksFine() {
        // given
        IdAndPassword idAndPassword = new IdAndPassword("abc", "1234");
        // when
        Admin admin = adminMapper.selectByIdAndPassword(idAndPassword);
        // then
        assertThat(admin).isNotNull().hasFieldOrPropertyWithValue("id", "abc");
    }
}