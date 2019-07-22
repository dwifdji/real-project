package com.winback.core.facade.risk;

import com.winback.arch.common.ResponseModel;
import com.winback.core.facade.risk.req.RiskReq;

/**
 * 描述：风控管理
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:32
 */
public interface RiskFacade {


    ResponseModel getComInfo(RiskReq.keyWordReq req);


    ResponseModel getInvestList(RiskReq.keyWordReq req);


    ResponseModel getEquityInfo(RiskReq.keyWordReq req);


}
