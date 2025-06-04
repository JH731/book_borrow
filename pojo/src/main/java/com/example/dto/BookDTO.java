package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BookDTO {
    private Long id;
    //书籍名称
    private String name;
    //书籍分类id
    private Long categoryId;
    //分类名称
    private String categoryName;
    //图片
    private String image;
    //描述信息
    private String description;
    //借阅状态:0 不可借阅  1 可借阅
    private Integer status;
    //数量
    private Integer stock;



}
