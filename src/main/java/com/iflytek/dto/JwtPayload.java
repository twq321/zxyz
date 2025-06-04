package com.iflytek.dto;

import lombok.Data;

@Data
public class JwtPayload {
    private int userid;
    private String logintext;

}