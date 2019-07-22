package com._360pai.core.service.assistant.impl;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.BackstageOperationEnum;
import com._360pai.core.condition.assistant.TBackstageOperationRecordCondition;
import com._360pai.core.dao.assistant.TBackstageOperationRecordDao;
import com._360pai.core.model.assistant.TBackstageOperationRecord;
import com._360pai.core.service.assistant.TBackstageOperationRecodService;
import com._360pai.core.utils.ServicePayUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/15 10:13
 */
@Service
public class TBackstageOperationRecodServiceImpl implements TBackstageOperationRecodService {

    @Autowired
    private TBackstageOperationRecordDao tBackstageOperationRecordDao;
    @Autowired
    private ServicePayUtils              servicePayUtils;

    @Override
    public Integer insertRecode(BackstageOperationEnum.Type type, Long refId, String content, Integer operatorId) {
        TBackstageOperationRecord tBackstageOperationRecord = new TBackstageOperationRecord();
        tBackstageOperationRecord.setCreateTime(new Date());
        tBackstageOperationRecord.setType(type.getKey());
        tBackstageOperationRecord.setContent(content);
        tBackstageOperationRecord.setRefId(refId);
        tBackstageOperationRecord.setOperatorId(operatorId);
        tBackstageOperationRecordDao.insert(tBackstageOperationRecord);
        return tBackstageOperationRecord.getId();
    }

    @Override
    public TBackstageOperationRecord selectLastRecode(BackstageOperationEnum.Type type, Long refId, String content) {
        TBackstageOperationRecordCondition tBackstageOperationRecordCondition = new TBackstageOperationRecordCondition();
        tBackstageOperationRecordCondition.setType(type.getKey());
        tBackstageOperationRecordCondition.setContent(content);
        tBackstageOperationRecordCondition.setRefId(refId);
        List<TBackstageOperationRecord> tBackstageOperationRecords = tBackstageOperationRecordDao.selectList(tBackstageOperationRecordCondition);
        if (CollectionUtils.isEmpty(tBackstageOperationRecords)) {
            return null;
        }
        //根据创建时间倒排
        tBackstageOperationRecords = tBackstageOperationRecords.stream().sorted(Comparator.comparing(TBackstageOperationRecord::getCreateTime)
                .reversed()).collect(Collectors.toList());

        return tBackstageOperationRecords.get(0);
    }

    @Override
    public String getPayDeadlineTimestamp(BackstageOperationEnum.Type type, Long refId, String content) {
        TBackstageOperationRecordCondition tBackstageOperationRecordCondition = new TBackstageOperationRecordCondition();
        tBackstageOperationRecordCondition.setType(type.getKey());
        tBackstageOperationRecordCondition.setContent(content);
        tBackstageOperationRecordCondition.setRefId(refId);
        List<TBackstageOperationRecord> tBackstageOperationRecords = tBackstageOperationRecordDao.selectList(tBackstageOperationRecordCondition);
        if (CollectionUtils.isEmpty(tBackstageOperationRecords)) {
            return "";
        }
        //根据创建时间倒排
        tBackstageOperationRecords = tBackstageOperationRecords.stream().sorted(Comparator.comparing(TBackstageOperationRecord::getCreateTime)
                .reversed()).collect(Collectors.toList());
        TBackstageOperationRecord tBackstageOperationRecord = tBackstageOperationRecords.get(0);
        // 暂定24小时  支付过期
        Calendar c = Calendar.getInstance();
        c.setTime(tBackstageOperationRecord.getCreateTime());
//        c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MINUTE, servicePayUtils.getServicePayExpiredTime());
        return DateUtil.format(c.getTime(),DateUtil.NORM_DATETIME_PATTERN);
    }
}
