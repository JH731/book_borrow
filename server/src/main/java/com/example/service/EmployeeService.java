package com.example.service;

import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeLoginDTO;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Employee;
import com.example.result.PageResult;

public interface EmployeeService {
    Employee login(EmployeeLoginDTO adminLoginDTO);

    void save(EmployeeDTO employeeDTO);


    void update(EmployeeDTO employeeDTO);


    Employee getById(Integer id);

    PageResult pageQuery(EmployeePageQueryDTO employeePageQueryDTO);

    void delete(Integer id);
}
