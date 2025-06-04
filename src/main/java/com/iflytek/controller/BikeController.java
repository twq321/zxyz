package com.iflytek.controller;

import com.iflytek.dto.CustomUserDetails;
import com.iflytek.service.BikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "自行车管理层")
@RestController
@RequestMapping("/bike")
public class BikeController {
    @Resource
    private BikeService bikeService;
    @ApiOperation("查看自行车")
    @GetMapping
    public ResponseEntity<?> selectBike(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(userDetails.getUser());
        return ResponseEntity.ok(bikeService.findBikesByOwnerId(userDetails.getUser().getUserid()));
    }
}
