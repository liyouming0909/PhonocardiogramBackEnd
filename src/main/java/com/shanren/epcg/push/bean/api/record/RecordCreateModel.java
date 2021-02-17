package com.shanren.epcg.push.bean.api.record;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @Title: RecordCreateModel
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/8 20:10
 */
@Data
public class RecordCreateModel {
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

    public static boolean check(RecordCreateModel model) {
        return model != null
                && StringUtils.isNotEmpty(model.startTime)
                && StringUtils.isNotEmpty(model.endTime)
                && StringUtils.isNotEmpty(model.testType)
                && StringUtils.isNotEmpty(model.area);

    }
}
