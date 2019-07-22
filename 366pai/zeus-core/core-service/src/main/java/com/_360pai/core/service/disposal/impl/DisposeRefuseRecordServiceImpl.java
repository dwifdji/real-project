package com._360pai.core.service.disposal.impl;

import com._360pai.core.dao.disposal.TDisposeRefuseRecordDao;
import com._360pai.core.model.disposal.TDisposeRefuseRecord;
import com._360pai.core.service.disposal.DisposeRefuseRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaolei
 * @create 2018-11-08 13:27
 */
@Service
public class DisposeRefuseRecordServiceImpl implements DisposeRefuseRecordService {

    @Autowired
    private TDisposeRefuseRecordDao disposeRefuseRecordDao;

    @Override
    public int addRefuseRecord(TDisposeRefuseRecord record) {

        return disposeRefuseRecordDao.insert(record);
    }
}
