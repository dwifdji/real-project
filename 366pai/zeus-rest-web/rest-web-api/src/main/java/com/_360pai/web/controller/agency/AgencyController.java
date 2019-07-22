package com._360pai.web.controller.agency;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AgencyApplyReq;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.req.ApplyAgencyAuthReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.req.AccountRequest;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com._360pai.web.shiro.ShiroAuthenService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * Created by RuQ on 2018/8/19 18:09
 */
@RestController
public class AgencyController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AgencyController.class);

    @Resource
    private ShiroAuthenService shiroAuthenService;

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @PostMapping(value = "/agency_confined/applyAgency")
    public ResponseModel applyAgency(@RequestBody ApplyAgencyAuthReq params) {

        LOGGER.info("开始调用 applyAgency ，请求参数 :{} ", JSON.toJSONString(params));

        if(StringUtils.isEmpty(params.getName())
                || StringUtils.isEmpty(params.getLegalPerson())
                || StringUtils.isEmpty(params.getIdCard())
                || StringUtils.isEmpty(params.getAddress())
        ){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        ApplyAgencyAuthReq req = new ApplyAgencyAuthReq();
        BeanUtils.copyProperties(params,req);
        req.setMobile(accountInfo.getMobile());
        req.setAccountId(accountInfo.getAccountId());
        return ResponseModel.succ();
    }


    /**
     * 退出登录
     */
    @PostMapping(value = "/agency_confined/logout")
    public ResponseModel logout(@RequestBody AccountRequest params) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        shiroAuthenService.removeTicket(accountInfo.getPartyPrimaryId(),accountInfo.getType());
        return ResponseModel.succ();
    }

    /**
     * 机构申请接口
     */
    @PostMapping(value = "/confined/agency/apply")
    public ResponseModel agencyApply(@Valid @RequestBody AgencyApplyReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail("账户尚未认证过，无法申请");
        }
        if (!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())) {
            return ResponseModel.fail("非企业认证账户，无法申请");
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        return ResponseModel.succ(accountFacade.agencyApply(req));
    }

    /**
     * 机构申请详情接口
     */
    @GetMapping(value = "/confined/agency/apply/detail")
    public ResponseModel applyDetail(AgencyApplyReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            ResponseModel.fail("账户尚未认证过，无法申请");
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        return ResponseModel.succ(accountFacade.getAgencyApplyRecordById(req));
    }

    /**
     * 搜索机构列表接口
     */
    @GetMapping(value = "/open/search/agency/list")
    public ResponseModel searchAgencyList(AgencyReq.QueryReq req) {
        return accountFacade.searchAgencyList(req);
    }

    /**
     * 机构列表接口
     */
    @GetMapping(value = "/open/agency/list")
    public ResponseModel getAgencyList(AgencyReq.BaseReq req) {
        return accountFacade.getAgencyList(req);
    }
}
