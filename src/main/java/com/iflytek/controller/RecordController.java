package com.iflytek.controller;

import com.iflytek.dto.CustomUserDetails;
import com.iflytek.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@Api(tags = "记录控制层")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @ApiOperation("查询订单")
    @GetMapping
    public ResponseEntity<?> selectRecord(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(recordService.selectRecordsById(userDetails.getUser().getUserid()));
    }
}
