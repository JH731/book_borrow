package com.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDTO implements Serializable {
    private Integer id;
    //姓名
    private String name;
    // md5加密存储
    private String password;

    //手机号
    private String phone;

    //性别 0 女 1 男
    private String sex;

    //头像
    private String avatar;

    //登录时间

    //更新时间
    private LocalDateTime updateTime;

    //修改的管理员
    private Integer updateUser;

    //最大可借阅数量就设置为3
    private Integer maxBorrow = 3;
}
