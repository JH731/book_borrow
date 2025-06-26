package com.example.controller.admin;

import com.example.dto.AdminDTO;
import com.example.dto.EmployeeDTO;
import com.example.entity.Admin;
import com.example.result.Result;
import com.example.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端相关接口
 */
@RestController("adminController")
@RequestMapping("/admin/admin")
@Api(tags = "管理端相关接口")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
//    /**
//     * 修改员工信息
//     * @param employeeDTO
//     * @return
//     */
//    @PutMapping
//    @ApiOperation("修改员工信息")
//    public Result update(@RequestBody EmployeeDTO employeeDTO){
//        log.info("编辑管理员信息:{}",employeeDTO);
//        adminService.update(employeeDTO);
//        return Result.success();
//    }

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
    public Result<Admin> getById() {
        Admin admin = adminService.getById();
        return Result.success(admin);
    }


}
