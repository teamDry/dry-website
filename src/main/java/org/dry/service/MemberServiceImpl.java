package org.dry.service;

import org.dry.entity.Member;
import org.dry.mapper.MemberMapper;
import org.dry.repository.MemberRepository;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }

    @Override
    public Member insertMember(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member login(IdAndPassword idAndPassword) {
        return memberMapper.selectMemberByIdAndPassword(idAndPassword);
    }
}
