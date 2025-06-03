package com.iflytek.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.iflytek.entity.User;
import com.iflytek.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

//@RestController
public class TestController {
    @Resource
    UserMapper userMapper;
    @GetMapping("/test")
    public List<User> listUsers() {
        QueryWrapper<User> wrapper =new QueryWrapper<>();
        wrapper.eq("username","秋风");
        List<User> userList=userMapper.selectList(wrapper);
        // 演示一下从配置里读到的 name
        // 调用 MyBatis-Plus 提供的 selectList
        return userList;
    }

}
