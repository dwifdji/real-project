package com._360pai.core.service.applet;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.model.numberJump.TDebtCalculator;

/**
 * @author xdrodger
 * @Title: DebtCalculatorService
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-30 10:50
 */
public interface DebtCalculatorService {

    ResponseModel debtCalculatorV2(CalculatorReq.DebtCalculatorReq req);

    ResponseModel getDeptCalculatorBroadcastV2(CalculatorReq.QueryReq req);

    ResponseModel getDebtCalculatorDetailV2(CalculatorReq.QueryReq req);

    void doBroadcastJob();
    void processDebtCalculatorBroadcast(TDebtCalculator item);

    ResponseModel debtCalculatorDel(CalculatorReq.QueryReq req);

    ResponseModel debtCalculatorClose(CalculatorReq.QueryReq req);

    ResponseModel debtCalculator(CalculatorReq.DebtCalculatorReq req);

    ResponseModel getDebtCalculatorDetail(CalculatorReq.QueryReq req);

    ResponseModel getDeptCalculatorBroadcast(CalculatorReq.QueryReq req);
}
