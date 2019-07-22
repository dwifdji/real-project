package com.tzCloud.gateway.facade;

import com.tzCloud.gateway.resp.risk.RiskComInfoResp;
import com.tzCloud.gateway.resp.risk.RiskInvestmentResp;

/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/24 11:24
 */
public interface QiChaChaFacade {


    /**
     * @Param keyWord 查询关键字
     *风控预检 获取公司基本信息
     */

    RiskComInfoResp getRiskComInfo(String keyWord);



    /**
     * @Param keyWord 查询关键字
     *风控预检 获取十层穿透图
     */

    String getStockAnalysisData(String keyWord);



    /**
     * @Param keyWord 查询关键字
     *风控预检 获取对外投资信息
     */
    RiskInvestmentResp searchInvestment(String keyWord, String page, String pageSize);
}
