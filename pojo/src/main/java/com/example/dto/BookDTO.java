package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookDTO {
    private Integer id;
    //书籍名称
    private String name;
    //书籍分类id
    private String categoryName;

    //作者
    private String author;
    //出版社
    private String publish;
    //第几版的书
    private String edition;
    //图片
    private String image;
    //借阅状态:0 不可借阅  1 可借阅
    private Integer status;
    //数量
    private Integer stock;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;



}
