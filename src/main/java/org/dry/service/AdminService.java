package org.dry.service;

import org.dry.entity.Admin;
import org.dry.mapper.AdminMapper;
import org.dry.vo.IdAndPassword;
import org.springframework.stereotype.Service;

public interface AdminService {
    Admin login(IdAndPassword idAndPassword);
}
