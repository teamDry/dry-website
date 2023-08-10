package org.dry.service;

import org.dry.entity.Admin;
import org.dry.mapper.AdminMapper;
import org.dry.repository.AdminRepository;
import org.dry.vo.IdAndPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private AdminMapper adminMapper;
    private AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminMapper adminMapper, AdminRepository adminRepository) {
        this.adminMapper = adminMapper;
        this.adminRepository = adminRepository;
    }

    @Override
    public Admin login(IdAndPassword idAndPassword) {
        return adminMapper.selectByIdAndPassword(idAndPassword);
    }
}
