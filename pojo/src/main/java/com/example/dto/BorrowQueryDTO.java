package com.example.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class BorrowQueryDTO implements Serializable {
    //读者姓名
    private String userName;
    //书籍类别
    private String categoryName;
    //书籍名称
    private String bookName;

    private Integer categoryId;

    private String author;

    private String publish;

    private int page;

    private int pageSize;

    //借阅状态:0表示待通过 1表示通过 2拒绝
    private Integer status;


    private LocalDateTime startTime;


    private LocalDateTime endTime;

    private Integer userId;
}
