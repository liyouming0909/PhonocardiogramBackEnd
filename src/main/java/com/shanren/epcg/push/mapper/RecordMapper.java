package com.shanren.epcg.push.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanren.epcg.push.bean.db.Patient;
import com.shanren.epcg.push.bean.db.Record;
import com.shanren.epcg.push.bean.db.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Title: RecordFactory
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/8 21:41
 */
@Component
@Mapper
public interface RecordMapper extends BaseMapper<Record> {

    @Select("select * from TB_RECORD where userId=#{id}")
    List<Record> selectByUserId(String id);
}
