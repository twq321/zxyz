package com.iflytek.service;

import com.iflytek.dto.CustomUserDetails;
import com.iflytek.dto.LoginRequest;
import com.iflytek.entity.User;

import java.util.List;

public interface UserService {
    User loginUser(LoginRequest loginRequest);
    CustomUserDetails findUser(String logintext, int userid);
    int upDate(User user);
    int register(LoginRequest loginRequest);
    int delete(int userId);

}
