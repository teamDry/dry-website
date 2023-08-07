package org.dry.repository;

import org.dry.config.JpaConfig;
import org.dry.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("MemberRepository TEST")
@Import(JpaConfig.class)
@DataJpaTest
class MemberRepositoryTest {
    private MemberRepository memberRepository;

    public  MemberRepositoryTest(@Autowired MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @DisplayName("Select Test")
    @Test
    void givenTestData_whenSelect_thenWorksFine() {
        // given
        // Auditing 활성화 되어있어도 data.sql에서 넣을땐 created_at도 적어줘야함
        // when
        List<Member> members = memberRepository.findAll(); 
        // then
        assertThat(members).isNotNull().hasSize(100);
    }

    @DisplayName("Insert Test")
    @Test
    void givenTestData_whenInsert_thenWorksFine() {
        // given

        // 추가 전 컬럼 수
        long previousCount = memberRepository.count();
        // 추가할 member
        Member insertMember = Member.of("newId", "newPw", "newNick", "newEmail");

        // when
        memberRepository.save(insertMember);

        // then
        // 현재 컬럼 수 == 추가 전 컬럼수 + 1
        assertThat(memberRepository.count() == previousCount + 1);
    }
    @DisplayName("Update Test")
    @Test
    void givenTestData_whenUpdate_thenWorksFine() {
        // given
        Member updateMember = memberRepository.findById(1).orElseThrow();
        String changeNick = "changeNick";
        updateMember.setNickname(changeNick);
        // when
        updateMember = memberRepository.save(updateMember);
        // then
        assertThat(updateMember).hasFieldOrPropertyWithValue("nickname", changeNick);
    }

    @DisplayName("Delete Test")
    @Test
    void givenTestData_whenDelete_thenWorksFine() {
        // given
        // 삭제 전 컬럼 수
        long previousCount = memberRepository.count();
        // 삭제할 member
        Member deleteMember = memberRepository.findById(1).orElseThrow();
        // when
        memberRepository.delete(deleteMember);

        // then
        // 현재 컬럼 수 == 추가 전 컬럼수 - 1
        assertThat(memberRepository.count() == previousCount - 1);
    }
}