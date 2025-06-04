package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminBorrowVO {
    private Integer id;
    private String userName;
    //借阅书籍
    private String bookName;
    //书籍类别
    private String categoryName;
    //todo 借阅状态需要统一一下,1未归还,2待确认,3已归还
    private Integer status;
    //作者
    private String author;
    //出版社
    private String publish;
    //第几版的书
    private String edition;
    //借阅时间,申请时间
    private LocalDateTime startTime;
}
