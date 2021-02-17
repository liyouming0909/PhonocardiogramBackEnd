package com.shanren.epcg.push.service;

import com.shanren.epcg.push.bean.api.base.ResponseModel;
import com.shanren.epcg.push.bean.api.record.RecordCreateModel;
import com.shanren.epcg.push.bean.card.RecordCard;
import com.shanren.epcg.push.bean.db.Record;
import com.shanren.epcg.push.bean.db.User;
import com.shanren.epcg.push.mapper.RecordMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static com.shanren.epcg.push.bean.api.base.ResponseModel.ERROR_CREATE_RECORD;

@Component
public class RecordService {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @Autowired
    private RecordMapper recordMapper;

    public ResponseModel<RecordCard> createRecord(RecordCreateModel model) {

        Record record = new Record(model);
        // 开始注册逻辑
        recordMapper.insert(record);

        if (record != null) {
            // 返回当前的账户
            return ResponseModel.buildOk(new RecordCard(record));
        } else {
            // 注册异常
            return ResponseModel.buildCreateError(ERROR_CREATE_RECORD);
        }
    }

    /**
     * 拉取自己当前的记录列表
     *
     * @return 记录信息列表
     */
    public ResponseModel<List<RecordCard>> list(User user, String dateStr) {
        // 拿时间
        LocalDateTime dateTime = null;
        if (StringUtils.isNotEmpty(dateStr)) {
            try {
                dateTime = LocalDateTime.parse(dateStr + " 23:59:59", FORMATTER);
            } catch (Exception e) {
                dateTime = null;
            }
        }

        List<Record> records = recordMapper.selectByUserId(user.getId());
        if (CollectionUtils.isEmpty(records)) {
            return ResponseModel.buildOk();
        }

        final LocalDateTime finalDateTime = dateTime;
        //转换为UserCard
        List<RecordCard> recordCards = records.stream()
                .filter(record -> finalDateTime == null || record.getUpdateAt().isAfter(finalDateTime)).map(RecordCard::new).collect(Collectors.toList());

        return ResponseModel.buildOk(recordCards);
    }
}

