package com.winback.core.service.risk.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.condition.risk.TRiskComInfoCondition;
import com.winback.core.condition.risk.TRiskInvestmentCondition;
import com.winback.core.condition.risk.TRiskSearchRecordCondition;

import com.winback.core.condition.risk.TRiskStockInfoCondition;
import com.winback.core.dao.risk.TRiskComInfoDao;
import com.winback.core.dao.risk.TRiskInvestmentDao;
import com.winback.core.dao.risk.TRiskSearchRecordDao;
import com.winback.core.dao.risk.TRiskStockInfoDao;
import com.winback.core.dao.risk.impl.TRiskStockInfoDaoImpl;
import com.winback.core.dto.finance.ExpendDto;
import com.winback.core.facade.risk.req.RiskReq;
import com.winback.core.model.risk.TRiskComInfo;
import com.winback.core.model.risk.TRiskInvestment;
import com.winback.core.model.risk.TRiskSearchRecord;
import com.winback.core.model.risk.TRiskStockInfo;
import com.winback.core.service.risk.RiskService;
import com.winback.core.vo.finance.ExpendVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 15:39
 */
@Service
public class RiskServiceImpl implements RiskService {

    @Autowired
    private TRiskSearchRecordDao  riskSearchRecordDao;


    @Autowired
    private TRiskComInfoDao riskComInfoDao;


    @Autowired
    private TRiskStockInfoDao riskStockInfoDao;

    @Autowired
    private TRiskInvestmentDao riskInvestmentDao;


    @Override
    public List<TRiskSearchRecord> getSearchRecord(TRiskSearchRecordCondition condition) {
        return riskSearchRecordDao.selectList(condition);
    }

    @Override
    public TRiskComInfo getRiskComInfo(TRiskComInfoCondition condition) {
        return riskComInfoDao.selectFirst(condition);
    }

    @Override
    public PageInfo getRiskInvestmentList(RiskReq.keyWordReq req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());

        TRiskInvestmentCondition condition = new TRiskInvestmentCondition();
        condition.setKeyWord(req.getKeyWord());
        condition.setDeleteFlag(false);

        List<TRiskInvestment> list = riskInvestmentDao.selectList(condition);

        return new PageInfo<>(list);
     }

    @Override
    public void saveRiskComInfo(TRiskComInfo info) {
        riskComInfoDao.insert(info);
    }


    @Override
    public Integer saveSearchRecord(TRiskSearchRecord record) {
        riskSearchRecordDao.insert(record);

        return record.getId();
    }

    @Override
    public TRiskStockInfo getRiskStockInf(TRiskStockInfoCondition condition) {
        return riskStockInfoDao.selectFirst(condition);
    }

    @Override
    public void batchRiskInvestment(List<TRiskInvestment> info) {


        riskInvestmentDao.batchSaveRiskInvestment(info);

    }


    @Override
    public void updateSearchRecord(TRiskSearchRecord record) {
        riskSearchRecordDao.updateById(record);
    }
}
