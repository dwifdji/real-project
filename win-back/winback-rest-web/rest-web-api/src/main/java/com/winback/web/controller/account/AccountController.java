package com.winback.web.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.arch.common.AppReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AppAccountReq;
import com.winback.core.facade.account.vo.AppMessage;
import com.winback.core.facade.contract.ContractFacade;
import com.winback.web.controller.AbstractController;
import com.winback.web.vo.LoginInfo;
import com.winback.web.vo.Profile;
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
     * 忘记密码接口
     */
    @PostMapping(value = "/confined/account/modifyPassword")
    public ResponseModel modifyPassword(@RequestBody AppAccountReq.ModifyPasswordReq req) {
        if (StringUtils.isEmpty(req.getPassword())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.modifyPassword(req));
    }

    /**
     * 律师认证申请接口
     */
    @PostMapping(value = "/confined/account/lawyer/apply")
    public ResponseModel lawyerApply(@RequestBody AppAccountReq.LawyerApplyReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.lawyerApply(req));
    }

    /**
     * 加盟商认证申请接口
     */
    @PostMapping(value = "/confined/account/franchisee/apply")
    public ResponseModel franchiseeApply(@RequestBody AppAccountReq.FranchiseeApplyReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.franchiseeApply(req));
    }

    /**
     * 获取账户信息接口
     */
    @GetMapping(value = "/confined/account/profile")
    public ResponseModel profile(AppReq req) {
        Profile profile = new Profile();
        LoginInfo loginInfo = loadCurLoginInfo();
        BeanUtils.copyProperties(loginInfo, profile);


        return ResponseModel.succ(profile);
    }

    /**
     * 更新账户信息接口
     */
    @PostMapping(value = "/confined/account/update")
    public ResponseModel update(@RequestBody AppAccountReq.AccountUpdateReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.updateAccount(req));
    }

    /**
     * 我的客户列表接口
     */
    @GetMapping(value = "/confined/account/invite/customer/list")
    public ResponseModel inviteCustomerList(AppAccountReq.FranchiseeQueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getInviteCustomerListByPage(req));
    }

    /**
     * 我推荐的案件的列表接口
     */
    @GetMapping(value = "/confined/account/invite/case/list")
    public ResponseModel inviteCaseList(AppAccountReq.FranchiseeQueryReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getInviteCaseListByPage(req));
    }

    /**
     * 我收藏的合同列表接口(分页)
     */
    @GetMapping(value = "/confined/account/favorite/contract/list")
    public ResponseModel getFavoriteContractList(AppReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(contractFacade.getFavoriteContractList(req));
    }

    /**
     * 我的消息列表接口
     */
    @GetMapping(value = "/confined/account/message/list")
    public ResponseModel getMessageList(AppAccountReq.MessageReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getAppMessageListByPage(req));
    }

    /**
     * 我的消息详情接口
     */
    @GetMapping(value = "/confined/account/message/detail")
    public ResponseModel getMessage(AppAccountReq.MessageReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.getAppMessage(req));

    }

    /**
     * 上传通讯录接口
     */
    @PostMapping(value = "/confined/account/upload/contacts")
    public ResponseModel uploadContacts(@RequestBody AppAccountReq.UploadContactsReq req) {
        req.setLoginId(loadCurLoginId());
        accountFacade.uploadContacts(req);
        return ResponseModel.succ();
    }

}
