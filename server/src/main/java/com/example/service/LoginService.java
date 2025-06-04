package com.example.service;

import com.example.dto.LoginDTO;
import com.example.vo.LoginVO;

public interface LoginService {
    LoginVO login(LoginDTO loginDTO);
}
