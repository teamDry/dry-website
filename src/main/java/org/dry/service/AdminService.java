package org.dry.service;

import lombok.AllArgsConstructor;
import org.dry.entity.Admin;
import org.dry.mapper.AdminMapper;
import org.dry.repository.AdminRepository;
import org.dry.vo.IdAndPassword;
import org.springframework.stereotype.Service;

public interface AdminService {
    Admin login(IdAndPassword idAndPassword);
    Admin signUp(Admin admin);
    boolean isIdTaken(String id);
    boolean isNicknameTaken(String nickname);
    boolean isEmailTaken(String email);
}
