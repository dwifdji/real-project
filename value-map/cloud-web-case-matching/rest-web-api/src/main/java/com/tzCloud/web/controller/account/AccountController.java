package com.tzCloud.web.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.PlatformReq;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.core.facade.account.AccountFacade;
import com.tzCloud.core.facade.account.req.PlatformAccountReq;
import com.tzCloud.web.controller.AbstractController;
import com.tzCloud.web.vo.LoginInfo;
import com.tzCloud.web.vo.Profile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 获取账户信息接口
     */
    @GetMapping(value = "/confined/account/profile")
    public ResponseModel profile(PlatformReq req) {
        Profile profile = new Profile();
        LoginInfo loginInfo = loadCurLoginInfo();
        BeanUtils.copyProperties(loginInfo, profile);
        profile.setMembershipCardVOList(loadAvailableMemberCard());

        return ResponseModel.succ(profile);
    }

    /**
     * 更新账户信息接口
     */
    @PostMapping(value = "/confined/account/edit/profile")
    public ResponseModel editProfile(@RequestBody PlatformAccountReq.EditProfileReq req) {
        Assert.notNull(req.getId(), "id");
        return ResponseModel.succ(accountFacade.editProfile(req));
    }

    /**
     * 修改密码接口
     */
    @PostMapping(value = "/confined/account/modifyPassword")
    public ResponseModel modifyPassword(@RequestBody PlatformAccountReq.ModifyPasswordReq req) {
        if (StringUtils.isEmpty(req.getPassword())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.modifyPassword(req));
    }
}
