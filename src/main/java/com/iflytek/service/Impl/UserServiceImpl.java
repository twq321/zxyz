package com.iflytek.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.dto.LoginRequest;
import com.iflytek.entity.User;
import com.iflytek.mapper.UserMapper;
import com.iflytek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User loginUser(LoginRequest loginRequest) {
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        wrapper.eq("loginText",loginRequest.getLoginText())
                .eq("password",loginRequest.getPassword());
        // 演示一下从配置里读到的 name
        // 调用 MyBatis-Plus 提供的 selectList
        return userMapper.selectOne(wrapper);
    }
}
