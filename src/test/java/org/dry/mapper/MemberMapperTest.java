package org.dry.mapper;

import org.dry.entity.Member;
import org.dry.util.TestUtil;
import org.dry.vo.MemberIdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Member Mapper Test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@MybatisTest
class MemberMapperTest {
    private MemberMapper memberMapper;

    @Autowired
    public MemberMapperTest(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @DisplayName("Mapper Insert Test")
    @Test
    void givenNewMember_whenInsert_thenWorksFine() {
        // given
        // 넣을 Member 객체 생성
        Member newMember = Member.of("abcd", "1234", "testMember1", "test1@naver.com");
        // when
        // memberMapper class와 매핑된 memberMapper.xml의 쿼리문 실행, return : insert된 컬럼 수
        int count = memberMapper.insertMember(newMember);
        // then
        // 들어간 컬럼 수가 1개면 테스트 통과
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("Mapper Select Test")
    @Test
    void givenNewMember_whenSelectByIdAndPassword_thenFindMember() {
        // given
        String memberId = "abc";
        String memberPassword = "1234";
        memberMapper.insertMember(Member.of(memberId, memberPassword, "testNick", "test@email.com"));

        // memberId와 memberPassword를 합친 V.O 객체 생성
        MemberIdAndPassword memberIdAndPassword = new MemberIdAndPassword(memberId, memberPassword);
        // when
        // selectmemberByIdAndPassword와 매핑된 쿼리 실행, return : Member
        Member findMember = memberMapper.selectMemberByIdAndPassword(memberIdAndPassword);
        // then
        // 찾은 객체가 Null이 아니면 테스트 통과
        assertThat(findMember).isNotNull();
    }

    @DisplayName("Mapper Count Test")
    @Test
    void givenTestData_whenCounting_thenWorksFine() {
        // given
        memberMapper.insertMember(TestUtil.getDummyData());
        // when
        int count = memberMapper.count();
        // then
        assertThat(count).isEqualTo(11);
    }

    @DisplayName("Mapper DeleteAll Test")
    @Test
    void givenTestData_whenDeleteAll_thenWorksFine() {
        // given

        // when
        int deleteCount = memberMapper.deleteAll();
        // then
        assertThat(deleteCount).isEqualTo(10);
    }
}