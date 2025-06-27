package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookQueryDTO implements Serializable {
    private int page;

    private int pageSize;

    private String name;

    private Integer categoryId;

    private Integer status;

    private String categoryName;

    private String author;
    private String publish;

}
