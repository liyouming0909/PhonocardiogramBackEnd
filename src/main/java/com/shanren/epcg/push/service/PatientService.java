package com.shanren.epcg.push.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.api.patient.PatientModel;
import com.shanren.epcg.push.bean.card.PatientCard;
import com.shanren.epcg.push.bean.db.Patient;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.mapper.PatientMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import static com.shanren.epcg.push.bean.api.base.ResponseModel.ERROR_DELETE_PATIENT;

/**
 * @Title: PatientService
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/8 11:22
 */
@Service
public class PatientService {

    @Autowired
    private PatientMapper patientMapper;

    public ResponseModel<PatientCard> addPatient(PatientModel model){
        if (!PatientModel.check(model)) {
            // 返回参数异常
            return ResponseModel.buildParameterError();
        }

        //检查是否已有
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cardId",model.getCardId());
        int count =patientMapper.selectCount(queryWrapper);
        if(count != 0){
            return ResponseModel.buildHavePatientError();
        }

        // 开始添加信息
        Patient patient = new Patient(model);
        patientMapper.insert(patient);
        return ResponseModel.buildOk(new PatientCard(patient));
    }

    //获取某人的信息
    public ResponseModel<PatientCard> getPatient(String patientId){

        if(StringUtils.isEmpty(patientId)){
            //参数异常
            return ResponseModel.buildParameterError();
        }

        Patient patient = patientMapper.selectById(patientId);
        if (patient == null){
            //没找到，返回没找到患者
            return ResponseModel.buildNotFoundPatientError(null);
        }

        return ResponseModel.buildOk(new PatientCard(patient));
    }

    //拉取患者库
    public ResponseModel<List<PatientCard>> contacts(User user) {
        //拿到我的联系人
        List<Patient> patients =patientMapper.contacts(user.getId());

        //转换为UserCard
        List<PatientCard> patientCards = patients.stream().map(PatientCard::new).collect(Collectors.toList());//map操作，相当于转置操作，User -> UserCard

        return ResponseModel.buildOk(patientCards);
    }

    public ResponseModel<List<PatientCard>> search(String name) {
        QueryWrapper<Patient> queryWrapper;
        queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name",name);
        queryWrapper.or();
        queryWrapper.like("phone",name);
        queryWrapper.or();
        queryWrapper.like("cardId",name);
        //先查询数据
        List<Patient> searchPatients = patientMapper.selectList(queryWrapper);

        //把查询的人封装为UserCard
        //判断这些人是否有我已经关注的人
        //如果有，则返回的关注状态中应该已经设置好状态

        //把User转换为UserCard2
        List<PatientCard> patientCards = searchPatients.stream().map(patient -> new PatientCard(patient)).collect(Collectors.toList());

        return ResponseModel.buildOk(patientCards);
    }

    public ResponseModel<PatientCard> delete(String patientId) {
        int i = patientMapper.deleteById(patientId);
        if(i != 1){
            return ResponseModel.buildCreateError(ERROR_DELETE_PATIENT);
        }

        return ResponseModel.buildOk();
    }
}
