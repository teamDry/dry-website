package org.dry.security;

import org.dry.entity.Member;
import org.dry.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository =memberRepository ;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // id만 조회
        Member member = memberRepository.findById(username);

        // 해당 id가 없을 때
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

        CustomUserDetails custMember = CustomUserDetails.builder()
                                                        .member(member)
                                                        .build();

        return custMember;
    }
}
