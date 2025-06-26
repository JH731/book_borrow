package com.example.service;

import com.example.dto.AdminDTO;
import com.example.dto.EmployeeDTO;
import com.example.entity.Admin;

public interface AdminService {
    void update(EmployeeDTO employeeDTO);

    Admin getById();
}
