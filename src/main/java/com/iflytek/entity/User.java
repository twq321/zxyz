package com.iflytek.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "user")
public class User {
    private Integer userid;

    private String username;

    private String logintext;

    private String password;

    private String voice;

    private String hand;

    private String avatar;

    private Integer usebikeid;

    private Integer bikeid;
}
