package com.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginVO {
    private Integer id;
    private String userName;
    private String password;
    private Integer type;
    private String token;
}
