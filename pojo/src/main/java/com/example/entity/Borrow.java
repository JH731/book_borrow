package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Borrow implements Serializable {
    private Long id;
    // 借阅状态,有未归还,已归还,还有丢失这些
    private Integer status;
    //借阅用户id
    private Long userId;
    //借阅的书籍id
    private Long bookId;
    //借阅时间
    private LocalDateTime startTime;
    //截止日期
    private LocalDateTime endTime;
    //归还日期
    private LocalDateTime returnTime;
}
