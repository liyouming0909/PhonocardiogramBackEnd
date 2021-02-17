package com.shanren.epcg.push.controller;

import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.api.record.RecordCreateModel;
import com.shanren.epcg.push.bean.card.RecordCard;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Title: RecordService
 * @Description:
 * @Auther: jeff0213
 * @Version: 1.0
 * @create 2021/1/8 21:27
 */
@RestController
public class RecordController {

    @Autowired
    private RecordService recordService;

    // 指定请求与返回的相应体为JSON
    @PostMapping("/record/createRecord")
    public ResponseModel<RecordCard> createRecord(@RequestBody RecordCreateModel model){
        if (!RecordCreateModel.check(model)) {
            return ResponseModel.buildParameterError();  // 返回参数异常
        }

        return recordService.createRecord(model);
    }

    /**
     * 拉取自己当前的记录列表
     *
     * @param dateStr 时间字段，不传递，则返回全部当前的记录列表；有时间，则返回这个时间之后的加入的记录
     * @return 记录信息列表
     */
    @GetMapping("/record/list/{date}")
    public ResponseModel<List<RecordCard>> list(@PathVariable(value = "date") String dateStr,@RequestAttribute("user") User self) {
        return recordService.list(self,dateStr);
    }
}

