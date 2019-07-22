package com._360pai.partner.controller.account;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.FBindMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.req.AccountRequest;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com._360pai.partner.controller.account.vo.BindPayVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * @author xdrodger
 * @Title: AccountAgencyController
 * @ProjectName zeus
 * @Description:
 * @date 11/09/2018 14:53
 */
@RestController
@RequestMapping(value = "/agency/account", produces = "application/json;charset=UTF-8")
public class AccountAgencyController extends AbstractController {

    public static final Logger LOGGER = LoggerFactory.getLogger(AccountAgencyController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private AcctFacade acctFacade;

    @Reference(version = "1.0.0")
    private DfftPayFacade dfftPayFacade;

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    /**
     * 个人用户列表
     */
    @GetMapping(value = "/user/list")
    public ResponseModel userList(UserReq.QueryReq req) {
        req.setDefaultAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.getUserListByPage(req));
    }

    /**
     * 企业用户列表
     */
    @GetMapping(value = "/company/list")
    public ResponseModel companyList(CompanyReq.QueryReq req) {
        req.setDefaultAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.getCompanyListByPage(req));
    }

    /**
     * 绑定东方付通
     */
    @PostMapping(value = "/bindPay")
    public ResponseModel bindPay(@RequestBody AccountRequest params) {
        AgencyResp agency = accountFacade.getAgencyById(loadCurLoginAgencyId());
        if (agency.getPayBind() != null && agency.getPayBind().equals(1)) {
            return ResponseModel.succ();
        }
        return invokeGatewayBindDfft(agency.getName(), agency.getDfftId(), "1");
    }

    private  ResponseModel invokeGatewayBindDfft(String name,String dfftId,String type){
        FBindMemberReq req = new FBindMemberReq();
        req.setMemCode(dfftId);
        req.setMemName(name);
        req.setPayMemType(type);
        LOGGER.info("开始调用 bindMember ,参数:{}", JSON.toJSONString(req));
        BindPayVo vo = new BindPayVo();
        BindMemberResp resp = dfftPayFacade.bindMember(req);
        LOGGER.info("结束调用 bindMember ,参数:{},返回结果:{}",JSON.toJSONString(req),JSON.toJSONString(resp));
        if(resp!=null && PayResultEnums.REQUEST_SUCCESS.getCode().equals(resp.getCode())){
            vo.setOrder(resp.getOrder());
            vo.setUrl(resp.getUrl());
            return ResponseModel.succ(vo);
        }else{
            return ResponseModel.fail(resp.getCode(),resp.getDesc());
        }
    }


    /**
     * 绑定法大大
     */
    @PostMapping(value = "/bindFdd")
    public ResponseModel bindFdd(@RequestBody AccountRequest params) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_COMMON_TYPE)){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        AgencyResp agency = accountFacade.getAgencyById(loadCurLoginAgencyId());
        if (StringUtils.isNotEmpty(agency.getFadadaId())) {
            return ResponseModel.succ();
        }
        FOpenMemberResp resp = invokeGatewayBindCompanyFdd(agency.getName(),agency.getMobile(), agency.getLicense(), agency.getFadadaEmail());
        if (resp != null && ApiCallResult.SUCCESS.getCode().equals(resp.getCode())) {
            String faddId = resp.getCustomer_id();
            AgencyReq.UpdateDfftOrFadadaReq req = new AgencyReq.UpdateDfftOrFadadaReq();
            req.setId(agency.getId());
            req.setFadadaId(resp.getCustomer_id());
            accountFacade.updateAgency(req);
        } else {
            return ResponseModel.fail(resp.getCode(), resp.getDesc());
        }
        return ResponseModel.succ();
    }

    /**
     * 设置是否可以查看保留价接口
     */
    @PostMapping(value = "/set/can/check/reserve/price")
    public ResponseModel setCanCheckReservePrice(@RequestBody AgencyReq.BaseReq req) {
        Assert.notNull(req.getAccountId(), "accountId 参数不能为空");
        Assert.notNull(req.getCanCheckReservePrice(), "canCheckReservePrice 参数不能为空");
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        if (!accountInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.PERMISSION_DENIED);
        }
        req.setAgencyId(accountInfo.getAgencyId());
        return ResponseModel.succ(accountFacade.agencySetCanCheckReservePrice(req));
    }


    private FOpenMemberResp invokeGatewayBindCompanyFdd(String name,String mobile,String IdOrLicenceNo, String fadadaEmail){
        FOpenMemberReq req = new FOpenMemberReq();
        req.setCustomer_name(name);
        req.setMobile(mobile);
        req.setCustomer_type("2");
        req.setId_card(IdOrLicenceNo);
        req.setEmail(fadadaEmail);
        LOGGER.info("开始调用 bindFdd ,参数:{}",JSON.toJSONString(req));
        //FOpenMemberResp resp = null;
        FOpenMemberResp resp = fddSignatureFacade.openMember(req);
        LOGGER.info("结束调用 bindFdd ,参数:{},返回结果:{}",JSON.toJSONString(req),JSON.toJSONString(resp));
        return resp;
    }


    /**
     * 提現
     */
    @PostMapping(value = "/withdraw")
    public ResponseModel withdraw(@RequestBody WithdrawReq req) {

        if(req.getAmount() == null
                || req.getAmount().compareTo(BigDecimal.ZERO)<=0
                || req.getBankAccountId() == null
        ){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey());

        req.setType(AccountEnum.AcctType.AGENCY.getKey());
        req.setPartyId(loadCurLoginAgencyId());

        return ResponseModel.succ(acctFacade.withdraw(req));
    }


    /**
     * 提交發票
     */
    @PostMapping(value = "/submitInvoice")
    public ResponseModel submitInvoice(@RequestBody WithdrawReq req) {

        if(req.getAcctDetailId() == null || req.getInvoiceType() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setType(AccountEnum.AcctType.AGENCY.getKey());
        req.setPartyId(loadCurLoginAgencyId());

        boolean flag = acctFacade.submitInvoice(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 银行账户列表
     */
    @GetMapping(value = "/bank/account/list")
    public ResponseModel bankAccounts(TBankAccountReq.BaseReq req) {
        req.setPartyType(AccountEnum.AcctType.AGENCY.getKey());
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(accountFacade.getTBankAccounts(req));
    }


    /**
     * 添加银行账户
     */
    @PostMapping(value = "/bank/account/add")
    public ResponseModel addBankAccount(@Valid @RequestBody TBankAccountReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey());
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setPartyType(AccountEnum.AcctType.AGENCY.getKey());
        return accountFacade.addTBankAccount(req);
    }

    /**
     * 解绑银行账户
     */
    @PostMapping(value = "/bank/account/unbind")
    public ResponseModel unbindBankAccount(@RequestBody TBankAccountReq.BaseReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        if (StringUtils.isEmpty(req.getSmsCode())) {
            return ResponseModel.fail("验证码不能为空");
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_UNBIND_BANK_CARD_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_UNBIND_BANK_CARD_TYPE.getKey());
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setPartyType(AccountEnum.AcctType.AGENCY.getKey());
        return accountFacade.unbindTBankAccount(req);
    }

    /**
     * 平台资金账户明细列表接口
     */
    @GetMapping(value = "/acct/detail/list")
    public ResponseModel acctDetailList(AcctReq.QueryReq req) {
        AcctResp acctResp = accountFacade.getAcct(loadCurLoginAgencyId(), AccountEnum.AcctType.AGENCY.getKey());
        req.setAcctId(acctResp.getId());
        return ResponseModel.succ(accountFacade.getFrontAcctDetailListByPage(req));
    }

    /**
     * 平台资金账户明细详情接口
     */
    @GetMapping(value = "/acct/detail")
    public ResponseModel acctDetail(AcctReq.BaseReq req) {
        AcctResp acctResp = accountFacade.getAcct(loadCurLoginAgencyId(), AccountEnum.AcctType.AGENCY.getKey());
        req.setAcctId(acctResp.getId());
        return ResponseModel.succ(accountFacade.getFrontAcctDetail(req));
    }


    /**
     * 签合同
     */
    @PostMapping(value = "/signContract")
    public ResponseModel signContract(@RequestBody WithdrawReq req) {

        if(req.getAcctDetailId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        req.setPartyId(loadCurLoginAgencyId());
        req.setType(AccountEnum.AcctType.AGENCY.getKey());

        return ResponseModel.succ(acctFacade.signContract(req));
    }

}

