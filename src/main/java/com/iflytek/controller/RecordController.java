package com.iflytek.controller;

import com.iflytek.service.RecordService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/record")
@Api(tags = "记录控制层")
public class RecordController {

    @Autowired
    private RecordService recordService;



}
