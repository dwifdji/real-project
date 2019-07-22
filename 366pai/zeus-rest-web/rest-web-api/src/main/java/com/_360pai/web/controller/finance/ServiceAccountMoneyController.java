package com._360pai.web.controller.finance;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.finance.ServiceAccountMoneyFacade;
import com._360pai.core.facade.finance.resp.AccountMoneyDTO;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-10-03 14:13
 */
@RestController
public class ServiceAccountMoneyController extends AbstractController {

    @Reference(version = "1.0.0")
    ServiceAccountMoneyFacade serviceAccountMoneyFacade;

    @GetMapping("/confined/finance/getAccountMoneyInfo")
    public ResponseModel getAccountMoneyInfo() {
        isAuth();
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        AccountMoneyDTO accountMoneyDTO =
                serviceAccountMoneyFacade.getAccountMoneyByPartyId(accountInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountMoneyDTO);
    }

}
