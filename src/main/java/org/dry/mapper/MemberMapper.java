package org.dry.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dry.entity.Member;

@Mapper
public interface MemberMapper {
    Member selectMemberByIdAndPassword(MemberIdAndPassword memberIdAndPassword);
    int insertMember(Member member);
}
