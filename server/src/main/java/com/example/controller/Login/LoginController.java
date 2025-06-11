package com.example.controller.Login;

import com.example.constant.JwtClaimsConstant;
import com.example.constant.MessageConstant;
import com.example.dto.LoginDTO;
import com.example.entity.User;
import com.example.exception.AccountNotFoundException;
import com.example.exception.PasswordErrorException;
import com.example.properties.JwtProperties;
import com.example.result.Result;
import com.example.service.LoginService;
import com.example.utils.JwtUtil;
import com.example.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录相关接口
 */
@RestController
@RequestMapping("/login")
@Api(tags = "登录相关接口")
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<?> login(LoginDTO loginDTO) {
        log.info("登录:{}",loginDTO);
        LoginVO loginVO = loginService.login(loginDTO);
//        System.out.println("loginId" + loginVO.getId());
        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.LOGIN_ID, loginVO.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);
        loginVO.setToken(token);
        return Result.success(loginVO);
    }
    @ApiOperation("登出")
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

}
