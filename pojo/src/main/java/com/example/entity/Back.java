package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Back {
    private Integer id;
    private Integer brid;
    //点击了这个按钮之后就是归还状态了,所以默认设置为0
    private Integer status = 0;
    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;
}
