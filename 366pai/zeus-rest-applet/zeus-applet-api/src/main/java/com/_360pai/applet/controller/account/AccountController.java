package com._360pai.applet.controller.account;

import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.req.ApplyAccountAuthRequest;
import com._360pai.applet.controller.account.req.ApplyCompanyAuthRequest;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.applet.shiro.ShiroAuthService;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.applet.AppletFacade;
import com._360pai.core.facade.applet.req.AppletReq;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.gateway.facade.AliSmsFacade;
import com._360pai.gateway.facade.WxFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: LoginController
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/22 15:00
 */
@Slf4j
@RestController
@RequestMapping(value = "/confined/account", produces = "application/json;charset=UTF-8")
public class AccountController extends AbstractController {
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private AppletFacade appletFacade;
    @Resource
    private RedisCachemanager redisCachemanager;
    @Autowired
    private ShiroAuthService shiroAuthService;
    @Reference(version = "1.0.0")
    private AliSmsFacade aliSmsFacade;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;

    @Reference(version = "1.0.0")
    private AcctFacade acctFacade;

    @Autowired
    private SystemProperties systemProperties;

    /**
     * 个人认证
     */
    @PostMapping(value = "/applyUserAuth")
    public ResponseModel applyUserAuth(@RequestBody ApplyAccountAuthRequest params) {
        log.info("开始调用 applyUserAuth , 参数:{}", JSON.toJSONString(params));
        if (StringUtils.isEmpty(params.getName())
                || StringUtils.isEmpty(params.getCertificateNumber())
                || StringUtils.isEmpty(params.getCertificateFrontImg())
                || StringUtils.isEmpty(params.getCertificateBackImg())
        ) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getAccountId() == null) {
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
        ApplyUserAuthReq applyAuthReq    = new ApplyUserAuthReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        applyAuthReq.setApplySource(PartyEnum.ApplySource.APPLET.getKey());
        boolean flag  =  accountFacade.applyUserAuth(applyAuthReq);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }

    }


    /**
     * 企业认证
     */
    @PostMapping(value = "/applyCompanyAuth")
    public ResponseModel applyCompanyAuth(@RequestBody ApplyCompanyAuthRequest params) {
        log.info("开始调用 applyCompanyAuth , 参数:{}", JSON.toJSONString(params));
        if(StringUtils.isEmpty(params.getName())
                || StringUtils.isEmpty(params.getLicense())
                || StringUtils.isEmpty(params.getLicenseImg())
                || StringUtils.isEmpty(params.getAuthorizationImg())
        ){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getAccountId() == null) {
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
        ApplyCompanyAuthReq applyAuthReq    = new ApplyCompanyAuthReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        applyAuthReq.setApplySource(PartyEnum.ApplySource.APPLET.getKey());
        boolean flag  =  accountFacade.applyCompanyAuth(applyAuthReq);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 我的邀请二维码接口
     */
    @GetMapping(value = "/invite/qr/code")
    public ResponseModel inviteQrCode() {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        if (accountInfo.getShopId() == null) {
            return ResponseModel.fail();
        }
        Map<String, Object> data = new HashMap<>();
        ShopReq.BaseReq req = new ShopReq.BaseReq();
        req.setShopId(accountInfo.getShopId());
        req.setPartyId(accountInfo.getCurrentPartyId());
        req.setAccountId(accountInfo.getAccountId());
        ShopVo shop = shopFacade.getShop(req);
        if (shop != null) {
            data.put("imgUrl", shop.getInviteQrCode());
        }
        return ResponseModel.succ(data);
    }

    /**
     * 我的邀请记录列表接口
     */
    @GetMapping(value = "/invite/record/list")
    public ResponseModel inviteRecordList(AppletReq.InviteRecordReq req) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        if (accountInfo.getShopId() == null) {
            return ResponseModel.fail();
        }
        req.setShopId(accountInfo.getShopId());
        return ResponseModel.succ(appletFacade.getInviteRecordList(req));
    }

    /**
     * 银行账户列表
     */
    @GetMapping(value = "/bank/account/list")
    public ResponseModel bankAccounts(TBankAccountReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getCurrentPartyId() == null) {
            return ResponseModel.fail("请先进行账户认证");
        }
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        req.setPartyType(accountBaseInfo.getType());
        return ResponseModel.succ(accountFacade.getTBankAccounts(req));
    }


    /**
     * 添加银行账户
     */
    @PostMapping(value = "/bank/account/add")
    public ResponseModel addBankAccount(@Valid @RequestBody TBankAccountReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getCurrentPartyId() == null) {
            return ResponseModel.fail("请先进行账户认证");
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey());
        if (req.isBankOptional()) {
            req.setBankCode("");
        }
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        req.setPartyType(accountBaseInfo.getType());
        return accountFacade.addTBankAccount(req);
    }

    /**
     * 我的佣金记录列表接口
     */
    @GetMapping(value = "/commission/record/list")
    public ResponseModel commissionRecordList(AcctReq.QueryReq req) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        if (accountInfo.getCurrentPartyId() == null && accountInfo.getShopId() == null) {
            return ResponseModel.succ(new PageInfoResp<>());
        }
        if (accountInfo.getCurrentPartyId() != null) {
            req.setPartyId(accountInfo.getCurrentPartyId());
            req.setPartyType(accountInfo.getType());
        } else if (accountInfo.getShopId() != null) {
            req.setPartyId(accountInfo.getShopId());
            req.setPartyType(AccountEnum.AcctType.SHOP.getKey());
        }
        return ResponseModel.succ(accountFacade.getMyCommissionListByPage(req));
    }


    /**
     * 提現
     */
    @PostMapping(value = "/withdraw")
    public ResponseModel withdraw(@RequestBody WithdrawReq req) {

        if(req.getAmount() == null
                || req.getBankAccountId() == null
                || req.getAmount().compareTo(BigDecimal.ZERO)<=0){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();

        // 校验 验证码
        if (!checkSmsCode(accountInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey());


        req.setPartyId(accountInfo.getCurrentPartyId());
        req.setType(accountInfo.getType());

        return ResponseModel.succ(acctFacade.withdraw(req));
    }



    /**
     * 提現記錄
     */
    @PostMapping(value = "/getWithdrawRecord")
    public ResponseModel getWithdrawRecord(SearchBatchReq req) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountInfo.getCurrentPartyId());
        req.setType(accountInfo.getType());
        return ResponseModel.succ(acctFacade.getWithdrawRecord(req));
    }


    /**
     * 提交發票
     */
    @PostMapping(value = "/submitInvoice")
    public ResponseModel submitInvoice(@RequestBody WithdrawReq req) {

        if(req.getAcctDetailId() == null || req.getInvoiceType() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountInfo.getCurrentPartyId());
        req.setType(accountInfo.getType());

        boolean flag = acctFacade.submitInvoice(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 平台资金账户明细列表接口
     */
    @GetMapping(value = "/acct/detail/list")
    public ResponseModel acctDetailList(AcctReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getCurrentPartyId() == null && accountBaseInfo.getShopId() == null) {
            return ResponseModel.succ(new PageInfoResp<>());
        }
        AcctResp acctResp;
        if (accountBaseInfo.getCurrentPartyId() != null) {
            acctResp = accountFacade.getAcct(accountBaseInfo.getCurrentPartyId(), accountBaseInfo.getType());
        } else {
            acctResp = accountFacade.getAcct(accountBaseInfo.getShopId(), AccountEnum.AcctType.SHOP.getKey());
        }
        req.setAcctId(acctResp.getId());
        return ResponseModel.succ(accountFacade.getFrontAcctDetailListByPage(req));
    }

    /**
     * 平台资金账户明细详情接口
     */
    @GetMapping(value = "/acct/detail")
    public ResponseModel acctDetail(AcctReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getCurrentPartyId() == null && accountBaseInfo.getShopId() == null) {
            return ResponseModel.succ();
        }
        AcctResp acctResp;
        if (accountBaseInfo.getCurrentPartyId() != null) {
            acctResp = accountFacade.getAcct(accountBaseInfo.getCurrentPartyId(), accountBaseInfo.getType());
        } else {
            acctResp = accountFacade.getAcct(accountBaseInfo.getShopId(), AccountEnum.AcctType.SHOP.getKey());
        }
        req.setAcctId(acctResp.getId());
        return ResponseModel.succ(accountFacade.getFrontAcctDetail(req));
    }

}
