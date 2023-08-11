package org.dry.service;

import org.dry.entity.Member;
import org.dry.vo.IdAndPassword;

public interface MemberService {

    Member insertMember(Member member);

    Member login(IdAndPassword idAndPassword);
}
