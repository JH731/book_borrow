package com.example.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class BorrowStatisticVO implements Serializable {
    //借阅中
    private Integer borrowing;

    //已归还
    private Integer returned;

    //异常丢失
    private Integer lossed;
}
