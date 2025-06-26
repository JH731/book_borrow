package com.example.service.impl;

import com.example.context.BaseContext;
import com.example.dto.AdminDTO;
import com.example.dto.EmployeeDTO;
import com.example.entity.Admin;
import com.example.mapper.AdminMapper;
import com.example.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceimpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Override
    public void update(EmployeeDTO employeeDTO) {

    }

    @Override
    public Admin getById() {
        Integer id = BaseContext.getCurrentId();
        Admin admin = adminMapper.getByUserId(id);
        return admin;
    }
}
