package com.winback.admin.controller.account;

import com.alibaba.dubbo.config.annotation.Reference;
import com.winback.admin.controller.AbstractController;
import com.winback.admin.service.CaptchaService;
import com.winback.admin.shiro.ShiroAuthService;
import com.winback.arch.common.PageInfoResp;
import com.winback.arch.common.ResponseModel;
import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.facade.account.AccountFacade;
import com.winback.core.facade.account.SysFacade;
import com.winback.core.facade.account.req.AdminAccountReq;
import com.winback.core.facade.account.req.AdminSysReq;
import com.winback.core.facade.account.vo.LawyerApplyRecord;
import com.winback.core.facade.account.vo.Role;
import com.winback.core.facade.account.vo.Staff;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @author xdrodger
 * @Title: SysController
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 11:30
 */
@Slf4j
@RestController
@RequestMapping(value = "/confined/sys", produces = "application/json;charset=UTF-8")
public class SysController extends AbstractController {

    @Reference(version = "1.0.0")
    private SysFacade sysFacade;
    @Autowired
    private ShiroAuthService shiroAuthService;

    /**
     * 模块列表接口
     */
    @GetMapping(value = "/module/list")
    public ResponseModel moduleList(AdminSysReq.QueryReq req) {
        return ResponseModel.succ(sysFacade.getModuleList());
    }

    /**
     * 特殊权限列表接口
     */
    @GetMapping(value = "/special/permission/list")
    public ResponseModel specialPermissionList(AdminSysReq.QueryReq req) {
        return ResponseModel.succ(sysFacade.getSpecialPermissionList());
    }


    /**
     * 员工列表接口
     */
    @RequiresPermissions(value = {"permission_mgt_1_2_1", "permission_mgt_1_1_1"}, logical = Logical.OR)
    @GetMapping(value = "/staff/list")
    public ResponseModel staffList(AdminSysReq.QueryReq req) {
        return ResponseModel.succ(sysFacade.getStaffList(req));
    }

    /**
     * 员工详情接口
     */
    @GetMapping(value = "/staff/detail")
    public ResponseModel staffDetail(AdminSysReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Staff> resp = sysFacade.getStaffList(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 员工新增接口
     *
     */
    @PostMapping(value = "/staff/add")
    public ResponseModel staffAdd(@RequestBody AdminSysReq.StaffAddReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.staffAdd(req));
    }

    /**
     * 员工修改接口
     *
     */
    @PostMapping(value = "/staff/update")
    public ResponseModel staffUpdate(@RequestBody AdminSysReq.StaffUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.staffUpdate(req));
    }

    /**
     * 员工重置密码接口
     *
     */
    @PostMapping(value = "/staff/reset/password")
    public ResponseModel staffResetPassword(@RequestBody AdminSysReq.StaffResetPasswordReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        Assert.notNull(req.getPassword(), "password 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.staffResetPassword(req));
    }

    /**
     * 分组角色列表接口
     */
    @RequiresPermissions("permission_mgt_1_1_1")
    @GetMapping(value = "/role/list")
    public ResponseModel roleList(AdminSysReq.QueryReq req) {
        return ResponseModel.succ(sysFacade.getRoleList(req));
    }

    /**
     * 分组角色详情接口
     */
    @GetMapping(value = "/role/detail")
    public ResponseModel roleDetail(AdminSysReq.QueryReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        PageInfoResp<Role> resp = sysFacade.getRoleList(req);
        if (resp.getTotal() == 0) {
            return ResponseModel.fail(ApiCallResult.PARAMETER_INVALID);
        }
        return ResponseModel.succ(resp.getList().get(0));
    }

    /**
     * 分组角色新增接口
     *
     */
    @PostMapping(value = "/role/add")
    public ResponseModel roleAdd(@RequestBody AdminSysReq.RoleAddReq req) {
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.roleAdd(req));
    }

    /**
     * 分组角色修改接口
     *
     */
    @PostMapping(value = "/role/update")
    public ResponseModel roleUpdate(@RequestBody AdminSysReq.RoleUpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.roleUpdate(req));
    }

    /**
     * 分组角色删除接口
     *
     */
    @PostMapping(value = "/role/delete")
    public ResponseModel roleDelete(@RequestBody AdminSysReq.RoleDeleteReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setLoginId(loadCurLoginId());
        return ResponseModel.succ(sysFacade.roleDelete(req));
    }
}
