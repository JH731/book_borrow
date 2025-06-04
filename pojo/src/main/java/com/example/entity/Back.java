package com.example.entity;

import lombok.Data;

@Data
public class Back {
    private Integer id;
    private Integer brid;
    //点击了这个按钮之后就是归还状态了,所以默认设置为0
    private Integer status = 0;
}
