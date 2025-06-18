package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVO {
    private Integer id;
    //书籍名称
    private String name;
    //书籍分类id
    private Integer categoryId;
    //作者
    private String author;
    //出版社
    private String publish;
    //第几版的书
    private String edition;
    private Integer stock;
    //图片
    private String image;
    //描述信息
    private String description;
    //借阅状态:0 不可借阅  1 可借阅
    private Integer status;
    //更新的时间
    private LocalDateTime updateTime;
    //书籍分类的名称
    private String categoryName;

}
