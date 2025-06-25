package com.example.controller.user;

import com.example.constant.JwtClaimsConstant;
import com.example.dto.RegisterDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.entity.User;
import com.example.properties.JwtProperties;
import com.example.result.Result;
import com.example.service.UserService;
import com.example.utils.JwtUtil;
import com.example.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Api(tags = "用户端相关接口")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("用户登出")
    public Result<String> logout() {
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
        userService.updateByself(userDTO);
        return Result.success();
    }

}
