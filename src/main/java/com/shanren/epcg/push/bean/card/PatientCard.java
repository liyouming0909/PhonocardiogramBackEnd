package com.shanren.epcg.push.bean.card;

import com.shanren.epcg.push.bean.db.Patient;
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
public class PatientCard {
    private String id;  //主键

    private String name;

    private int sex =0;

    private String age;

    private String height;

    private String weight;

    private String cardType;

    private String cardId;

    private String phone;

    private String pastMedicalHistory;

    private String dragUseSituation;

    private String otherInfo;

    // 用户信息最后的更新时间
    private LocalDateTime modifyAt;

    public PatientCard(final Patient patient){
        this.id = patient.getId();
        this.name = patient.getName();
        this.sex = patient.getSex();
        this.age = patient.getAge();
        this.height = patient.getHeight();
        this.weight = patient.getWeight();
        this.cardType = patient.getCardType();
        this.cardId = patient.getCardId();
        this.phone = patient.getPhone();
        this.pastMedicalHistory = patient.getPastMedicalHistory();
        this.dragUseSituation = patient.getDragUseSituation();
        this.otherInfo = patient.getOtherInfo();
        this.modifyAt = patient.getUpdateAt();
    }

}

