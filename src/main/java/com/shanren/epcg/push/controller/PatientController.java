package com.shanren.epcg.push.controller;

import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.api.patient.PatientModel;
import com.shanren.epcg.push.bean.card.PatientCard;
import com.shanren.epcg.push.bean.card.RecordCard;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: PatientService
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/8 11:22
 */

@RestController
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping("/patient/addPatient")
    public ResponseModel<PatientCard> addPatient(@RequestBody PatientModel model) {
        return patientService.addPatient(model);
    }

    //获取某人的信息
    @GetMapping("/patient/{patientId}")
    public ResponseModel<PatientCard> getPatient(@PathVariable("patientId") String patientId) {
        return patientService.getPatient(patientId);
    }

    //拉取患者库
    @GetMapping("/patient/contact")
    public ResponseModel<List<PatientCard>> contact(@RequestAttribute("user") User self) {
        //拿到我的联系人
        return patientService.contacts(self);
    }

    //搜索人的接口实现
    //为了简化分页，只返回20条数据
    //搜索人，不涉及数据更改，只是查询，则为GET

    @GetMapping("/patient/search/{name}") //名字为任意字符，可以为空
    public ResponseModel<List<PatientCard>> search(@PathVariable(value = "name") String name) {

        //先查询数据
        return patientService.search(name);
    }


    @DeleteMapping("/patient/delete/{patientId}")
    public ResponseModel<PatientCard> deleteRecord(@PathVariable(value = "patientId") String patientId) {
        return patientService.delete(patientId);
    }
}
