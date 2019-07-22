package com._360pai.web.controller.finance;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.finance.ServiceAccountBankFacade;
import com._360pai.core.facade.finance.ServiceWithdrawRecordFacade;
import com._360pai.core.facade.finance.req.WithdrawRecordReq;
import com._360pai.core.facade.finance.resp.AccountBankDTO;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-07 20:30
 */
@RestController
public class ServiceWithdrawRecordController extends AbstractController {

    @Reference(version = "1.0.0")
    ServiceWithdrawRecordFacade serviceWithdrawRecordFacade;
    @Reference(version = "1.0.0")
    ServiceAccountBankFacade    serviceAccountBankFacade;

    @PostMapping("/confined/finance/addWithdrawRecord")
    public ResponseModel addWithdrawRecord(@RequestBody WithdrawRecordReq.AddWithdrawRecord req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        AccountBankDTO card = serviceAccountBankFacade.getBindingCardByPartyId(accountBaseInfo.getPartyPrimaryId());
        if (card == null) {
            return ResponseModel.fail("请先绑定银行卡");
        }
        req.setBankId(card.getBankId());
        req.setAccountType(accountBaseInfo.getType());
        req.setAccountName(accountBaseInfo.getName());
        req.setBankNo(card.getBankNo());
        int id = serviceWithdrawRecordFacade.addWithdrawRecord(req);
        return ResponseModel.succ(id);
    }

    @GetMapping("/confined/finance/getWithdrawRecordPage")
    public ResponseModel getWithdrawRecordPage(WithdrawRecordReq.GetWithdrawRecord req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageInfoResp pageInfoResp = serviceWithdrawRecordFacade.getWithdrawRecordPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    @PostMapping("/confined/finance/resubmitWithdrawRecord")
    public ResponseModel resubmitWithdrawRecord(@RequestBody WithdrawRecordReq.AddWithdrawRecord req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        AccountBankDTO  card            = serviceAccountBankFacade.getBindingCardByPartyId(accountBaseInfo.getPartyPrimaryId());
        if (card == null) {
            return ResponseModel.fail("请先绑定银行卡");
        }
        req.setBankId(card.getBankId());
        serviceWithdrawRecordFacade.resubmitWithdrawRecord(req);
        return ResponseModel.succ();
    }

}
