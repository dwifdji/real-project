package com._360pai.core.service.applet;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.CalculatorReq;
import com._360pai.core.model.numberJump.TPrincipalInterestCalculator;

/**
 * @author xdrodger
 * @Title: PrincipalInterestCalculatorService
 * @ProjectName dev2-zeus
 * @Description:
 * @date 2019-04-30 10:56
 */
public interface PrincipalInterestCalculatorService {

    ResponseModel principalInterestCalculatorV2(CalculatorReq.PrincipalInterestCalculatorReq req);

    ResponseModel getPrincipalInterestCalculatorBroadcastV2(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorDetailV2(CalculatorReq.QueryReq req);

    void doBroadcastJob();
     void processPrincipalInterestCalculatorBroadcast(TPrincipalInterestCalculator item);

    ResponseModel principalInterestCalculatorDel(CalculatorReq.QueryReq req);

    ResponseModel principalInterestCalculatorClose(CalculatorReq.QueryReq req);

    ResponseModel principalInterestCalculator(CalculatorReq.PrincipalInterestCalculatorReq req);

    ResponseModel getPrincipalInterestCalculatorDetail(CalculatorReq.QueryReq req);

    ResponseModel getPrincipalInterestCalculatorBroadcast(CalculatorReq.QueryReq req);
}
