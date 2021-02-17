package com.shanren.epcg.push.bean.db;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Title: User
 * @Description: 用户的Model,对应数据库
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2020/12/30 17:03
 */
@Data
@TableName("TB_USER")
public class User  {
    //生成存储的类型是uuid类型
    //把UUID的生成器定义为UUID2,UUID2是常规的uuid toString
    //uuid2 使uuid之间有横杠
    //不允许更改，不允许为null
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;  //主键

    //设备名必须唯一  这个作为账户名
    @TableField("name")
    private String name;   //

    @TableField("password")
    private String password;

    //联系电话
    @TableField("phone")
    private String phone;

    //地区
    @TableField("area")
    private String area;

    //医院
    @TableField("unitName")
    private String unitName;

    //科室
    @TableField("department")
    private String department;

    //设备管理员
    @TableField("deviceAdmin")
    private String deviceAdmin;

    @TableField("portrait")
    private String portrait;

    //token可以拉取用户信息，所以token必须唯一
    @TableField("token")
    private String token;

    //用于推送的设备ID
    @TableField("pushId")
    private String pushId;

    //定义为创建时间戳，在创建时就已经写入
    @TableField("createAt")
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，在创建时就已经写入
    @TableField("updateAt")
    private LocalDateTime updateAt = LocalDateTime.now();

    //最后一次收到消息的时间
    @TableField("lastReceivedAt")
    private LocalDateTime lastReceivedAt = LocalDateTime.now();
}
