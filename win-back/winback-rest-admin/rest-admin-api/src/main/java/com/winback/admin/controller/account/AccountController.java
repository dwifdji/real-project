package com.winback.admin.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.shiro.PermissionService;
import com.winback.admin.vo.LoginInfo;
import com.winback.admin.vo.Profile;
import com.winback.arch.common.AdminReq;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade._case.CaseFacade;
import com.winback.core.facade._case.req.AdminCaseReq;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xdrodger
 * @Title: AccountController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/25 11:26
 */
@Slf4j
@RestController
@RequestMapping(value = "/confined/account", produces = "application/json;charset=UTF-8")
public class AccountController extends AbstractController {

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private CaseFacade caseFacade;
    @Autowired
    private PermissionService permissionService;

    /**
     * 获取账户信息接口
     */
    @GetMapping(value = "/profile")
    public ResponseModel profile(AdminReq req) {
        Profile profile = new Profile();
        LoginInfo loginInfo = loadCurLoginInfo();
        BeanUtils.copyProperties(loginInfo, profile);
        Map<String, Object> data = (Map<String, Object>) JSON.toJSON(profile);
        data.put("permission", permissionService.getPermission(loginInfo.getId() + "", loginInfo.getAdminFlag()));
        return ResponseModel.succ(data);
    }

    /**
     * 注册用户列表接口
     */
    @RequiresPermissions("user_mgt_1_1_1")
    @GetMapping(value = "/register/account/list")
    public ResponseModel registerAccountList(AdminAccountReq.AccountQueryReq req) {
        return ResponseModel.succ(accountFacade.getAccountListByPage(req));
    }

    /**
     * 注册用户详情接口
     */
    @GetMapping(value = "/register/account/detail")
    public ResponseModel registerAccountDetail(AdminAccountReq.AccountQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Account> resp = accountFacade.getAccountListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 更新注册账户信息接口
     */
    @PostMapping(value = "/register/account/update")
    public ResponseModel updateRegisterAccount(@RequestBody AdminAccountReq.AccountUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(accountFacade.updateAccount(req));
    }

    /**
     * 当事人列表接口接口
     */
    @RequiresPermissions("user_mgt_1_2_1")
    @GetMapping(value = "/party/list")
    public ResponseModel partyList(AdminAccountReq.PartyQueryReq req) {
        return ResponseModel.succ(accountFacade.getPartyListByPage(req));
    }

    /**
     * 当事人详情接口
     */
    @GetMapping(value = "/party/detail")
    public ResponseModel partyDetail(AdminAccountReq.PartyQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Party> resp = accountFacade.getPartyListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 用户发布案件列表接口
     */
    @GetMapping(value = "/publish/case/list")
    public ResponseModel publishCaseList(AdminCaseReq.QueryReq req) {
        return ResponseModel.succ(caseFacade.getPublishCaseList(req));
    }

    /**
     * 律师认证申请列表接口
     */
    @RequiresPermissions("user_mgt_1_3_1")
    @GetMapping(value = "/lawyer/apply/record/list")
    public ResponseModel lawyerApplyRecordList(AdminAccountReq.LawyerQueryReq req) {
        return ResponseModel.succ(accountFacade.getLawyerApplyRecordListByPage(req));
    }

    /**
     * 律师认证申请详情接口
     */
    @GetMapping(value = "/lawyer/apply/record/detail")
    public ResponseModel lawyerApplyRecordDetail(AdminAccountReq.LawyerQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<LawyerApplyRecord> resp = accountFacade.getLawyerApplyRecordListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 律师认证申请通过接口
     *
     */
    @PostMapping(value = "/lawyer/apply/approve")
    public ResponseModel lawyerApplyApprove(@RequestBody AdminAccountReq.LawyerVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.lawyerApplyApprove(req));
    }

    /**
     * 律师认证申请拒绝接口
     *
     */
    @PostMapping(value = "/lawyer/apply/reject")
    public ResponseModel lawyerApplyReject(@RequestBody AdminAccountReq.LawyerVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getId(), "reason 参数不能为空");
        req.setLoginId(loadCurLoginId());
        accountFacade.lawyerApplyReject(req);
        return ResponseModel.succ();
    }

    /**
     * 律师列表接口
     */
    @RequiresPermissions("user_mgt_1_3_2")
    @GetMapping(value = "/lawyer/list")
    public ResponseModel lawyerList(AdminAccountReq.LawyerQueryReq req) {
        return ResponseModel.succ(accountFacade.getLawyerListByPage(req));
    }

    /**
     * 律师详情接口
     */
    @GetMapping(value = "/lawyer/detail")
    public ResponseModel lawyerDetail(AdminAccountReq.LawyerQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Lawyer> resp = accountFacade.getLawyerListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 修改律师接口
     *
     */
    @PostMapping(value = "/lawyer/update")
    public ResponseModel lawyerUpdate(@RequestBody AdminAccountReq.LawyerUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.lawyerUpdate(req));
    }

    /**
     * 律所列表接口
     */
    @RequiresPermissions("user_mgt_1_5_1")
    @GetMapping(value = "/law/firm/list")
    public ResponseModel lawFirmList(AdminAccountReq.LawFirmQueryReq req) {
        return ResponseModel.succ(accountFacade.getLawFirmListByPage(req));
    }

    /**
     * 律所详情接口
     */
    @GetMapping(value = "/law/firm/detail")
    public ResponseModel lawFirmDetail(AdminAccountReq.LawFirmQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<LawFirm> resp = accountFacade.getLawFirmListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 添加律所接口
     *
     */
    @PostMapping(value = "/law/firm/add")
    public ResponseModel lawFirmAdd(@RequestBody AdminAccountReq.LawFirmAddReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.lawFirmAdd(req));
    }

    /**
     * 修改律所接口
     *
     */
    @PostMapping(value = "/law/firm/update")
    public ResponseModel lawFirmUpdate(@RequestBody AdminAccountReq.LawFirmUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.lawFirmUpdate(req));
    }

    /**
     * 加盟商认证申请列表接口
     */
    @RequiresPermissions("user_mgt_1_4_1")
    @GetMapping(value = "/franchisee/apply/record/list")
    public ResponseModel franchiseeApplyRecordList(AdminAccountReq.FranchiseeQueryReq req) {
        return ResponseModel.succ(accountFacade.getFranchiseeApplyRecordListByPage(req));
    }

    /**
     * 加盟商认证申请详情接口
     */
    @GetMapping(value = "/franchisee/apply/record/detail")
    public ResponseModel franchiseeApplyRecordDetail(AdminAccountReq.FranchiseeQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<FranchiseeApplyRecord> resp = accountFacade.getFranchiseeApplyRecordListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 加盟商认证申请通过接口
     *
     */
    @PostMapping(value = "/franchisee/apply/approve")
    public ResponseModel franchiseeApplyApprove(@RequestBody AdminAccountReq.FranchiseeVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.franchiseeApplyApprove(req));
    }

    /**
     * 加盟商认证申请拒绝接口
     *
     */
    @PostMapping(value = "/franchisee/apply/reject")
    public ResponseModel franchiseeApplyReject(@RequestBody AdminAccountReq.FranchiseeVerifyReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getId(), "reason 参数不能为空");
        req.setLoginId(loadCurLoginId());
        accountFacade.franchiseeApplyReject(req);
        return ResponseModel.succ();
    }

    /**
     * 修改加盟商接口
     *
     */
    @PostMapping(value = "/law/franchisee/update")
    public ResponseModel franchiseeUpdate(@RequestBody AdminAccountReq.FranchiseeUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(accountFacade.franchiseeUpdate(req));
    }


    /**
     * 加盟商列表接口
     */
    @RequiresPermissions("user_mgt_1_4_2")
    @GetMapping(value = "/franchisee/list")
    public ResponseModel franchiseeList(AdminAccountReq.FranchiseeQueryReq req) {
        return ResponseModel.succ(accountFacade.getFranchiseeListByPage(req));
    }

    /**
     * 加盟商详情接口
     */
    @GetMapping(value = "/franchisee/detail")
    public ResponseModel franchiseeDetail(AdminAccountReq.FranchiseeQueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Franchisee> resp = accountFacade.getFranchiseeListByPage(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 加盟商邀请客户列表接口
     */
    @GetMapping(value = "/franchisee/invite/customer/list")
    public ResponseModel franchiseeInviteCustomerList(AdminAccountReq.FranchiseeQueryReq req) {
        return ResponseModel.succ(accountFacade.getFranchiseeInviteCustomerListByPage(req));
    }

    /**
     * 加盟商邀请客户列表接口
     */
    @GetMapping(value = "/franchisee/invite/case/list")
    public ResponseModel franchiseeInviteCaseList(AdminAccountReq.FranchiseeQueryReq req) {
        return ResponseModel.succ(accountFacade.getFranchiseeInviteCaseListByPage(req));
    }

    /**
     * 项目经理列表接口
     */
    @RequiresPermissions("case_mgt_1_1_24")
    @GetMapping(value = "/getProjectManagerList")
    public ResponseModel getProjectManagerList(AdminAccountReq.AccountQueryReq req) {
        return ResponseModel.succ(accountFacade.getProjectManagerListByPage(req));
    }

}
