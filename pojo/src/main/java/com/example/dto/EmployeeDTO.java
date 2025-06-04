package com.example.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class EmployeeDTO implements Serializable {
    private Long id;

    private String password;

    private String name;

    private String phone;

    private String sex;

}
