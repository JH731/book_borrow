package com.example.dto;

import lombok.Data;

@Data
public class EmployeeLoginDTO {
    private Long id;

    private String username;

    private String password;

//    private String name;

    private String phone;

    private String sex;

    private String idNumber;
}
