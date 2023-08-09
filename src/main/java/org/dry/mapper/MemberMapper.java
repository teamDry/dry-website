package org.dry.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dry.entity.Member;
import org.dry.vo.MemberIdAndPassword;

@Mapper
public interface MemberMapper {
    Member selectMemberByIdAndPassword(MemberIdAndPassword memberIdAndPassword);
    Integer insertMember(Member member);
    Integer count();
    Integer deleteAll();
}
