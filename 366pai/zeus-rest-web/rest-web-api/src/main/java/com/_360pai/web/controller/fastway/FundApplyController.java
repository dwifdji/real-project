package com._360pai.web.controller.fastway;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.facade.fastway.FundApplyFacade;
import com._360pai.core.facade.fastway.req.FundApplyReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2018-12-07 10:20
 */
@RestController
public class FundApplyController extends AbstractController {

    @Reference(version = "1.0.0")
    private FundApplyFacade fundApplyFacade;

    @PostMapping("/confined/fastWay/fund/user/apply")
    public ResponseModel userApply(@RequestBody FundApplyReq.UserApplyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ(fundApplyFacade.userApplyFund(req, accountBaseInfo.getAccountId()));
    }

    @PostMapping("/confined/fastWay/fund/company/apply")
    public ResponseModel companyApply(@RequestBody FundApplyReq.CompanyApplyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ(fundApplyFacade.companyApplyFund(req, accountBaseInfo.getAccountId()));
    }

    @GetMapping("/confined/fastWay/fund/userFundAuthInfo")
    public ResponseModel getUserFundAuthInfoByType() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ( fundApplyFacade.fundAuthInfo(accountBaseInfo.getAccountId(), FastwayEnum.FundType.User));
    }

    @GetMapping("/confined/fastWay/fund/companyFundAuthInfo")
    public ResponseModel getCompanyFundAuthInfoByType() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        return ResponseModel.succ( fundApplyFacade.fundAuthInfo(accountBaseInfo.getAccountId(), FastwayEnum.FundType.Company));
    }
}
