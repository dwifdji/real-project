package com._360pai.web.controller.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.FinanceEnum;
import com._360pai.core.facade.finance.ServiceAdjustedRecordFacade;
import com._360pai.core.facade.finance.req.AdjustedRecordReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-03 15:04
 */
@RestController
public class ServiceAdjustedRecordController extends AbstractController {

    @Reference(version = "1.0.0")
    ServiceAdjustedRecordFacade serviceAdjustedRecordFacade;


    @GetMapping("/confined/finance/getCanWithdrawRecord")
    public ResponseModel getCanWithdrawRecord(AdjustedRecordReq.GetAdjustedRecord req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAdjustedStatus(Integer.valueOf(FinanceEnum.AdjustedStatusEnum.CAN_WITHDRAW.getKey()));
        PageInfoResp pageInfoResp = serviceAdjustedRecordFacade.getAdjustedRecord(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @GetMapping("/confined/finance/getPendingWithdrawRecord")
    public ResponseModel getPendingWithdrawRecord(AdjustedRecordReq.GetAdjustedRecord req) {
        PageInfoResp pageInfoResp = serviceAdjustedRecordFacade.getAdjustedRecordByWithdrawNo(req);
        return ResponseModel.succ(pageInfoResp);
    }

}
