package com._360pai.web.controller.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.finance.ServiceBusinessRecordFacade;
import com._360pai.core.facade.finance.req.BusinessRecordReq;
import com._360pai.core.facade.finance.resp.PurchaseHistoryDTO;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-03 10:34
 */
@RestController
public class ServiceBusinessRecordController extends AbstractController {

    @Reference(version = "1.0.0")
    ServiceBusinessRecordFacade serviceBusinessRecordFacade;

    @GetMapping("/confined/finance/getPurchaseRecord")
    public ResponseModel getPurchaseRecord(BusinessRecordReq.GetBusinessRecord req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageInfoResp<PurchaseHistoryDTO> pageInfoResp = serviceBusinessRecordFacade.getPurchaseHistoryByBuyerPartyId(req);
        return ResponseModel.succ(pageInfoResp);
    }

}
