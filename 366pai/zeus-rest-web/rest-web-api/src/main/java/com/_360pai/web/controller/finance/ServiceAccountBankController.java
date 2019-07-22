package com._360pai.web.controller.finance;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.finance.ServiceAccountBankFacade;
import com._360pai.core.facade.finance.req.AccountBankReq;
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
 * @create 2018-10-07 00:06
 */
@RestController
public class ServiceAccountBankController extends AbstractController {

    @Reference(version = "1.0.0")
    private ServiceAccountBankFacade serviceAccountBankFacade;

    @PostMapping("/confined/finance/addAccountBank")
    public ResponseModel addAccountBank(@RequestBody AccountBankReq.AddAccountBank req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int id = serviceAccountBankFacade.addAccountBank(req);
        return ResponseModel.succ(id);
    }

    @GetMapping("/confined/finance/getBindingCard")
    public ResponseModel getBindingCard() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyId = accountBaseInfo.getPartyPrimaryId();
        AccountBankDTO card = serviceAccountBankFacade.getBindingCardByPartyId(partyId);
        return ResponseModel.succ(card);
    }

}
