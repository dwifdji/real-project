package com._360pai.web.controller.account;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PasswordEncryption;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.AcctFacade;
import com._360pai.core.facade.account.req.*;
import com._360pai.core.facade.account.resp.AccountResp;
import com._360pai.core.facade.account.resp.AcctResp;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.resp.SpvResp;
import com._360pai.core.facade.account.vo.SpvVo;
import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.gateway.common.dfftpay.PayResultEnums;
import com._360pai.gateway.controller.req.dfftpay.FBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberReq;
import com._360pai.gateway.controller.req.dfftpay.FQueryBindMemberResp;
import com._360pai.gateway.controller.req.fdd.FOpenMemberReq;
import com._360pai.gateway.controller.req.fdd.FOpenMemberResp;
import com._360pai.gateway.facade.DfftPayFacade;
import com._360pai.gateway.facade.FddSignatureFacade;
import com._360pai.gateway.resp.BindMemberResp;
import com._360pai.gateway.resp.QueryBalanceResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.req.*;
import com._360pai.web.controller.account.resp.*;
import com._360pai.web.shiro.ShiroAuthenService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by RuQ on 2018/8/19 18:09
 */
@RestController
@RequestMapping(value = "/confined/account", produces = "application/json;charset=UTF-8")
public class AccountController  extends AbstractController {


    public static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    @Resource
    private ShiroAuthenService shiroAuthenService;

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private DfftPayFacade dfftPayFacade;

    @Reference(version = "1.0.0")
    private FddSignatureFacade fddSignatureFacade;

    @Reference(version = "1.0.0")
    private DisposeLevelFacade disposeLevelFacade;

    @Reference(version = "1.0.0")
    private AcctFacade acctFacade;

    /**
     * 提現
     */
    @PostMapping(value = "/withdraw")
    public ResponseModel withdraw(@RequestBody WithdrawReq req) {

        if(req.getAmount() == null
                || req.getAmount().compareTo(BigDecimal.ZERO)<=0
                || req.getBankAccountId() == null
                || StringUtils.isEmpty(req.getSmsCode())
                ){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (PartyEnum.Type.company.name().equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_WITHDRAW_DEPOSIT_TYPE.getKey());

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setType(accountBaseInfo.getType());

        return ResponseModel.succ(acctFacade.withdraw(req));
    }


    /**
     * 签合同
     */
    @PostMapping(value = "/signContract")
    public ResponseModel signContract(@RequestBody WithdrawReq req) {

        if(req.getAcctDetailId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountInfo.getPartyPrimaryId());
        req.setType(accountInfo.getType());

        return ResponseModel.succ(acctFacade.signContract(req));
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
        req.setPartyId(accountInfo.getPartyPrimaryId());
        req.setType(accountInfo.getType());

        boolean flag = acctFacade.submitInvoice(req);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    @PostMapping(value = "/checkCanUploadActivity")
    public ResponseModel checkCanUploadActivity(@RequestBody AccountRequest params){
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(!accountBaseInfo.isAccountAuth()){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_CAN_NOT_UPLOAD_ERROR);
        }
        if(StringUtils.isEmpty(accountBaseInfo.getFadadaId())){
            return ResponseModel.fail(ApiCallResult.NO_FDD_CAN_NOT_UPLOAD_ERROR);
        }

        if(!accountBaseInfo.getOperOffline()){
            //线上
            if(!accountBaseInfo.isBank() && !accountBaseInfo.isIs_pay_bind()){
                return ResponseModel.fail(ApiCallResult.NO_DFFT_CAN_NOT_UPLOAD_ERROR);
            }

            if(accountBaseInfo.isBank() && StringUtils.isEmpty(accountBaseInfo.getBankAccountNo())){
                return ResponseModel.fail(ApiCallResult.NO_BANK_ACCOUNT_ERROR);
            }
        }
        return ResponseModel.succ();
    }

    @PostMapping(value = "/getAccountInfo")
    public ResponseModel getAccountInfo(@RequestBody AccountRequest params) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        AccountInfoVo accountInfoVo = new AccountInfoVo();
        accountInfoVo.setDisposerStatus(accountBaseInfo.getDisposerStatus());
        accountInfoVo.setAgencyStatus(accountBaseInfo.getAgencyStatus());
        accountInfoVo.setFundStatus(accountBaseInfo.getFundStatus());
        accountInfoVo.setAdmin(accountBaseInfo.isAdmin());
        accountInfoVo.setAccountAuth(accountBaseInfo.isAccountAuth());
        accountInfoVo.setAccountAuthName(accountBaseInfo.getName());
        accountBaseInfo.setAccountAuthTime(accountBaseInfo.getAccountAuthTime());
        accountInfoVo.setPayBind(accountBaseInfo.isIs_pay_bind());
        accountInfoVo.setFddBind(!StringUtils.isEmpty(accountBaseInfo.getFadadaId()));
        accountInfoVo.setAccountList(accountBaseInfo.getAccountList());
        accountInfoVo.setType(accountBaseInfo.getType());
        accountInfoVo.setDefaultAgencyId(accountBaseInfo.getDefaultAgencyId());
        accountInfoVo.setDefaultAgencyName(accountBaseInfo.getDefaultAgencyName());
        accountInfoVo.setMobile(accountBaseInfo.getMobile());
        accountInfoVo.setIdOrLicenceNo(accountBaseInfo.getIdOrLicenceNo());
        accountInfoVo.setFunder(accountBaseInfo.isFunder());
        accountInfoVo.setDisposer(accountBaseInfo.isDisposer());
        accountInfoVo.setAgencyId(accountBaseInfo.getAgencyId());
        accountInfoVo.setIsBank(accountBaseInfo.isBank());
        accountInfoVo.setOperOffline(accountBaseInfo.getOperOffline());
        accountInfoVo.setOperWithoutFadada(accountBaseInfo.getOperWithoutFadada());
        accountInfoVo.setBankName(accountBaseInfo.getBankName());
        accountInfoVo.setBankAccountName(accountBaseInfo.getBankAccountName());
        accountInfoVo.setBankAccountNo(accountBaseInfo.getBankAccountNo());
        accountInfoVo.setIsUserAuth(accountBaseInfo.getIsUserAuth());
        accountInfoVo.setIsFirstLevelProvider(isFirstLevelPartner(accountBaseInfo.getPartyPrimaryId()));
        accountInfoVo.setShopId(accountBaseInfo.getShopId());

        accountInfoVo.setLeaseAuditFlag(accountBaseInfo.isLeaseAuditFlag());
        accountInfoVo.setLeaseComAddress(accountBaseInfo.getLeaseComAddress());
        accountInfoVo.setLeaseComId(accountBaseInfo.getLeaseComId());
        accountInfoVo.setLeaseComName(accountBaseInfo.getLeaseComName());
        accountInfoVo.setLeaseReleaseFlag(accountBaseInfo.isLeaseReleaseFlag());
        accountInfoVo.setLeaseStaffId(accountBaseInfo.getLeaseStaffId());
        if(accountBaseInfo.isIs_pay_bind()){
            //查询账户金额
            queryDfftAmount(accountBaseInfo.getDfftId(),accountBaseInfo.getName(),accountInfoVo);
        }else if(accountBaseInfo.isAccountAuth()){
            FQueryBindMemberReq fQueryBindMemberReq = new FQueryBindMemberReq();
            fQueryBindMemberReq.setMemCode(accountBaseInfo.getDfftId());
            fQueryBindMemberReq.setMemName(accountBaseInfo.getName());
            FQueryBindMemberResp fQueryBindMemberResp = dfftPayFacade.queryBindMember(fQueryBindMemberReq);
            if(fQueryBindMemberResp != null && fQueryBindMemberResp.getCode().equals(PayResultEnums.MEM_BOUND.getCode())){
                // 更新 payBind
                if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_USER_TYPE)){
                    UserReq userReq = new UserReq();
                    userReq.setId(accountBaseInfo.getPartyPrimaryId());
                    userReq.setPayBind(1);
                    accountFacade.updateUserById(userReq);
                    accountInfoVo.setPayBind(true);
                    accountFacade.openEasternPayNotify(accountBaseInfo.getMobile());
                }else if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_COMPANY_TYPE)){
                    CompanyReq companyReq = new CompanyReq();
                    companyReq.setId(accountBaseInfo.getPartyPrimaryId());
                    companyReq.setPayBind(1);
                    accountFacade.updateCompanyById(companyReq);
                    accountInfoVo.setPayBind(true);
                    accountFacade.openEasternPayNotify(accountBaseInfo.getMobile());
                }

                //查询账户金额
                queryDfftAmount(accountBaseInfo.getDfftId(),accountBaseInfo.getName(),accountInfoVo);
            }
        }

        AcctResp acctResp = new AcctResp();
        if(accountBaseInfo.getPartyPrimaryId() != null){
            acctResp = acctFacade.getAcctByPartyIdAndType(accountBaseInfo.getPartyPrimaryId(),accountBaseInfo.getType());
        }else{
            if(accountBaseInfo.getShopId() != null){
                acctResp = acctFacade.getAcctByPartyIdAndType(accountBaseInfo.getShopId(), AccountEnum.AcctType.SHOP.getKey());
            }
        }
        if(acctResp != null){
            accountInfoVo.setAccountAmount360(acctResp.getTotalAmt().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
            accountInfoVo.setLockAmount360(acctResp.getLockAmt().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
            accountInfoVo.setAvailableAmount360(acctResp.getAvailAmt().setScale(2,BigDecimal.ROUND_HALF_UP)+"");
        }
        return ResponseModel.succ(accountInfoVo);
    }

    private boolean isFirstLevelPartner(Integer partyId) {
        return disposeLevelFacade.isFirstLevelPartner(partyId);
    }


    private void queryDfftAmount(String dfftId,String name,AccountInfoVo accountInfoVo){
        FQueryBindMemberReq fQueryBindMemberReq = new FQueryBindMemberReq();
        fQueryBindMemberReq.setMemCode(dfftId);
        fQueryBindMemberReq.setMemName(name);
        LOGGER.info("开始调用 queryBalance ,参数:{}",JSON.toJSONString(fQueryBindMemberReq));

        try{
            QueryBalanceResp queryBalanceResp = dfftPayFacade.queryBalance(fQueryBindMemberReq);
            LOGGER.info("结束调用 queryBalance ,参数:{},返回结果:{}",JSON.toJSONString(fQueryBindMemberReq),JSON.toJSONString(queryBalanceResp));

            if(queryBalanceResp != null && queryBalanceResp.getCode().equals(PayResultEnums.PAY_SUCCESS.getCode())){
                accountInfoVo.setAccountAmount(queryBalanceResp.getTotalAmt());
                accountInfoVo.setLockAmount(queryBalanceResp.getLockedAmt());
                accountInfoVo.setAvailableAmount(queryBalanceResp.getFreeAmt());
            }
        }catch (Exception e){
            LOGGER.error("调用 queryBalance 异常,参数:{},异常信息:{}",JSON.toJSONString(fQueryBindMemberReq),e.getMessage());
            accountInfoVo.setAccountAmount("0.00");
            accountInfoVo.setLockAmount("0.00");
            accountInfoVo.setAvailableAmount("0.00");

        }
    }


    /**
     * 绑定东方付通
     */
    @PostMapping(value = "/bindPay")
    public ResponseModel bindPay(@RequestBody AccountRequest params) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_COMMON_TYPE)){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }

        if(params.getSpvId() == null || params.getSpvId() <=0){
            return invokeGatewayBindDfft(accountBaseInfo.getName(),accountBaseInfo.getDfftId(),SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())?"1":"2");
        }else{
            if(!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())){
                return ResponseModel.fail(ApiCallResult.ONLY_COMPANY_CAN_APPLY_SPV);
            }

            SpvResp spvResp = accountFacade.getSpvById(params.getSpvId());
            if(spvResp == null || !String.valueOf(spvResp.getCompanyId()).equals(String.valueOf(accountBaseInfo.getPartyPrimaryId()))
                    || spvResp.getPayBind()==1){
                return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
            }

            return invokeGatewayBindDfft(spvResp.getName(),spvResp.getDfftId(),"1");
        }
    }


    private  ResponseModel invokeGatewayBindDfft(String name,String dfftId,String type){
        FBindMemberReq req = new FBindMemberReq();
        req.setMemCode(dfftId);
        req.setMemName(name);
        req.setPayMemType(type);
        LOGGER.info("开始调用 bindMember ,参数:{}",JSON.toJSONString(req));
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

    private FOpenMemberResp invokeGatewayBindPersonFdd(String name, String mobile, String IdOrLicenceNo) {
        FOpenMemberReq req = new FOpenMemberReq();
        req.setCustomer_name(name);
        req.setMobile(mobile);
        req.setCustomer_type("1");
        req.setId_card(IdOrLicenceNo);
        LOGGER.info("开始调用 bindFdd ,参数:{}", JSON.toJSONString(req));
        //FOpenMemberResp resp = null;
        FOpenMemberResp resp = fddSignatureFacade.openMember(req);
        LOGGER.info("结束调用 bindFdd ,参数:{},返回结果:{}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
    }

    private FOpenMemberResp invokeGatewayBindCompanyFdd(String name, String mobile, String IdOrLicenceNo, String fadadaEmail) {
        FOpenMemberReq req = new FOpenMemberReq();
        req.setCustomer_name(name);
        req.setMobile(mobile);
        req.setCustomer_type("2");
        req.setId_card(IdOrLicenceNo);
        req.setEmail(fadadaEmail);
        LOGGER.info("开始调用 bindFdd ,参数:{}", JSON.toJSONString(req));
        //FOpenMemberResp resp = null;
        FOpenMemberResp resp = fddSignatureFacade.openMember(req);
        LOGGER.info("结束调用 bindFdd ,参数:{},返回结果:{}", JSON.toJSONString(req), JSON.toJSONString(resp));
        return resp;
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

        if(params.getSpvId() == null || params.getSpvId() <=0){
            FOpenMemberResp resp;
            if (SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())) {
                resp = invokeGatewayBindCompanyFdd(accountBaseInfo.getName(), accountBaseInfo.getMobile(), accountBaseInfo.getIdOrLicenceNo(), accountBaseInfo.getFadadaEmail());
            } else {
                resp = invokeGatewayBindPersonFdd(accountBaseInfo.getName(), accountBaseInfo.getMobile(), accountBaseInfo.getIdOrLicenceNo());
            }
            if(resp != null && ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
                String faddId = resp.getCustomer_id();
                if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_USER_TYPE)){
                    UserReq userReq = new UserReq();
                    userReq.setId(accountBaseInfo.getPartyPrimaryId());
                    userReq.setFadadaId(faddId);
                    accountFacade.updateUserById(userReq);
                    accountFacade.openElectronicSignatureNotify(accountBaseInfo.getMobile());
                }else if(accountBaseInfo.getType().equals(SystemConstant.ACCOUNT_COMPANY_TYPE)){
                    CompanyReq companyReq =new CompanyReq();
                    companyReq.setId(accountBaseInfo.getPartyPrimaryId());
                    companyReq.setFadadaId(faddId);
                    accountFacade.updateCompanyById(companyReq);
                    accountFacade.openElectronicSignatureNotify(accountBaseInfo.getMobile());
                }

        }else{
                return ResponseModel.fail(resp.getCode(),resp.getDesc());
            }
        }else{
            if(!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())){
                return ResponseModel.fail(ApiCallResult.ONLY_COMPANY_CAN_APPLY_SPV);
            }

            SpvResp spvResp = accountFacade.getSpvById(params.getSpvId());
            if(spvResp == null || !String.valueOf(spvResp.getCompanyId()).equals(String.valueOf(accountBaseInfo.getPartyPrimaryId()))
                    || !StringUtils.isEmpty(spvResp.getFddId())){
                return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
            }
            FOpenMemberResp resp = invokeGatewayBindCompanyFdd(spvResp.getName(),spvResp.getMobile(),spvResp.getLicense(), RandomNumberGenerator.wordGenerator(10).toLowerCase() + "@360pai.com");
            if(resp != null && ApiCallResult.SUCCESS.getCode().equals(resp.getCode())){
                SpvReq req = new SpvReq();
                req.setId(params.getSpvId());
                req.setFddId(resp.getCustomer_id());
                accountFacade.updateSpv(req);
            }else {
                return ResponseModel.fail(resp.getCode(),resp.getDesc());
            }

        }
        return ResponseModel.succ();
    }



    /**
     * 退出
     */
    @PostMapping(value = "/logout")
    public ResponseModel logout(@RequestBody AccountRequest params) {
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        shiroAuthenService.removeTicket(accountInfo.getPartyPrimaryId(),accountInfo.getType());
        return ResponseModel.succ();
    }

    /**
     * 修改密码
     */

    @PostMapping(value = "/modifyPassword")
    public ResponseModel modifyPassword(@RequestBody AccountRequest params) {
        String phone = loadCurLoginAccountInfo().getMobile();
        String password = params.getPassword();
        String smsCode = params.getSmsCode();
        if (StringUtils.isEmpty(phone)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(smsCode)) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountResp accountResp = accountFacade.findAccountByMobile(phone);
        if (accountResp != null && accountResp.getId() != null) {
            if (checkSmsCode(phone, SmsType.SMS_MODIFY_PWD_TYPE.getKey(), smsCode)) {
                removeSmsCode(phone, SmsType.SMS_MODIFY_PWD_TYPE.getKey());
                // 更新密码
                AccountReq accountReq = new AccountReq();
                accountReq.setId(accountResp.getId());
                try {
                    accountReq.setPassword(PasswordEncryption.getEncryptedPassword(password));
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                }
                boolean updateFlag = accountFacade.updateAccountById(accountReq);
                if (updateFlag) {
                    return ResponseModel.succ();
                } else {
                    return ResponseModel.fail();
                }
            }else {
                return ResponseModel.fail(ApiCallResult.VERIFICATION);
            }
        } else {
            return ResponseModel.fail(ApiCallResult.NOREGISTER);
        }
    }


    /**
     * 个人认证
     */
    @PostMapping(value = "/applyUserAuth")
    public ResponseModel applyUserAuth(@RequestBody ApplyAccountAuthRequest params) {
        LOGGER.info("开始调用 applyUserAuth , 参数:{}", JSON.toJSONString(params));
        if (StringUtils.isEmpty(params.getName())
                || StringUtils.isEmpty(params.getCertificateNumber())
                || StringUtils.isEmpty(params.getAddress())
        ) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();
        ApplyUserAuthReq applyAuthReq    = new ApplyUserAuthReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        applyAuthReq.setApplySource(PartyEnum.ApplySource.PLATFORM.getKey());
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
        LOGGER.info("开始调用 applyCompanyAuth , 参数:{}", JSON.toJSONString(params));
        if(StringUtils.isEmpty(params.getName())
                || StringUtils.isEmpty(params.getLegalPerson())
                || StringUtils.isEmpty(params.getIdCard())
                || StringUtils.isEmpty(params.getAddress())
        ){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo     accountBaseInfo = loadCurLoginAccountInfo();
        ApplyCompanyAuthReq applyAuthReq    = new ApplyCompanyAuthReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        applyAuthReq.setApplySource(PartyEnum.ApplySource.PLATFORM.getKey());
        boolean flag  =  accountFacade.applyCompanyAuth(applyAuthReq);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }



    /**
     * 申请资金服务商
     */
    @PostMapping(value = "/applyFundAuth")
    public ResponseModel applyFundAuth(@RequestBody ApplyFundAuthRequest params) {
        LOGGER.info("开始调用 applyFundAuth , 参数:{}", JSON.toJSONString(params));
        if(StringUtils.isEmpty(params.getCompanyName())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(!accountBaseInfo.isAccountAuth()){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        FundProviderApplyReq applyAuthReq = new FundProviderApplyReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        boolean flag  =  accountFacade.applyFundAuth(applyAuthReq);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }


    /**
     * 申请处置服务商
     */
    @PostMapping(value = "/applyDisposeAuth")
    public ResponseModel applyDisposeAuth(@RequestBody ApplyFundAuthRequest params) {
        LOGGER.info("开始调用 applyDisposeAuth , 参数:{}", JSON.toJSONString(params));
        if(StringUtils.isEmpty(params.getCompanyName())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(!accountBaseInfo.isAccountAuth()){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        DisposeProviderApplyReq applyAuthReq = new DisposeProviderApplyReq();
        BeanUtils.copyProperties(params,applyAuthReq);
        applyAuthReq.setAccountId(accountBaseInfo.getAccountId());
        applyAuthReq.setMobile(accountBaseInfo.getMobile());
        boolean flag  =  accountFacade.applyDisposeAuth(applyAuthReq);
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }






    /**
     * 切换角色
     */
    @PostMapping(value = "/changeRole")
    public ResponseModel changeRole(@RequestBody AccountRequest params, HttpServletResponse response){
         LOGGER.info("开始调用 changeRole ,参数:{}",JSON.toJSONString(params));
         if(StringUtils.isEmpty(params.getPartyId())){
             return ResponseModel.fail(ApiCallResult.EMPTY);
         }
         AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
         List<AccountSimpleVo> accountList = accountBaseInfo.getAccountList();
         if(accountList == null || accountList.isEmpty() || accountList.size() == 1){
             return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
         }
         boolean flag = false;
         for(AccountSimpleVo vo : accountList){
             if(String.valueOf(vo.getPartyId()).equals(String.valueOf(params.getPartyId()))){
                   flag = true;
             }
         }
         if(!flag){
             return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
         }
        //List<AccountSimpleVo> list = accountBaseInfo.getAccountList();
        AccountReq req = new AccountReq();
         req.setId(accountBaseInfo.getAccountId());
         req.setCurrentPartyId(Integer.parseInt(params.getPartyId()));
        return ResponseModel.succ(accountFacade.updateAccountById(req));
    }

    /**
     * 搜索机构
     */
    @PostMapping(value = "/searchAgency")
    public ResponseModel serachAgency(@RequestBody SearchAgencyReq req){
        LOGGER.info("开始调用 serachAgency ，参数:{}",JSON.toJSONString(req));
        List<SearchAgencyResp> searchAgencyRespList = new ArrayList<SearchAgencyResp>();
        List<AgencyResp> respList = accountFacade.searchAgency(req.getCityId(),req.getQ());
        for(AgencyResp resp : respList){
            SearchAgencyResp searchAgencyResp = new SearchAgencyResp();
            searchAgencyResp.setAgencyId(resp.getId()+"");
            searchAgencyResp.setAgencyName(resp.getName());
            searchAgencyResp.setLogoUrl(resp.getLogoUrl());
            searchAgencyRespList.add(searchAgencyResp);
        }
        return ResponseModel.succ(searchAgencyRespList);
    }

    /**
     * 更改默认机构
     */
    @PostMapping(value = "/modifyAgency")
    public ResponseModel modifyAgency(@RequestBody SearchAgencyReq req){
        LOGGER.info("开始调用 modifyAgency ，参数:{}",JSON.toJSONString(req));
        if(req.getAgencyId() == null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_USER_TYPE.equals(baseInfo.getType())){
            UserReq userReq = new UserReq();
            userReq.setId(baseInfo.getPartyPrimaryId());
            userReq.setDefaultAgencyId(req.getAgencyId());
            accountFacade.updateUserById(userReq);
        }else if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(baseInfo.getType())){
            CompanyReq companyReq = new CompanyReq();
            companyReq.setId(baseInfo.getPartyPrimaryId());
            companyReq.setDefaultAgencyId(req.getAgencyId());
            accountFacade.updateCompanyById(companyReq);
        }
        return ResponseModel.succ(true);
    }


    /**
     * 获取账户认证信息
     */
    @PostMapping(value = "/getAuthInfo")
    public ResponseModel getAuthInfo(@RequestBody SearchAgencyReq req){
        LOGGER.info("开始调用 getAuthInfo ，参数:{}",JSON.toJSONString(req));
        AccountBaseInfo baseInfo = loadCurLoginAccountInfo();
        if(SystemConstant.ACCOUNT_USER_TYPE.equals(baseInfo.getType())){
            return ResponseModel.succ(accountFacade.getUserById(baseInfo.getPartyPrimaryId()));
        }else if(SystemConstant.ACCOUNT_COMPANY_TYPE.equals(baseInfo.getType())){
            return ResponseModel.succ(accountFacade.getCompanyByCompanyId(baseInfo.getPartyPrimaryId()));
        }
        return ResponseModel.fail(ApiCallResult.UNSUPPORTED);
    }

    /**
     * 申请处置服务商
     */
    @PostMapping(value = "/dispose/provider/apply")
    public ResponseModel disposeProviderApply(@RequestBody DisposeProviderApplyReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail("账户尚未认证过，无法申请");
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.disposeProviderApply(req));
    }

    /**
     * 申請spv
     */
    @PostMapping(value = "/applySpv")
    public ResponseModel applySpv(@RequestBody SpvReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())){
            return ResponseModel.fail(ApiCallResult.ONLY_COMPANY_CAN_APPLY_SPV);
        }
        if(StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getName())
            || StringUtils.isEmpty(req.getLicense()) || StringUtils.isEmpty(req.getSmsCode())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        if(!checkSmsCode(req.getMobile(),SmsType.SMS_SPV_APPLY_TYPE.getKey(),req.getSmsCode())){
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        removeSmsCode(req.getMobile(),SmsType.SMS_SPV_APPLY_TYPE.getKey());
        boolean applyFlag = accountFacade.saveSpvApply(accountBaseInfo.getPartyPrimaryId(),req.getMobile(),req.getLicense(),req.getName());
        if(applyFlag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 申请资金供应商
     */
    @PostMapping(value = "/fund/provider/apply")
    public ResponseModel fundProviderApply(@RequestBody FundProviderApplyReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail("账户尚未认证过，无法申请");
        }
        req.setFundType(accountBaseInfo.getType());
        return ResponseModel.succ(accountFacade.fundProviderApply(req));
    }

    /**
     * 处置服务商申请详情
     */
    @GetMapping(value = "/dispose/provider/apply/detail")
    public ResponseModel disposeProviderApplyDetail(DisposeProviderApplyReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.getDisposeProviderApply(req));
    }

    /**
     * 资金供应商申请详情
     */
    @GetMapping(value = "/fund/provider/apply/detail")
    public ResponseModel fundProviderApplyDetail(FundProviderApplyReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.getFundProviderApply(req));
    }

    /**
     * 企业银行账户列表
     */
    @GetMapping(value = "/bank/accounts")
    public ResponseModel bankAccounts(BankAccountReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.getBankAccounts(req));
    }


    /**
     * 企业添加银行账户
     */
    @PostMapping(value = "/add/bank/account")
    public ResponseModel addBankAccount(@Valid @RequestBody BankAccountReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.addBankAccount(req));
    }

    /**
     * 企业修改银行账户
     */
    @PostMapping(value = "/update/bank/account")
    public ResponseModel updateBankAccount(@Valid @RequestBody BankAccountReq.UpdateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.updateBankAccount(req));
    }

    /**
     * 企业删除银行账户
     */
    @PostMapping(value = "/delete/bank/account")
    public ResponseModel deleteBankAccount(@RequestBody BankAccountReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
        }
        req.setPartyId(accountBaseInfo.getAccountId());
        return ResponseModel.succ(accountFacade.deleteBankAccount(req));
    }

    /**
     * 银行账户列表
     */
    @GetMapping(value = "/bank/account/list")
    public ResponseModel bankAccounts(TBankAccountReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setPartyType(accountBaseInfo.getType());
        return ResponseModel.succ(accountFacade.getTBankAccounts(req));
    }

    /**
     * 添加银行账户
     */
    @PostMapping(value = "/bank/account/add")
    public ResponseModel addBankAccount(@Valid @RequestBody TBankAccountReq.CreateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (PartyEnum.Type.company.name().equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_BIND_BANK_CARD_TYPE.getKey());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setPartyType(accountBaseInfo.getType());
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
        if (PartyEnum.Type.company.name().equals(accountBaseInfo.getType()) && !accountBaseInfo.isAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_COMPANY_ADMIN_ERRPR);
        }
        // 校验 验证码
        if (!checkSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_UNBIND_BANK_CARD_TYPE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_UNBIND_BANK_CARD_TYPE.getKey());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setPartyType(accountBaseInfo.getType());
        return accountFacade.unbindTBankAccount(req);
    }

    /**
     * 企业成员列表
     */
    @GetMapping(value = "/company/member/list")
    public ResponseModel companyMemberList(CompanyReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth() || !SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())) {
            return ResponseModel.fail("账号未认证或者非企业用户");
        }
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.getCompanyMemberList(req));
    }


    /** 获取spv详情**/
    @PostMapping(value = "/getSpvDetailXX")
    public ResponseModel getSpvDetail(@RequestBody AccountRequest params){
        if(params.getSpvId() == null || params.getSpvId() <= 0){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        SpvResp resp = accountFacade.getSpvById(params.getSpvId());
        if(!StringUtils.isEmpty(resp.getFddId())){
            resp.setFddId("1");
        }
        resp.setDfftId("");
        return ResponseModel.succ(resp);
    }


    /**
     * 添加企业成员
     */
    @PostMapping(value = "/company/member/add")
    public ResponseModel addCompanyMember(@Valid @RequestBody CompanyReq.AddMemberReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.addCompanyMember(req));
    }

    /**
     * 删除企业成员
     */
    @PostMapping(value = "/company/member/delete")
    public ResponseModel deleteCompanyMember(@Valid @RequestBody CompanyReq.DeleteMemberReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.deleteCompanyMember(req));
    }

    /** 获取spv列表**/
    @PostMapping(value = "/getSpvListXXX")
    public ResponseModel getSpvList(@RequestBody AccountRequest params) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())){
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        PageInfoResp<SpvVo> spvVoPageInfoResp = accountFacade.getSpvListByPage(accountBaseInfo.getPartyPrimaryId(),params.getPage(),params.getPerPage(),"platform");
        if(spvVoPageInfoResp != null && spvVoPageInfoResp.getList() != null && !spvVoPageInfoResp.getList().isEmpty()){
            for(SpvVo vo : spvVoPageInfoResp.getList()){
                if(vo.getId()!=null && vo.getId()>0 && !vo.isPayBind()){
                    FQueryBindMemberReq fQueryBindMemberReq = new FQueryBindMemberReq();
                    fQueryBindMemberReq.setMemCode(accountFacade.getSpvById(vo.getId()).getDfftId());
                    fQueryBindMemberReq.setMemName(vo.getName());
                    FQueryBindMemberResp fQueryBindMemberResp = dfftPayFacade.queryBindMember(fQueryBindMemberReq);
                    if(fQueryBindMemberResp != null && fQueryBindMemberResp.getCode().equals(PayResultEnums.MEM_BOUND.getCode())){
                        SpvReq req = new SpvReq();
                        req.setId(vo.getId());
                        req.setPayBind(1);
                        accountFacade.updateSpv(req);
                        accountFacade.openEasternPayNotify(vo.getMobile());
                    }
                }
            }
        }
        return ResponseModel.succ(spvVoPageInfoResp);

    }


    /** 获取spv列表**/
    @PostMapping(value = "/getCompanySpvXXX")
    public ResponseModel getCompanySpv(@RequestBody AccountRequest params) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
//        if(!SystemConstant.ACCOUNT_COMPANY_TYPE.equals(accountBaseInfo.getType())){
//            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
//        }
        return ResponseModel.succ(accountFacade.getSpvListByCompanyId(accountBaseInfo.getPartyPrimaryId()));
    }

    @PostMapping(value = "/checkCanApllyAuth")
    public ResponseModel checkCanApllyAuth(@RequestBody AccountRequest params) {
        if(StringUtils.isEmpty(params.getType())){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        boolean flag = accountFacade.checkCanApllyAuth(accountBaseInfo.getAccountId(),params.getType());
        if(flag){
            return ResponseModel.succ();
        }else{
            return ResponseModel.fail();
        }
    }

    /**
     * 认证申请记录列表接口
     */
    @GetMapping(value = "/apply/record/list")
    public ResponseModel applyRecordList(AccountReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        return accountFacade.getApplyRecordList(req);
    }

    /**
     * 平台资金账户明细列表接口
     */
    @GetMapping(value = "/acct/detail/list")
    public ResponseModel acctDetailList(AcctReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getPartyPrimaryId() == null && accountBaseInfo.getShopId() == null) {
            return ResponseModel.succ(new PageInfoResp<>());
        }
        AcctResp acctResp;
        if (accountBaseInfo.getPartyPrimaryId() != null) {
            acctResp = accountFacade.getAcct(accountBaseInfo.getPartyPrimaryId(), accountBaseInfo.getType());
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
        if (accountBaseInfo.getPartyPrimaryId() == null && accountBaseInfo.getShopId() == null) {
            return ResponseModel.succ();
        }
        AcctResp acctResp;
        if (accountBaseInfo.getPartyPrimaryId() != null) {
            acctResp = accountFacade.getAcct(accountBaseInfo.getPartyPrimaryId(), accountBaseInfo.getType());
        } else {
            acctResp = accountFacade.getAcct(accountBaseInfo.getShopId(), AccountEnum.AcctType.SHOP.getKey());
        }
        req.setAcctId(acctResp.getId());
        return ResponseModel.succ(accountFacade.getFrontAcctDetail(req));
    }

   /**
     * 未完成任务列表接口
     */
    @GetMapping(value = "/unfinished/task/list")
    public ResponseModel unfinishedTaskList(HttpServletRequest request) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        AccountReq.BaseReq req = new AccountReq.BaseReq();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(accountFacade.getUnfinishedTaskList(req));
    }

  	/**
     * 新增我的足迹查询
     * @param viewRecordRequest
     * @return
     */
    @GetMapping("/getViewRecords")
    public ResponseModel getViewRecords(AcctReq.ViewRecordRequest viewRecordRequest) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo.getAccountId() == null){
            return ResponseModel.fail(ApiCallResult.NOREGISTER.getDesc());
        }

        if(StringUtils.isBlank(viewRecordRequest.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        viewRecordRequest.setAccountId(accountBaseInfo.getAccountId());
        viewRecordRequest.setPartyId(accountBaseInfo.getPartyPrimaryId() == null ?
                null: accountBaseInfo.getPartyPrimaryId());

        return accountFacade.getViewRecords(viewRecordRequest);
    }
}
