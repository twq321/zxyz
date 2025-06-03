package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "bike")
public class Bike {
    private int bikeid;
    private int userid;
    private int status;
    private String type;
    private int parkingid;
}
