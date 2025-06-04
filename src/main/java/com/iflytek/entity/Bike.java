package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "bike")
public class Bike {
    @TableId(type = IdType.AUTO)
    private int bikeid;
    private int userid;
    private int status;
    private String type;
    private int parkingid;
    private int ownerid;
}
