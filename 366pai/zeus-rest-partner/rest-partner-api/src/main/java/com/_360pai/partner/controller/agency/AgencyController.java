package com._360pai.partner.controller.agency;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.FBindMemberReq;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.AccountAgencyController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xdrodger
 * @Title: AgencyController
 * @ProjectName zeus
 * @Description:
 * @date 14/09/2018 18:58
 */
@RestController
public class AgencyController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountAgencyController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;


    /**
     * 机构列表接口
     */
    @GetMapping(value = "/agency/list")
    public ResponseModel agencyList(AgencyReq.QueryReq req) {
        return ResponseModel.succ(accountFacade.getAgencyListByPage(req));
    }

    /**
     * 机构详情接口
     */
    @GetMapping(value = "/agency/detail")
    public ResponseModel agencyDetail(AgencyReq.BaseReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.getAgencyById(req));
    }

    /**
     * 支付账户余额接口
     */
    @GetMapping(value = "/agency/payment/account/balance")
    public ResponseModel agencyPaymentAccountBalance(AgencyReq.BaseReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.agencyPaymentAccountBalance(req));
    }

    /**
     * 机构账户列表接口
     */
    @GetMapping(value = "/agency/account/list")
    public ResponseModel accountList(AccountReq.QueryReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.getAccountListByPage(req));
    }

    /**
     * 添加机构账号接口
     */
    @PostMapping(value = "/agency/account/add")
    public ResponseModel accountAdd(@RequestBody AccountReq.BaseReq req) {
        Assert.notNull(req.getMobile(), "mobile 参数不能为空");
        AccountBaseInfo account = loadCurLoginAccountInfo();
        req.setOperatorId(account.getAccountId());
        req.setMobile(req.getMobile());
        return ResponseModel.succ(accountFacade.agencyAccountAdd(req));
    }

    /**
     * 删除机构账号接口
     */
    @PostMapping(value = "/agency/account/delete")
    public ResponseModel accountDelete(@RequestBody AccountReq.BaseReq req) {
        Assert.notNull(req.getAccountId(), "accountId 参数不能为空");
        AccountBaseInfo account = loadCurLoginAccountInfo();
        req.setOperatorId(account.getAccountId());
        req.setAccountId(req.getAccountId());
        return ResponseModel.succ(accountFacade.agencyAccountDelete(req));
    }

    /**
     * 更新机构信息接口
     *
     */
    @PostMapping(value = "/agency/update")
    public ResponseModel agencyUpdate(@RequestBody AgencyReq.AgencyUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.updateAgency(req));
    }

}
