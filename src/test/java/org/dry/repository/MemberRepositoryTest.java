package org.dry.repository;

import org.dry.config.JpaConfig;
import org.dry.entity.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MemberRepository TEST")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
@Import(JpaConfig.class)
@DataJpaTest
class MemberRepositoryTest {
    private MemberRepository memberRepository;

    @Autowired
    public  MemberRepositoryTest(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    
    @DisplayName("Select Test")
    @Test
    void givenTestData_whenSelectAll_thenWorksFine() {
        // given
        // when
        List<Member> members = memberRepository.findAll(); // db의 모든 데이터 List로 추출
        // then
        // 테스트 순서 : members에 대해 NotNull인가? : Yes >> 그럼 사이즈는 1인가? : YES >> 통과
        assertThat(members).isNotNull().hasSize(10);
    }

    @DisplayName("Insert Test")
    @Test
    void givenTestData_whenInsert_thenWorksFine() {
        // given

        // 추가 전 컬럼 수
        long previousCount = memberRepository.count();
        // 추가할 member
        Member insertMember = Member.of("a", "b", "c", "d");

        // when
        memberRepository.save(insertMember); // 1개 추가

        // then
        // 현재 컬럼 수 == 추가 전 컬럼수 + 1
        assertThat(memberRepository.count()).isEqualTo(previousCount + 1);
    }



    @DisplayName("Update Test")
    @Test
    void givenTestData_whenUpdateNickname_thenWorksFine() {
        // given
        // pk가 1인 member 객체 가져옴, 없을시 예외 던짐
        Member updateMember = memberRepository.findById(2).orElseThrow();
        // 바꿀 닉네임
        String changeNick = "changeNick";
        // 가져온 객체의 닉네임을 changeNick으로 바꿈
        // testNick >> changeNick
        updateMember.setNickname(changeNick);

        // when
        // 바꾼 객체를 다시 저장
        updateMember = memberRepository.save(updateMember);
        // then
        // Field와 Value를 짝지어 검사
        // updateMember의 nickname이라는 필드 값이 changeNick인가? >> True라면 테스트 통과
        assertThat(updateMember).hasFieldOrPropertyWithValue("nickname", changeNick);
    }

    @DisplayName("Delete Test")
    @Test
    void givenTestData_whenDeleteByMemberNo_thenWorksFine() {
        // given
        // 삭제 전 컬럼 수
        long previousCount = memberRepository.count();
        // 삭제할 member
        Member deleteMember = memberRepository.findById(2).orElseThrow();
        // when
        // 삭제
        memberRepository.delete(deleteMember);

        // then
        // 현재 컬럼 수 == 추가 전 컬럼수 - 1
        assertThat(memberRepository.count()).isEqualTo(previousCount - 1);
    }

    @DisplayName("Count Column")
    @Test
    void givenNothing_whenCounting_thenWorksFine() {
        // given

        // when
        long count = memberRepository.count();
        // then
        assertThat(count).isEqualTo(10);
    }
}