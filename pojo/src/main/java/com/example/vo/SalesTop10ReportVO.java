package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesTop10ReportVO implements Serializable {

    //书籍名称列表，以逗号分隔，例如：查理九世,水浒传,红楼梦
    private String nameList;

    //借阅列表，以逗号分隔，例如：260,215,200
    private String numberList;

}
