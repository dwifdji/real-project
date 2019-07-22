package com.winback.core.service.risk;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.risk.TRiskComInfoCondition;
import com.winback.core.condition.risk.TRiskInvestmentCondition;
import com.winback.core.condition.risk.TRiskSearchRecordCondition;
import com.winback.core.condition.risk.TRiskStockInfoCondition;
import com.winback.core.facade.risk.req.RiskReq;
import com.winback.core.model.risk.TRiskComInfo;
import com.winback.core.model.risk.TRiskInvestment;
import com.winback.core.model.risk.TRiskSearchRecord;
import com.winback.core.model.risk.TRiskStockInfo;

import java.util.List;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 14:33
 */
public interface RiskService {


    List<TRiskSearchRecord> getSearchRecord(TRiskSearchRecordCondition condition);


    TRiskComInfo getRiskComInfo(TRiskComInfoCondition condition);



    PageInfo getRiskInvestmentList(RiskReq.keyWordReq req);


    void saveRiskComInfo(TRiskComInfo info);


    Integer  saveSearchRecord(TRiskSearchRecord record);

    TRiskStockInfo getRiskStockInf(TRiskStockInfoCondition condition);



    void   updateSearchRecord(TRiskSearchRecord record);


    void batchRiskInvestment(List<TRiskInvestment> info);
}
