package com.example.controller.employee;

import com.example.context.BaseContext;
import com.example.dto.BookQueryDTO;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Admin;
import com.example.entity.Employee;
import com.example.properties.JwtProperties;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.EmployeeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端相关接口
 */
@RestController("EmployeeController")
@RequestMapping("/employee/employee")
@Api("员工相关接口")
@Slf4j
public class EmployeeController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工登出")
    public Result<String> logout() {
        return Result.success();
    }

    @GetMapping
    @ApiOperation("查询自己的信息")
    public Result<Employee> getById() {
        Employee employee = employeeService.getById(BaseContext.getCurrentId());
        return Result.success(employee);
    }
}
