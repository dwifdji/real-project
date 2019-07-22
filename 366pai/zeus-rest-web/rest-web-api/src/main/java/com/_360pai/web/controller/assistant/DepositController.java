package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.assistant.DepositFacade;
import com._360pai.core.facade.assistant.req.DepositReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: DepositController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/12 13:58
 */
@RestController
public class DepositController extends AbstractController {

    @Reference(version = "1.0.0")
    private DepositFacade depositFacade;

    @GetMapping(value = "/confined/deposit/myDeposit")
    public ResponseModel myDepositList(DepositReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        req.setPartyId(partyPrimaryId);
        PageInfo pageInfo = depositFacade.myDepositList(req);
        return ResponseModel.succ(pageInfo);
    }
}
