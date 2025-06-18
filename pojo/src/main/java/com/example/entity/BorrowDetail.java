package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    //名称
    private String name;

    //借阅id
    private Integer borrowId;

    //书籍id
    private Integer bookId;

    //该书的借阅数量
    private Integer number;

    //图片
    private String image;
}
