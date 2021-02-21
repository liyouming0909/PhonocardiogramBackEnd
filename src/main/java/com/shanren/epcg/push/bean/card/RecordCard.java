package com.shanren.epcg.push.bean.card;

import com.shanren.epcg.push.bean.db.Record;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Title: PatientCard
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/7 22:29
 */
@Data
public class RecordCard {
    private String id;  //主键

    private String startTime;

    private String endTime;

    private boolean uploadStatus;

    //类型（ECG、PCG）
    private String testType;

    //心音部位、心电类型
    private String area;

    private String diagnose;

    private String filename;

    private String fileLocalAddr;

    private String fileCloudAddr;

    private String bodyStatus;

    private String selfFeeling;

    private String otherInfo;

    private String patientId;

    private String userId;

    // 用户信息最后的更新时间
    private LocalDateTime modifyAt;


    public RecordCard(final Record record) {
        this.id = record.getId();
        this.startTime = record.getStartTime();
        this.endTime = record.getEndTime();
        this.uploadStatus = record.isUploadStatus();
        this.testType = record.getTestType();
        this.area = record.getArea();
        this.diagnose = record.getDiagnose();
        this.filename = record.getFilename();
        this.fileLocalAddr = record.getFileLocalAddr();
        this.fileCloudAddr = record.getFileCloudAddr();
        this.bodyStatus = record.getBodyStatus();
        this.selfFeeling = record.getSelfFeeling();
        this.otherInfo = record.getOtherInfo();
        this.userId = record.getUserId();
        this.patientId = record.getPatientId();
        this.modifyAt = record.getUpdateAt();
    }
}

