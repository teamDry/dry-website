package org.dry.service;

import org.dry.config.JpaConfig;
import org.dry.entity.Member;
import org.dry.mapper.MemberMapper;
import org.dry.repository.MemberRepository;
import org.dry.vo.IdAndPassword;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberServiceTest")
public class MemberServiceTest {
//    @Mock       // 가짜 repository를 생성하여 jpa를 사용함
//    private MemberRepository memberRepository;
//    @Mock
//    private MemberMapper memberMapper;
//
//    @InjectMocks    // 목객체를 주입받아 service를 테스트만을 위해 실행됨(Mockito가 자동으로 처리해줘서 편리하지만 목 객체와 테스트 클래스 간의 의존성이 커질수 있음)
//    private MemberServiceImpl service;

    // 생성자를 통한 주입방식
//    private MemberServiceImpl service;

//    @BeforeEach
//    public void setUp() {
//        service = new MemberServiceImpl(memberRepository);
//    }

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MemberServiceImpl service;

    public MemberServiceTest() {
        memberRepository = Mockito.mock(MemberRepository.class);    // @Mock 어노테이션 대신 사용
        memberMapper = Mockito.mock(MemberMapper.class);
        service = new MemberServiceImpl(memberRepository, memberMapper);
    }


    @DisplayName("MemberService Insert Test")
    @Test
    void givenTestData_whenInsertMember_thenWorksFine() {
        // given
        String testId = "test";
        Member member = Member.of( "test", "1234", "Tester", "test@naver.com");
        // service로 넘어가는 데이터를 Mockito로 가로챔
        Mockito.when(memberRepository.save(Mockito.any(Member.class))).thenReturn(member);
        // when
        Member newMember = service.insertMember(member);

        // then
        // newMember가 먼저 null값인지 확인 후 id값이 예상값과 일치 하는지 까지 확인
        assertThat(newMember).isNotNull().hasFieldOrPropertyWithValue("id", testId);
    }

    @DisplayName("MemberService login Test")
    @Test
    void given_when_then() {
        // given
        IdAndPassword idAndPassword = new IdAndPassword("test", "1234");
        // mapper 에 가짜데이터로 접속 되는지 여부만 따져 그에 반환 값을 Member 타입으로 초기화 하여 설정
        Mockito.when(memberMapper.selectMemberByIdAndPassword(Mockito.any(IdAndPassword.class))).thenReturn(Mockito.mock(Member.class));
        // when
        Member member = service.login(idAndPassword);
        // then
        // 접속이 성공적이라면 초기화된 객체가 들어갔으므로 NotNull이 맞음
        assertThat(member).isNotNull();
    }


}
