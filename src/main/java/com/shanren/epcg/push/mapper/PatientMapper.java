package com.shanren.epcg.push.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanren.epcg.push.bean.db.Patient;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: PatientFactory
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/7 0:08
 */
@Component
@Mapper
public interface PatientMapper extends BaseMapper<Patient> {

    @Select("select p.* from TB_PATIENT p ,TB_Record r where p.id=r.patientId and r.userId= #{id}")
    List<Patient> contacts(String id);

    @Select("select name from TB_PATIENT where id = #{patientId}")
    Patient selectByPrimaryKey(String patientId);
}
