package com._360pai.core.service.activity.impl;

import com._360pai.core.dao.activity.TAuctionStepRecordDao;
import com._360pai.core.model.activity.TAuctionStepRecord;
import com._360pai.core.service.activity.AuctionStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author RuQ
 * @Title: AuctionStepServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/10/11 12:40
 */
@Service
public class AuctionStepServiceImpl implements AuctionStepService {

    @Autowired
    private TAuctionStepRecordDao tAuctionStepRecordDao;

    @Override
    public boolean saveAuctionStep(TAuctionStepRecord record) {
        return tAuctionStepRecordDao.insert(record) == 1;
    }
}
