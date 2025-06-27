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
public class BackVO  implements Serializable {
    private Integer id;
    private String userName;
    //借阅书籍
    private String bookName;
    private String categoryName;
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
    //截止日期
    private LocalDateTime endTime;
    //归还日期
    private LocalDateTime returnTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;
}
