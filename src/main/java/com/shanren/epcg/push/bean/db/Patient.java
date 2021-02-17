package com.shanren.epcg.push.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanren.epcg.push.bean.api.patient.PatientModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * @Title: Patient
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/6 16:18
 */

@Data
@TableName("TB_PATIENT")
public class Patient implements Serializable {
    //生成存储的类型是uuid类型
    //把UUID的生成器定义为UUID2,UUID2是常规的uuid toString
    //uuid2 使uuid之间有横杠
    //不允许更改，不允许为null
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;  //主键

    @TableField("name")
    private String name;

    @TableField("age")
    private String age;

    @TableField("sex")
    private int sex = 0;

    @TableField("height")
    private String height;

    @TableField("weight")
    private String weight;

    //住院卡号/身份证/
    @TableField("cardType")
    private String cardType;

    @TableField("cardId")
    private String cardId;

    @TableField("phone")
    private String phone;

    //疾病史
    @TableField("pastMedicalHistory")
    private String pastMedicalHistory;

    //用药情况
    @TableField("dragUseSituation")
    private String dragUseSituation;

    @TableField("otherInfo")
    private String otherInfo;

    // 患者的的记录
    // 对应的数据库表字段为TB_RECORD.patientId
    // 1对多，一个患者可以有很多记录，每一条都是一个记录
    //private Set<Record> records = new HashSet<>();

    //定义为创建时间戳，在创建时就已经写入
    @TableField("createAt")
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，在创建时就已经写入
    @TableField("updateAt")
    private LocalDateTime updateAt = LocalDateTime.now();


    public Patient(){
    }

    public Patient(PatientModel model) {
        this.name = model.getName();
        this.age = model.getAge();
        this.sex = model.getSex();
        this.height = model.getHeight();
        this.weight = model.getWeight();
        this.cardType = model.getCardType();
        this.cardId = model.getCardId();
        this.phone = model.getPhone();
        this.pastMedicalHistory = model.getPastMedicalHistory();
        this.dragUseSituation = model.getDragUseSituation();
        this.otherInfo = model.getOtherInfo();
    }
}
