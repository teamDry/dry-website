package org.dry.service;

import lombok.NoArgsConstructor;
import org.dry.entity.Admin;
import org.dry.mapper.AdminMapper;
import org.dry.repository.AdminRepository;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;
    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper, AdminRepository adminRepository) {
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }
    
    // mock 테스트용 생성자
    public AdminServiceImpl(AdminMapper adminMapper, AdminRepository adminRepository, String mock) {
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }


    @Override
    public Admin login(IdAndPassword idAndPassword) {
        return adminMapper.selectByIdAndPassword(idAndPassword);
    }

    @Override
    public Admin signUp(Admin admin) {
        return adminRepository.save(admin);
    }

    // 항목 중복 검사
    @Override
    public boolean isIdTaken(String id) {
        Admin admin = adminMapper.selectById(id);
        boolean isIdTaken = (admin != null);
        return isIdTaken;
    }
    
    @Override
    public boolean isNicknameTaken(String nickname) {
        Admin admin = adminMapper.selectByNickname(nickname);
        boolean isNicknameTaken = (admin != null);
        return isNicknameTaken;
    }


    @Override
    public boolean isEmailTaken(String email) {
        Admin admin = adminMapper.selectByEmail(email);
        boolean isEmailTaken = (admin != null);
        return isEmailTaken;
    }
}
