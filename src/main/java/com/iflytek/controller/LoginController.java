package com.iflytek.controller;



import com.iflytek.dto.LoginRequest;
import com.iflytek.entity.User;
import com.iflytek.service.UserService;
import com.iflytek.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@Api(tags = "用户登录")
public class LoginController {
    @Resource
    private UserService userService;
    @Resource
    private JwtUtil jwtUtil;
    @ApiOperation("登录")
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

}
