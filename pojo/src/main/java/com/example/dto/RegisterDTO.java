package com.example.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterDTO {
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String phone;

    @NotBlank(message = "验证码不能为空")
    @Length(min = 6, max = 6, message = "验证码必须为6位数字")
    private String code;

    @NotBlank(message = "密码不能为空")
    @Length(min = 8, max = 20, message = "密码长度需8-20位")
    private String password;
}