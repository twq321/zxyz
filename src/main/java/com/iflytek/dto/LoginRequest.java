package com.iflytek.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String loginText; // 账号
    private String password;  // 密码
}
