package com._360pai.core.facade.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;

/**
 * @author xdrodger
 * @Title: NumberJumpFacade
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 15:01
 */
public interface CalculatorFacade {

    CalculatorResp.LoginResp login(CalculatorReq.LoginReq req);

    ResponseModel debtCalculatorV2(CalculatorReq.DebtCalculatorReq req);

    ResponseModel principalInterestCalculatorV2(CalculatorReq.PrincipalInterestCalculatorReq req);

    ResponseModel calculatorBroadcastPay(CalculatorReq.CalculatorBroadcastPayReq req);

    ResponseModel calculatorBroadcastPayCallBack(CalculatorReq.CalculatorBroadcastPayCallBackReq req);

    PageInfoResp<CalculatorHistory> getCalculatorHistoryV2(CalculatorReq.QueryHistoryReq req);

    PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastListV2(CalculatorReq.QueryBroadcastReq req);

    ResponseModel getDeptCalculatorBroadcastV2(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorBroadcastV2(CalculatorReq.QueryReq req);

    ResponseModel getDebtCalculatorDetailV2(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorDetailV2(CalculatorReq.QueryReq req);

    int getUnreadBroadcastCount(Integer extBindId);

    ResponseModel debtCalculatorDel(CalculatorReq.QueryReq req);

    ResponseModel principalInterestCalculatorDel(CalculatorReq.QueryReq req);

    ResponseModel debtCalculatorClose(CalculatorReq.QueryReq req);

    ResponseModel principalInterestCalculatorClose(CalculatorReq.QueryReq req);

    ResponseModel getRelativeList(CalculatorReq.QueryRelativeListReq req);

    ResponseModel getCalculatorQueryCondition(CalculatorReq.CalculatorQueryConditionReq req);

    ResponseModel getRelativeBroadcastList(CalculatorReq.QueryRelativeListReq req);

    ResponseModel getDebtCalculatorDetail(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorDetail(CalculatorReq.QueryReq req);

    PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastList(CalculatorReq.QueryReq req);

    Object getCalculatorHistory(CalculatorReq.QueryReq req);

    ResponseModel principalInterestCalculator(CalculatorReq.PrincipalInterestCalculatorReq req);

    ResponseModel debtCalculator(CalculatorReq.DebtCalculatorReq req);

    ResponseModel getDeptCalculatorBroadcast(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorBroadcast(CalculatorReq.QueryReq req);

    ResponseModel dataFix();

}
