package com.example.controller.admin;

import com.example.constant.JwtClaimsConstant;
import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeeLoginDTO;
import com.example.dto.EmployeePageQueryDTO;
import com.example.entity.Employee;
import com.example.properties.JwtProperties;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.EmployeeService;
import com.example.utils.JwtUtil;
import com.example.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 管理端相关接口
 */
@RestController("adminEmployeeController")
@RequestMapping("/admin/employee")
@Api("管理端相关接口")
@Slf4j
public class EmployeeController {
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工新增(注册)
     * @param employeeDTO
     * @return
     */
    @ApiOperation("员工新增")
    @PostMapping//省略了请求路径,因为上面进行统一设置
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        //使用Emp的DTO对象来接收前端传递的数据,由于是JSON格式的数据所以这里需要用注解@RequestBody
        //输出日志,便于调试
        log.info("新增员工:{}",employeeDTO);
        //调用Server层的方法
        employeeService.save(employeeDTO);
        return Result.success();
    }

    /**
     * 分页查询员工
     * @param employeePageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询员工信息")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("员工分页查询:{}",employeePageQueryDTO);
        PageResult pageResult = employeeService.pageQuery(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 编辑员工信息
     * @param employeeDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result update(@RequestBody EmployeeDTO employeeDTO){//JSON格式参数
        log.info("编辑员工信息: {}",employeeDTO);
        employeeService.update(employeeDTO);
        return Result.success();
    }


    @ApiOperation("根据id查询员工信息")
    @GetMapping("/{id}")
    public Result<Employee> getById(@PathVariable Long id){//路径参数
        Employee employee = employeeService.getById(id);
        return Result.success(employee);
    }

}
