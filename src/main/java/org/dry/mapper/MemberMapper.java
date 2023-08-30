package org.dry.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dry.entity.Member;
import org.dry.vo.IdAndPassword;

@Mapper
public interface MemberMapper {

    Member selectMemberById(String id);
    Member selectMemberByIdAndPassword(IdAndPassword memberIdAndPassword);
    Integer insertMember(Member member);
    Integer count();
    Integer deleteAll();
}
