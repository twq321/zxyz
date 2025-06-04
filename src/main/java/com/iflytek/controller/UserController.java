package com.iflytek.controller;


import com.iflytek.dto.LoginRequest;
import com.iflytek.entity.User;
import com.iflytek.service.UserService;
import com.iflytek.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(tags = "用户管理")
public class UserController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;
    @ApiOperation("查看当前用户")
    @GetMapping
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userDetails);
    }
    @ApiOperation("用户登录")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        User loginUser = userService.loginUser(loginRequest);
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("用户名或密码错误");
        }
        String token = jwtUtil.generateToken(loginUser);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("user", loginUser);

        return ResponseEntity.ok(data);
    }
    @ApiOperation("更新用户信息")
    @PostMapping("/update")
    public int upDate(@RequestBody User user){
        return userService.upDate(user);
    }
    @ApiOperation("注册用户信息")
    @PostMapping("/register")
    public int register(@RequestBody LoginRequest loginRequest){
        return userService.register(loginRequest);
    }
    @ApiOperation("删除用户信息")
    @PostMapping("/delete")
    public int delete(@RequestBody int userId){
        return userService.delete(userId);
    }

}
