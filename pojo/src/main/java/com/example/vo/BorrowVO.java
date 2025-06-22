package com.example.vo;

import com.example.entity.Borrow;
import com.example.entity.BorrowDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowVO extends Borrow implements Serializable {
    private Integer id;
    //借阅书籍
    private String bookName;
    //书籍类别
    private String categoryName;
    //借阅人
    private String userName;
    //todo 借阅状态需要统一一下,1未归还,2待确认,3已归还
    private Integer status;
    //作者
    private String author;
    //出版社
    private String publish;
    //第几版的书
    private String edition;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    //归还日期
    private LocalDateTime returnTime;
}
