package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

@Data
@TableName("record")
public class Record {
    private int recordid;
    private int bikeid;
    private int userid;
    private String type;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime starttime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private LocalDateTime endtime;

    private DecimalFormat money;
    private int sparkingid;
    private int eparkingid;
    private int touser;
}
