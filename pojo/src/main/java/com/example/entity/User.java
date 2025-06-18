package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
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

    //用户的状态,能否借书
    private Integer status;

    //注册时间
    private LocalDateTime createTime;

    private Long createUser;

    //登录时间

    //更新时间
    private LocalDateTime updateTime;

    //修改的管理员
    private Long updateUser;

    //最大可借阅数量就设置为3
    private Integer maxBorrow = 3;
}