package com.tzCloud.core.service.impl;

import com.tzCloud.core.constant.AssistantEnum;
import com.tzCloud.core.dao.account.TAccountSearchRecordDao;
import com.tzCloud.core.model.account.TAccountSearchRecord;
import com.tzCloud.core.service.AccountSearchRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xdrodger
 * @Title: AccountSearchRecordServiceImpl
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019/6/25 09:21
 */
@Slf4j
@Service
public class AccountSearchRecordServiceImpl implements AccountSearchRecordService {
    @Autowired
    private TAccountSearchRecordDao accountSearchRecordDao;


    @Override
    public void saveSearchRecord(AssistantEnum.SearchType type, Integer accountId, String content) {
        try {
            TAccountSearchRecord record = new TAccountSearchRecord();
            record.setAccountId(accountId);
            record.setType(type.getKey());
            record.setContent(content);
            accountSearchRecordDao.insert(record);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
