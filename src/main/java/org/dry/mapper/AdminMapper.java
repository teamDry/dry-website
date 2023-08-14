package org.dry.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dry.entity.Admin;
import org.dry.vo.IdAndPassword;

@Mapper
public interface AdminMapper {
    Admin selectByIdAndPassword(IdAndPassword idAndPassword);
    Admin selectByNickname(String nickname);
    Admin selectById(String id);
    Admin selectByEmail(String email);
}
