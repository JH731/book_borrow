package com.example.dto;

import com.example.entity.BorrowDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
public class BorrowDTO {

    private Integer id;

    // 借阅状态,有未归还,已归还,还有丢失这些
    private Integer status;
    //借阅用户id
    private Integer userId;
    //借阅时间
    private LocalDateTime startTime;
    //截止日期
    private LocalDateTime endTime;
    //用户名
    private String userName;
    //手机号
    private String phone;
    //分类名称
    private String categoryName;
}
