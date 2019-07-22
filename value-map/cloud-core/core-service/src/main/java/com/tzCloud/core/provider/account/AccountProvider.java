package com.tzCloud.core.provider.account;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.utils.PasswordEncryption;
import com.tzCloud.arch.core.sysconfig.properties.SystemProperties;
import com.tzCloud.core.common.constants.SmsType;
import com.tzCloud.core.exception.BusinessException;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.core.facade.account.resp.PlatformAccountResp;
import com.tzCloud.core.facade.account.vo.AccountInfo;
import com.tzCloud.core.facade.account.vo.MembershipCardVO;
import com.tzCloud.core.model.account.TAccount;
import com.tzCloud.core.model.account.TAccountMembershipCard;
import com.tzCloud.core.service.AccountService;
import com.tzCloud.core.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xdrodger
 * @Title: AccountProvider
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:42
 */
@Slf4j
@Service(version = "1.0.0")
@Component
public class AccountProvider implements AccountFacade {
    @Autowired
    private AccountService accountService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private SystemProperties systemProperties;

    @Override
    public PlatformAccountResp.AccountResp loginOrRegister(PlatformAccountReq.LoginReq req) {
        PlatformAccountResp.AccountResp resp = new PlatformAccountResp.AccountResp();
        TAccount account = accountService.findBy(req.getMobile());
        if (StringUtils.isNotEmpty(req.getPassword())) { // 需存量用户
            if (account == null) {
                throw new BusinessException(ApiCallResult.NOREGISTER);
            }
            try {
                if (!PasswordEncryption.authenticate(req.getPassword(), account.getPassword())) {
                    throw new BusinessException(ApiCallResult.ERROR_PASSWORD);
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
            }
        } else if (StringUtils.isNotEmpty(req.getSmsCode())) {
            //短信验证码登录
            if (!smsService.checkSmsCode(req.getMobile(), SmsType.PLATFORM_LOGIN.getKey(), req.getSmsCode())) {
                throw new BusinessException(ApiCallResult.VERIFICATION);
            }
            if (account == null) {
                // 快速注册
                PlatformAccountReq.RegisterReq registerReq = new PlatformAccountReq.RegisterReq();
                registerReq.setMobile(req.getMobile());
                registerReq.setPassword(systemProperties.getDefaultRegisterPassword());
                account = accountService.register(registerReq);
                smsService.sendDefaultPasswordSms(req.getMobile(), systemProperties.getDefaultRegisterPassword());
                resp.setResetPassword(true);
            }
        } else {
            throw new BusinessException(ApiCallResult.EMPTY);
        }

        resp.setLoginId(account.getId());
        return resp;
    }

    @Override
    public AccountInfo getAccountInfo(Integer accountId) {
        AccountInfo resp = new AccountInfo();
        TAccount account = accountService.findBy(accountId);
        if (account != null) {
            BeanUtils.copyProperties(account, resp);
        }
        return resp;
    }

    @Override
    public String sendSmsCode(PlatformAccountReq.SmsReq req) {
        /*
           未注册也获取短信验证码
         */
        boolean hasRegister = accountService.hasRegister(req.getMobile());
        String smsCode = "";
        if (SmsType.PLATFORM_LOGIN.getKey().equals(req.getSmsType())) {
//            if (!hasRegister) {
//                throw new BusinessException(ApiCallResult.NOREGISTER);
//            }
            //是否超过发送验证码的间隔时间
            if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
                throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
            }
            smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
            smsService.sendSmsCode(req.getMobile(), smsCode);
        }  else if (SmsType.PLATFORM_FORGET_PASSWORD.getKey().equals(req.getSmsType())) {
            if (!hasRegister) {
                throw new BusinessException(ApiCallResult.NOREGISTER);
            }
            //是否超过发送验证码的间隔时间
            if(smsService.canSendSmsCode(req.getMobile(), req.getSmsType())){
                throw new BusinessException(ApiCallResult.CAN_NOT_SEND_VERIFY_CODE);
            }
            smsCode = smsService.getSmsCode(req.getMobile(), req.getSmsType());
            smsService.sendForgetPasswordSmsCode(req.getMobile(), smsCode);
        }
        return smsCode;
    }

    @Override
    public PlatformAccountResp.AccountResp forgetPassword(PlatformAccountReq.ForgetPasswordReq req) {
        // todo
        return null;
    }

    @Override
    public PlatformAccountResp.AccountResp modifyPassword(PlatformAccountReq.ModifyPasswordReq req) {
        accountService.modifyPassword(req.getLoginId(), req.getPassword());
        PlatformAccountResp.AccountResp resp = new PlatformAccountResp.AccountResp();
        resp.setLoginId(req.getLoginId());
        return resp;
    }

    @Override
    public int editProfile(PlatformAccountReq.EditProfileReq req) {
        TAccount updateAccount = new TAccount();
        BeanUtils.copyProperties(req, updateAccount);
        return accountService.updateAccount(updateAccount);
    }

    @Override
    public List<MembershipCardVO> findAvailableCard(Integer accountId) {
        List<TAccountMembershipCard> membershipCard = accountService.findMembershipCard(accountId);
        List<MembershipCardVO> list = new LinkedList<>();
        for (TAccountMembershipCard card : membershipCard) {
            MembershipCardVO membershipCardVO = new MembershipCardVO();
            BeanUtils.copyProperties(card, membershipCardVO);
            list.add(membershipCardVO);
        }
        return list;
    }
}
