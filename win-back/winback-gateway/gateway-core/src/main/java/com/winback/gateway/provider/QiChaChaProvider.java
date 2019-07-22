package com.winback.gateway.provider;

import com.alibaba.dubbo.config.annotation.Service;

import com.winback.gateway.service.check.QiChaChaService;
import com.winback.gateway.facade.QiChaChaFacade;
import com.winback.gateway.resp.risk.RiskComInfoResp;
import com.winback.gateway.resp.risk.RiskInvestmentResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 描述：
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2019/1/22 13:50
 */
@Component
@Service(version = "1.0.0")
public class QiChaChaProvider implements QiChaChaFacade {

    @Autowired
    private QiChaChaService qiChaChaService;


    @Override
    public RiskComInfoResp getRiskComInfo(String keyWord) {
        return qiChaChaService.getRiskComInfo(keyWord);
    }

    @Override
    public String getStockAnalysisData(String keyWord) {
        return qiChaChaService.getStockAnalysisData(keyWord);
    }

    @Override
    public RiskInvestmentResp searchInvestment(String keyWord, String page, String pageSize) {
        return qiChaChaService.searchInvestment(keyWord,page,pageSize);
    }
}
