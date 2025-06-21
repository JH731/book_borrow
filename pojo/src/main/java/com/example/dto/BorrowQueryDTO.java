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

    private Integer categoryId;

    private int page;

    private int pageSize;

    //借阅状态,1未归还,2待确认,3已归还
    private Integer status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private Integer userId;
}
