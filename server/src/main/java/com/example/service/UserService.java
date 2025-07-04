package com.example.service;

import com.example.dto.RegisterDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserLoginDTO;
import com.example.dto.UserPageQueryDTO;
import com.example.entity.User;
import com.example.result.PageResult;
import com.example.result.Result;

public interface UserService {
    /**
    * 普通用户登录
    * @param userLoginDTO
    */
    User login(UserLoginDTO userLoginDTO);


    User getById(Integer userID);

    PageResult pageQuery(UserPageQueryDTO userPageQueryDTO);

    void startOrstop(Integer status, Integer id);


    void save(User user);

    Result sendVerificationCode(String phone);

    Result register(RegisterDTO dto);

    void updateByself(UserDTO userDTO);

    void update(UserDTO userDTO);

    void delete(Integer id);

}
