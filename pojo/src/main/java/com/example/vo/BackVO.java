package com.example.vo;

import com.example.entity.Borrow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BackVO extends Borrow implements Serializable {
    private Long id;
    private String userName;
    //借阅书籍
    private String bookName;
    // 借阅状态,1未归还,2待确认,3已归还
    private Integer status;
    //作者
    private String author;
    //出版社
    private String publish;
    //第几版的书
    private String edition;
    //借阅时间
    private LocalDateTime startTime;
}
