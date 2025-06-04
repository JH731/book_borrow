package com.example.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Admin {
    private Integer id;
    private String name;
    private String password;
    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
