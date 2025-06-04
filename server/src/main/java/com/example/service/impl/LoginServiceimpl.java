package com.example.service.impl;

import com.example.constant.MessageConstant;
import com.example.dto.LoginDTO;
import com.example.entity.Admin;
import com.example.entity.Employee;
import com.example.entity.User;
import com.example.exception.AccountNotFoundException;
import com.example.exception.PasswordErrorException;
import com.example.mapper.AdminMapper;
import com.example.mapper.EmployeeMapper;
import com.example.mapper.UserMapper;
import com.example.service.LoginService;
import com.example.vo.LoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.function.Function;

@Service
public class LoginServiceimpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public LoginVO login(LoginDTO loginDTO) {
        final String username = loginDTO.getUserName();
        final String inputPassword = DigestUtils.md5DigestAsHex(
                loginDTO.getPassword().getBytes(StandardCharsets.UTF_8)
        );

        switch (loginDTO.getType()) {
            case 1:
                return processLogin(username, inputPassword,
                        userMapper::getByUserName, User::getPassword);
            case 2:
                return processLogin(username, inputPassword,
                        employeeMapper::getByUsername, Employee::getPassword);
            case 3:
                return processLogin(username, inputPassword,
                        adminMapper::getByUserName, Admin::getPassword);
            default:
                throw new IllegalArgumentException("无效的登录类型");
        }
    }

    private <T> LoginVO processLogin(String username,
                                     String inputPassword,
                                     Function<String, T> entityFetcher,
                                     Function<T, String> passwordExtractor) {
        T entity = entityFetcher.apply(username);
        if (entity == null) {
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        String storedPassword = passwordExtractor.apply(entity);
        if (!storedPassword.equals(inputPassword)) {
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        LoginVO loginVO = new LoginVO();
        //todo 这里的名字先都统一是userName,后面都要改
        BeanUtils.copyProperties(loginVO, entity);
        return loginVO;
    }
}
