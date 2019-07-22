package com._360pai.core.service.finance.impl;

import com._360pai.core.condition.order.TServiceBusinessRecordCondition;
import com._360pai.core.dao.order.TServiceBusinessRecordDao;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.core.service.finance.ServiceBusinessRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xiaolei
 * @create 2018-09-29 18:50
 */
@Service
public class ServiceBusinessRecordServiceImpl
        implements ServiceBusinessRecordService {

    @Autowired
    private TServiceBusinessRecordDao tServiceBusinessRecordDao;


    @Override
    public int addBusinessRecord(TServiceBusinessRecord record) {
        return tServiceBusinessRecordDao.insert(record);
    }

    @Override
    public PageInfo<TServiceBusinessRecord> getBusinessRecordByBuyerPartyId(Integer buyerPartyId, int pageNum, int pageSize) {
        TServiceBusinessRecordCondition condition = new TServiceBusinessRecordCondition();
        condition.setBuyerPartyId(buyerPartyId);
        condition.setDelFlag(false);
        condition.setPayStatus("10");
        PageHelper.startPage(pageNum, pageSize);
        List<TServiceBusinessRecord> list = tServiceBusinessRecordDao.getBusinessRecordByReportType(condition);
        return new PageInfo<>(list);
    }

    @Override
    public List<TServiceBusinessRecord> getBusinessRecordByType(Integer buyerPartyId, Integer orderType, Integer assetId) {
        TServiceBusinessRecordCondition condition = new TServiceBusinessRecordCondition();
        condition.setBuyerPartyId(buyerPartyId);
        condition.setDelFlag(false);
        condition.setPayStatus("10");
        condition.setOrderType(orderType);
        condition.setAssetId(assetId);
        List<TServiceBusinessRecord> list = tServiceBusinessRecordDao.selectList(condition);
        return list;
    }

}
