package org.dry.mapper;

import org.dry.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member Mapper Test")
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@MybatisTest // default Test DB >> Test >> 없어 >> 오류
class MemberMapperTest {
    @Autowired
    private MemberMapper memberMapper;

    @DisplayName("Mapper Insert Test")
    @Test
    void givenNewMember_whenInsert_thenWorksFine() {
//         given
        Member newMember = Member.of("abc", "1234", "testMember", "test@naver.com");
        // when
        int count = memberMapper.insertMember(newMember);
//         then
        assertThat(count == 1);
    }

    @DisplayName("Mapper Select Test")
    @Test
    void givenIdAndPassword_whenSelect_thenFindMember() {
        // given
        String memberId = "abc";
        String memberPassword = "1234";
        MemberIdAndPassword memberIdAndPassword = new MemberIdAndPassword(memberId, memberPassword);
        // when
        Member findMember = memberMapper.selectMemberByIdAndPassword(memberIdAndPassword);
        // then
        assertThat(findMember).isNull();
    }
}