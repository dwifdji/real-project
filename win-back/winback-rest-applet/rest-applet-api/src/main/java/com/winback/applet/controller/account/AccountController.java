package com.winback.applet.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.applet.controller.AbstractController;
import com.winback.applet.vo.LoginInfo;
import com.winback.applet.vo.Profile;
import com.winback.arch.common.AppletReq;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AppletAccountReq;
import com.winback.core.facade.contract.ContractFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author xdrodger
 * @Title: AccountController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 11:26
 */
@Slf4j
@RestController
public class AccountController  extends AbstractController {

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private ContractFacade contractFacade;

    /**
     * 绑定账户接口
     */
    @PostMapping(value = "/confined/bind/account")
    public ResponseModel bindAccount(@RequestBody AppletAccountReq.BindAccountReq req) {
        if (StringUtils.isEmpty(req.getMobile()) || StringUtils.isEmpty(req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setLoginId(loadCurLoginId());
        accountFacade.bindAccount(req);
        Profile profile = new Profile();
        LoginInfo loginInfo = loadCurLoginInfo();
        BeanUtils.copyProperties(loginInfo, profile);
        if (loginInfo.getAccountId() != null) {
            profile.setBindAccountFlag(true);
        }
        return ResponseModel.succ(profile);
    }

    /**
     * 获取账户信息接口
     */
    @GetMapping(value = "/confined/account/profile")
    public ResponseModel profile(AppletReq req) {
        Profile profile = new Profile();
        LoginInfo loginInfo = loadCurLoginInfo();
        BeanUtils.copyProperties(loginInfo, profile);
        if (loginInfo.getAccountId() != null) {
            profile.setBindAccountFlag(true);
        }
        return ResponseModel.succ(profile);
    }

    /**
     * 我收藏的合同列表接口(分页)
     */
    @GetMapping(value = "/confined/account/favorite/contract/list")
    public ResponseModel getFavoriteContractList(AppletReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getFavoriteContractList(req));
    }

    /**
     * 我的消息列表接口(分页)
     */
    @GetMapping(value = "/confined/account/message/list")
    public ResponseModel getMessageList(AppletAccountReq.MessageReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getAppletMessageList(req));
    }

    /**
     * 我的消息详情接口
     */
    @GetMapping(value = "/confined/account/message/detail")
    public ResponseModel getMessage(AppletAccountReq.MessageReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getAppletMessage(req));

    }
}
