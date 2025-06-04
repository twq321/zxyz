package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("parking")
public class Parking {
    @TableId(type = IdType.AUTO)
    private int parkingid;
    private int bikeid;
    private int status;
    private int recordid;
}
