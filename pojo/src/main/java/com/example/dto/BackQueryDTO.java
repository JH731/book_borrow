package com.example.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class BackQueryDTO {
    private int page;

    private int pageSize;
    //借阅状态,1未归还,2待确认,3已归还
    private Integer status;


    private LocalDateTime startTime;


    private LocalDateTime endTime;

    private Integer userId;
}