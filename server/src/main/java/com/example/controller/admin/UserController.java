package com.example.controller.admin;

import com.example.dto.EmployeeDTO;
import com.example.dto.EmployeePageQueryDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPageQueryDTO;
import com.example.entity.Employee;
import com.example.entity.User;
import com.example.result.PageResult;
import com.example.result.Result;
import com.example.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 管理端查询用户的接口
 */
@RestController("adminUserController")
@RequestMapping("/admin/user")
@Api("管理端相关接口")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 用户新增
     * @param user
     * @return
     */
    @ApiOperation("用户新增")
    @PostMapping("/add")
    public Result save(@RequestBody User user) {
        log.info("用户新增:{}",user);
        userService.save(user);
        return Result.success();
    }



    /**
     * 编辑用户信息
     * @param userDTO
     * @return
     */
    @PutMapping
    @ApiOperation("编辑用户信息")
    public Result update(@RequestBody UserDTO userDTO){//JSON格式参数
        log.info("编辑用户信息: {}",userDTO);
        userService.update(userDTO);
        return Result.success();
    }

    /**
     * 删除用户信息
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("删除用户信息")
    public Result delete(Long id){
        log.info("删除用户信息: {}",id);
        userService.delete(id);
        return Result.success();
    }
    /**
     * 分页查询用户信息
     * @param userPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("分页查询用户信息")
    public Result<PageResult> page(UserPageQueryDTO userPageQueryDTO){
        //输出日志,便于调试
        log.info("用户分页查询,参数为:{}",userPageQueryDTO);
        //调用Service层的方法,希望返回的类型是所需要的PageResult类型
        PageResult pageResult = userService.pageQuery(userPageQueryDTO);
        return Result.success(pageResult);//封装成Result对象返回
    }


//    /**
//     * 启用禁用用户借书账号
//     * @param status
//     * @param id
//     * @return
//     */
//    @ApiOperation("设置用户借阅权限")
//    @PostMapping("/status/{status}")//路径参数是{}包起来的
//    public Result startOrstop(@PathVariable Integer status, Long id){//参数有两个,路径参数(需要注解)和地址栏传入的参数
//        log.info("启用禁用员工账号: {},{}",status,id);//输入日志便于调试
//        //调用Service层代码
//        userService.startOrstop(status,id);
//        return Result.success();
//    }

    /**
     * 根据Id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("根据Id查询用户信息")
    public Result<User> getById(@PathVariable Long id){//路径参数
        User user= userService.getById(id);
        return Result.success(user);
    }

}
