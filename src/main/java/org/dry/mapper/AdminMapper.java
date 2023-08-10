package org.dry.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dry.entity.Admin;
import org.dry.vo.MemberIdAndPassword;

@Mapper
public interface AdminMapper {
    Admin selectByIdAndPassword(MemberIdAndPassword idAndPassword);
}
