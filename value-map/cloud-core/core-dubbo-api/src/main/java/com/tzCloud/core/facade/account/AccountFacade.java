package com.tzCloud.core.facade.account;

import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.core.facade.account.resp.PlatformAccountResp;
import com.tzCloud.core.facade.account.vo.AccountInfo;
import com.tzCloud.core.facade.account.vo.MembershipCardVO;

import java.util.List;

/**
 * @author xdrodger
 * @Title: AccountFacade
 * @ProjectName tzCloud-parent
 * @Description:
 * @date 2019-04-19 13:16
 */
public interface AccountFacade {

    PlatformAccountResp.AccountResp loginOrRegister(PlatformAccountReq.LoginReq req);

    AccountInfo getAccountInfo(Integer accountId);

    String sendSmsCode(PlatformAccountReq.SmsReq req);

    PlatformAccountResp.AccountResp forgetPassword(PlatformAccountReq.ForgetPasswordReq req);

    PlatformAccountResp.AccountResp modifyPassword(PlatformAccountReq.ModifyPasswordReq req);

    int editProfile(PlatformAccountReq.EditProfileReq req);

    List<MembershipCardVO> findAvailableCard(Integer accountId);
}
