package com._360pai.core.service.account.impl;

import com._360pai.core.condition.account.TAccountViewRecordCondition;
import com._360pai.core.dao.account.TAccountViewRecordDao;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.account.TAccountViewRecord;
import com._360pai.core.service.account.TAccountViewRecordService;
import com._360pai.core.vo.activity.TAccountViewRecordVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TAccountViewRecordServiceImpl implements TAccountViewRecordService {

    @Autowired
    private TAccountViewRecordDao tAccountViewRecordDao;

    @Override
    public void findAndSaveViewRecord(String accountId, String partyId, String activityId, String key) {TAccountViewRecordCondition tAccountViewRecordCondition = new TAccountViewRecordCondition();
        tAccountViewRecordCondition.setAccountId(Integer.parseInt(accountId));
        tAccountViewRecordCondition.setActivityId(Integer.parseInt(activityId));
        tAccountViewRecordCondition.setPartyId(StringUtils.isNotBlank(partyId)?Integer.parseInt(partyId): null);
        tAccountViewRecordCondition.setType(key);
        TAccountViewRecord tAccountViewRecord = null;
        tAccountViewRecord = tAccountViewRecordDao.selectFirst(tAccountViewRecordCondition);

        //查询列表里已经存在数据
        if(tAccountViewRecord != null) {
            tAccountViewRecord.setUpdateAt(new Date());

            tAccountViewRecordDao.updateById(tAccountViewRecord);
        }else{
            tAccountViewRecord = new TAccountViewRecord();
            tAccountViewRecord.setAccountId(Integer.parseInt(accountId));
            tAccountViewRecord.setActivityId(Integer.parseInt(activityId));
            tAccountViewRecord.setPartyId(StringUtils.isNotBlank(partyId)?Integer.parseInt(partyId): null);
            tAccountViewRecord.setCreateAt(new Date());
            tAccountViewRecord.setUpdateAt(new Date());
            tAccountViewRecord.setType(key);

            tAccountViewRecordDao.insert(tAccountViewRecord);
        }
    }

    @Override
    public void updateActivityByPartyId(AcctReq.ViewRecordRequest viewRecordRequest) {
        tAccountViewRecordDao.updateActivityByPartyId(viewRecordRequest);
    }
}
