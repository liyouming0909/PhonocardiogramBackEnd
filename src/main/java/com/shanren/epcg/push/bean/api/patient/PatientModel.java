package com.shanren.epcg.push.bean.api.patient;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * @author qiujuer Email:qiujuer@live.cn
 * @version 1.0.0
 */
@Data
public class PatientModel {

    private String id;  //主键

    private String name;

    private int sex;

    private String age;

    private String height;

    private String weight;

    private String cardType;

    private String cardId;

    private String phone;

    private String pastMedicalHistory;

    private String dragUseSituation;

    private String otherInfo;

    // 校验
    public static boolean check(PatientModel model) {
        return model != null
                && StringUtils.isNotEmpty(model.name)
                && StringUtils.isNotEmpty(model.age)
                && model.sex != 0
                && StringUtils.isNotEmpty(model.cardId);

    }
}
