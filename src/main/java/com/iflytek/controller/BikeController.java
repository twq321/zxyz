package com.iflytek.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "自行车管理层")
@RestController
public class BikeController {
    @ApiOperation("查看自行车")
    @GetMapping("/bike")
    public ResponseEntity<?> selectBike(){
        Map<String, Object> data = new HashMap<>();
        data.put("token", "token");
        data.put("user", "loginUser");

        return ResponseEntity.ok(data);
    }
}
