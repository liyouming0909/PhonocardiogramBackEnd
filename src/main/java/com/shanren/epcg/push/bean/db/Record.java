package com.shanren.epcg.push.bean.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.shanren.epcg.push.bean.api.record.RecordCreateModel;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Title: Record
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/6 21:47
 */
@Data
@TableName("TB_RECORD")
public class Record implements Serializable {
    //生成存储的类型是uuid类型
    //把UUID的生成器定义为UUID2,UUID2是常规的uuid toString
    //uuid2 使uuid之间有横杠
    //不允许更改，不允许为null
    // 列
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;  //主键

    @TableField("startTime")
    private String startTime;

    @TableField("endTime")
    private String endTime;

    @TableField("uploadStatus")
    private boolean uploadStatus;

    //类型（ECG、PCG）
    @TableField("testType")
    private String testType;

    @TableField("area")
    private String area;

    @TableField("diagnose")
    private String diagnose;

    @TableField("filename")
    private String filename;

    @TableField("fileLocalAddr")
    private String fileLocalAddr;

    @TableField("fileCloudAddr")
    private String fileCloudAddr;

    @TableField("bodyStatus")
    private String bodyStatus;

    @TableField("selfFeeling")
    private String selfFeeling;

    @TableField("otherInfo")
    private String otherInfo;

    @TableField("userId")
    private String userId;

    @TableField("patientId")
    private String patientId;


    //定义为创建时间戳，在创建时就已经写入
    @TableField("createAt")
    private LocalDateTime createAt = LocalDateTime.now();

    //定义为更新时间戳，在创建时就已经写入
    @TableField("updateAt")
    private LocalDateTime updateAt = LocalDateTime.now();

    public Record(){

    }

    public Record(RecordCreateModel model){
        this.id = model.getId();
        this.startTime = model.getStartTime();
        this.endTime = model.getEndTime();
        this.uploadStatus = model.isUploadStatus();
        this.testType = model.getTestType();
        this.area = model.getArea();
        this.diagnose = model.getDiagnose();
        this.filename = model.getFilename();
        this.fileLocalAddr = model.getFileLocalAddr();
        this.fileCloudAddr = model.getFileCloudAddr();
        this.bodyStatus = model.getBodyStatus();
        this.selfFeeling = model.getSelfFeeling();
        this.otherInfo = model.getOtherInfo();
        this.userId = model.getUserId();
        this.patientId = model.getPatientId();
    }
}
