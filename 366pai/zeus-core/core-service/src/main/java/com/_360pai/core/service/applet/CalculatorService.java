package com._360pai.core.service.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.facade.applet.resp.CalculatorResp;
import com._360pai.core.facade.applet.vo.CalculatorBroadcast;
import com._360pai.core.facade.applet.vo.CalculatorHistory;
import com._360pai.core.model.numberJump.TCalculatorRequestRecord;

/**
 * @author xdrodger
 * @Title: NumberJumpService
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-28 15:03
 */

public interface CalculatorService {

    CalculatorResp.LoginResp login(CalculatorReq.LoginReq req);

    ResponseModel calculatorBroadcastPay(CalculatorReq.CalculatorBroadcastPayReq req);

    ResponseModel calculatorBroadcastPayCallBack(CalculatorReq.CalculatorBroadcastPayCallBackReq req);

    PageInfoResp<CalculatorHistory> getCalculatorHistoryV2(CalculatorReq.QueryHistoryReq req);

    PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastListV2(CalculatorReq.QueryBroadcastReq req);

    void saveCalculatorRecord(TCalculatorRequestRecord record);

    int getUnreadBroadcastCount(Integer extBindId);

    ResponseModel getRelativeList(CalculatorReq.QueryRelativeListReq req);

    ResponseModel getCalculatorQueryCondition(CalculatorReq.CalculatorQueryConditionReq req);

    ResponseModel getRelativeBroadcastList(CalculatorReq.QueryRelativeListReq req);

    Object getCalculatorHistory(CalculatorReq.QueryReq req);

    PageInfoResp<CalculatorBroadcast> getCalculatorBroadcastList(CalculatorReq.QueryReq req);

    ResponseModel dataFix();
}
