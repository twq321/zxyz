package com.iflytek.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.dto.CustomUserDetails;
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
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("loginText", loginRequest.getLoginText())
                .eq("password", loginRequest.getPassword());
        // 演示一下从配置里读到的 name
        // 调用 MyBatis-Plus 提供的 selectList
        return userMapper.selectOne(wrapper);
    }

    @Override
    public CustomUserDetails findUser(String logintext, int userid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("loginText", logintext)
                .eq("userid", userid);

        User user = userMapper.selectOne(wrapper);

        if (user == null) {
            return null; // 或抛异常
        }

        return new CustomUserDetails(user); // 构造并返回 UserDetails 对象
    }
    @Override
    public int upDate(User user){
       int row=userMapper.updateById(user);
        return row>0?1:0;
    }
    @Override
    public int register(LoginRequest loginRequest){
        User user=new User();
        user.setLogintext(loginRequest.getLoginText());
        user.setPassword(loginRequest.getPassword());
        int row=userMapper.insert(user);
        return row>0?1:0;
    }
    @Override
    public int delete(int userId){
        int row=userMapper.deleteById(userId);
        return row>0?1:0;
    }
    @Override
    public User loginByFace(int userId){
        return userMapper.selectById(userId);
    }
}
