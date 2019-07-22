package com._360pai.core.provider.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.CalculatorEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.applet.CalculatorFacade;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TCalculatorRequestRecord;
import com._360pai.core.service.applet.CalculatorService;
import com._360pai.core.service.applet.DebtCalculatorService;
import com._360pai.core.service.applet.PrincipalInterestCalculatorService;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: NumberJumpProvider
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 15:02
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class CalculatorProvider implements CalculatorFacade {

    @Autowired
    private CalculatorService calculatorService;
    @Autowired
    private DebtCalculatorService debtCalculatorService;
    @Autowired
    private PrincipalInterestCalculatorService principalInterestCalculatorService;

    @Override
    public CalculatorResp.LoginResp login(CalculatorReq.LoginReq req) {
        return calculatorService.login(req);
    }

    @Override
    public ResponseModel getDebtCalculatorDetail(CalculatorReq.QueryReq req) {
        return debtCalculatorService.getDebtCalculatorDetail(req);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorDetail(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.getPrincipalInterestCalculatorDetail(req);
    }

    @Override
    public  PageInfoResp<CalculatorBroadcast>  getCalculatorBroadcastList(CalculatorReq.QueryReq req) {
        return calculatorService.getCalculatorBroadcastList(req);
    }

    @Override
    public Object getCalculatorHistory(CalculatorReq.QueryReq req) {
        return calculatorService.getCalculatorHistory(req);
    }


    @Override
    public ResponseModel debtCalculator(CalculatorReq.DebtCalculatorReq req) {
        validateParam(req);
        ResponseModel resp = debtCalculatorService.debtCalculatorV2(req);
        TCalculatorRequestRecord record = new TCalculatorRequestRecord();
        record.setExtBindId(req.getExtBindId());
        record.setType(CalculatorEnum.Type.Debt.getKey());
        record.setInputParameter(JSON.toJSONString(req));
        record.setOutputParameter(JSON.toJSONString(resp));
        calculatorService.saveCalculatorRecord(record);
        return resp;
    }

    @Override
    public ResponseModel debtCalculatorV2(CalculatorReq.DebtCalculatorReq req) {
        // 验证必填项
        validateParam(req);
        // 单位乘以 万
        unitTransfer(req);
        ResponseModel resp = debtCalculatorService.debtCalculatorV2(req);
        TCalculatorRequestRecord record = new TCalculatorRequestRecord();
        record.setExtBindId(req.getExtBindId());
        record.setType(CalculatorEnum.Type.Debt.getKey());
        record.setInputParameter(JSON.toJSONString(req));
        record.setOutputParameter(JSON.toJSONString(resp));
        calculatorService.saveCalculatorRecord(record);
        return resp;
    }

    private void unitTransfer(CalculatorReq.DebtCalculatorReq req) {
        BigDecimal wan = new BigDecimal("10000");

        req.setDebtPrincipal(req.getDebtPrincipal().multiply(wan));
        req.setDebtInterest(req.getDebtInterest().multiply(wan));
        req.setMaximumDebt(req.getMaximumDebt().multiply(wan));
        req.setLiquidationAmount(req.getLiquidationAmount().multiply(wan));
        req.setTransferAmount(req.getTransferAmount().multiply(wan));
        req.setGpAmount(req.getGpAmount().multiply(wan));
        req.setLpAmount(req.getLpAmount().multiply(wan));
        req.setDueDiligenceAmount(req.getDueDiligenceAmount().multiply(wan));
        req.setMiscAmount(req.getMiscAmount().multiply(wan));
    }

    private void validateParam(CalculatorReq.DebtCalculatorReq req) {
        if (req.getLiquidationAmount() == null || req.getTransferAmount() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getTransferAmount() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getDisposalPeriod() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }

        if (req.getWithFundingPeriod() == null){
            req.setWithFundingPeriod(BigDecimal.ZERO);
        }
        //设置默认值
        if (req.getWithFundingAnnualizedRate() == null || req.getWithFundingAnnualizedRate().compareTo(BigDecimal.ZERO) < 0) {
            req.setWithFundingAnnualizedRate(BigDecimal.ZERO);
        }
        if (req.getDisposalAnnualizedRate() == null || req.getDisposalAnnualizedRate().compareTo(BigDecimal.ZERO) < 0) {
            req.setDisposalAnnualizedRate(BigDecimal.ZERO);
        }
        if (req.getGpAmount() == null) {
            req.setGpAmount(BigDecimal.ZERO);
        }

        if (req.getDebtInterest() == null) {
            req.setDebtInterest(BigDecimal.ZERO);
        }
        if (req.getMaximumDebt() == null) {
            req.setMaximumDebt(BigDecimal.ZERO);
        }
        if (req.getDebtPrincipal() == null) {
            req.setDebtPrincipal(BigDecimal.ZERO);
        }

        if (req.getMiscAmount() == null) {
            req.setMiscAmount(BigDecimal.ZERO);
        }
        if (req.getLpAmount() == null) {
            req.setLpAmount(BigDecimal.ZERO);
        }
        if (req.getDueDiligenceAmount() == null) {
            req.setDueDiligenceAmount(BigDecimal.ZERO);
        }
        if (req.getGpAmount() == null) {
            req.setGpAmount(BigDecimal.ZERO);
        }
        if (req.getTransferAmount() == null) {
            req.setTransferAmount(BigDecimal.ZERO);
        }
        if (req.getLiquidationAmount() == null) {
            req.setLiquidationAmount(BigDecimal.ZERO);
        }

    }
    @Override
    public ResponseModel principalInterestCalculator(CalculatorReq.PrincipalInterestCalculatorReq req) {
        validateParam(req);
        ResponseModel resp = principalInterestCalculatorService.principalInterestCalculatorV2(req);
        TCalculatorRequestRecord record = new TCalculatorRequestRecord();
        record.setExtBindId(req.getExtBindId());
        record.setType(CalculatorEnum.Type.Debt.getKey());
        record.setInputParameter(JSON.toJSONString(req));
        record.setOutputParameter(JSON.toJSONString(resp));
        calculatorService.saveCalculatorRecord(record);
        return resp;
    }

    @Override
    public ResponseModel principalInterestCalculatorV2(CalculatorReq.PrincipalInterestCalculatorReq req) {
        // 参数验证
        validateParam(req);

        //参数单位 为 万元
        transferTo(req);

        ResponseModel resp = principalInterestCalculatorService.principalInterestCalculatorV2(req);
        TCalculatorRequestRecord record = new TCalculatorRequestRecord();
        record.setExtBindId(req.getExtBindId());
        record.setType(CalculatorEnum.Type.Debt.getKey());
        record.setInputParameter(JSON.toJSONString(req));
        record.setOutputParameter(JSON.toJSONString(resp));
        calculatorService.saveCalculatorRecord(record);
        return resp;
    }

    private void transferTo(CalculatorReq.PrincipalInterestCalculatorReq req) {
        req.setRemainPrincipal(req.getRemainPrincipal().multiply(new BigDecimal("10000")));
        req.setDebtInterest(req.getDebtInterest().multiply(new BigDecimal("10000")));
    }

    private void validateParam(CalculatorReq.PrincipalInterestCalculatorReq req) {
        if (req.getRemainPrincipal() == null) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (req.getDebtInterest() == null) {
            req.setDebtInterest(BigDecimal.ZERO);
        }
        if (req.getOverdueStart() == null || req.getOverdueEnd() == null || req.getOverdueStart().compareTo(req.getOverdueEnd()) > 0) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
//        if (req.getDelayPerformanceStart() == null || req.getDelayPerformanceEnd() == null || req.getDelayPerformanceStart().compareTo(req.getDelayPerformanceEnd()) > 0) {
//            throw new BusinessException(ApiCallResult.EMPTY);
//        }
        if (req.getFluctuationRate() == null) {
            req.setFluctuationRate(BigDecimal.ZERO);
        }
        if (req.getLoanPeriod() == null) {
            req.setLoanPeriod(new BigDecimal("36"));
        }
        if (req.getPenaltyRate() == null) {
            req.setPenaltyRate(BigDecimal.ZERO);
        }
    }

    @Override
    public ResponseModel calculatorBroadcastPay(CalculatorReq.CalculatorBroadcastPayReq req) {
        return calculatorService.calculatorBroadcastPay(req);
    }

    @Override
    public ResponseModel calculatorBroadcastPayCallBack(CalculatorReq.CalculatorBroadcastPayCallBackReq req) {
        return calculatorService.calculatorBroadcastPayCallBack(req);
    }

    @Override
    public PageInfoResp<CalculatorHistory> getCalculatorHistoryV2(CalculatorReq.QueryHistoryReq req) {
        return calculatorService.getCalculatorHistoryV2(req);
    }

    @Override
    public PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastListV2(CalculatorReq.QueryBroadcastReq req) {
        return calculatorService.getCalculatorBroadcastListV2(req);
    }

    @Override
    public ResponseModel getDeptCalculatorBroadcast(CalculatorReq.QueryReq req) {
        return debtCalculatorService.getDeptCalculatorBroadcast(req);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorBroadcast(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.getPrincipalInterestCalculatorBroadcast(req);
    }

    @Override
    public ResponseModel getDeptCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        return debtCalculatorService.getDeptCalculatorBroadcastV2(req);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorBroadcastV2(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.getPrincipalInterestCalculatorBroadcastV2(req);
    }

    @Override
    public ResponseModel getDebtCalculatorDetailV2(CalculatorReq.QueryReq req) {
        return debtCalculatorService.getDebtCalculatorDetailV2(req);
    }

    @Override
    public ResponseModel getPrincipalInterestCalculatorDetailV2(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.getPrincipalInterestCalculatorDetailV2(req);
    }

    @Override
    public int getUnreadBroadcastCount(Integer extBindId) {
        return calculatorService.getUnreadBroadcastCount(extBindId);
    }


    @Override
    public ResponseModel debtCalculatorDel(CalculatorReq.QueryReq req) {
        return debtCalculatorService.debtCalculatorDel(req);
    }

    @Override
    public ResponseModel principalInterestCalculatorDel(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.principalInterestCalculatorDel(req);
    }

    @Override
    public ResponseModel debtCalculatorClose(CalculatorReq.QueryReq req) {
        return debtCalculatorService.debtCalculatorClose(req);
    }

    @Override
    public ResponseModel principalInterestCalculatorClose(CalculatorReq.QueryReq req) {
        return principalInterestCalculatorService.principalInterestCalculatorClose(req);
    }

    @Override
    public ResponseModel getRelativeList(CalculatorReq.QueryRelativeListReq req) {
        return calculatorService.getRelativeList(req);
    }

    @Override
    public ResponseModel getCalculatorQueryCondition(CalculatorReq.CalculatorQueryConditionReq req) {
        return calculatorService.getCalculatorQueryCondition(req);
    }

    @Override
    public ResponseModel getRelativeBroadcastList(CalculatorReq.QueryRelativeListReq req) {
        return calculatorService.getRelativeBroadcastList(req);
    }

    @Override
    public ResponseModel dataFix() {
        return calculatorService.dataFix();
    }
}
