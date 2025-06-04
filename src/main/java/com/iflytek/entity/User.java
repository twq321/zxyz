package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    @TableId(type = IdType.AUTO)  // 指定主键策略，如果是数据库自增主键用 AUTO
    private Long userid;

    private String username;

    private String logintext;

    private String password;

    private String voice;

    private String hand;

    private String avatar;

    private Integer usebikeid;

    private Integer bikeid;
    private Integer nowborrow;
}
