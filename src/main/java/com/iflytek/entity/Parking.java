package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("parking")
public class Parking {
    private int parkingid;
    private int bikeid;
    private int status;
}
