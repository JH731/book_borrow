package com.example.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class UserPageQueryDTO implements Serializable {

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;

    //姓名
    private String name;

}
