package com._360pai.admin.controller.account;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.RoleReq;
import com._360pai.core.facade.account.req.StaffReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author xdrodger
 * @Title: StaffController
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/13 10:04
 */
@RestController
@RequestMapping(value = "/admin/account", produces = "application/json;charset=UTF-8")
public class StaffController extends AbstractController {
    public static final Logger LOGGER = LoggerFactory.getLogger(StaffController.class);

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;

    /**
     * 有效员工列表接口
     */
    @GetMapping(value = "/enabled/staff/list")
    public ResponseModel enabledStaffList(StaffReq.QueryReq req) {
        req.setStatus("1");
        req.setBasicInfo(true);
        return ResponseModel.succ(staffFacade.getStaffListByPage(req));
    }

    /**
     * 员工列表
     */
    @RequiresPermissions("nbgl_yggl:list")
    @GetMapping(value = "/staff/list")
    public ResponseModel staffList(StaffReq.QueryReq req) {
        return ResponseModel.succ(staffFacade.getStaffListByPage(req));
    }

    /**
     * 员工列表
     */
    @GetMapping(value = "/staff/detail")
    public ResponseModel staffDetail(StaffReq.BaseReq req) {
        return ResponseModel.succ(staffFacade.getStaff(req));
    }

    /**
     * 添加员工
     */
    @PostMapping(value = "/staff/add")
    public ResponseModel addStaff(@Valid @RequestBody StaffReq.CreateReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.createStaff(req));
    }

    /**
     * 更新员工信息
     */
    @PostMapping(value = "/staff/update")
    public ResponseModel updateStaff(@Valid @RequestBody StaffReq.UpdateReq req) {
        Assert.notNull(req.getStaffId(), "staffId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.updateStaff(req));
    }

    /**
     * 重置密码
     */
    @PostMapping(value = "/staff/reset/password")
    public ResponseModel resetStaff(@RequestBody StaffReq.BaseReq req) {
        Assert.notNull(req.getStaffId(), "staffId 参数不能为空");
        Assert.notNull(req.getPassword(), "password 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.resetStaffPassword(req));
    }

    /**
     * 角色列表接口
     */
    @RequiresPermissions("nbgl_jsgl:list")
    @GetMapping(value = "/role/list")
    public ResponseModel roleList(RoleReq.QueryReq req) {
        return ResponseModel.succ(staffFacade.getRoleListByPage(req));
    }

    /**
     * 角色详情接口
     */
    @GetMapping(value = "/role/detail")
    public ResponseModel roleDetail(RoleReq.BaseReq req) {
        return ResponseModel.succ(staffFacade.getRole(req));
    }

    /**
     * 后台操作权限列表接口
     */
    @GetMapping(value = "/module/list")
    public ResponseModel moduleList(RoleReq.BaseReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.getModuleList(req));
    }

    /**
     * 创建角色
     */
    @PostMapping(value = "/role/create")
    public ResponseModel createRole(@Valid @RequestBody RoleReq.CreateReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.createRole(req));
    }

    /**
     * 更新角色
     */
    @PostMapping(value = "/role/update")
    public ResponseModel updateRole(@Valid @RequestBody RoleReq.UpdateReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.updateRole(req));
    }

    /**
     * 后台操作权限列表接口
     */
    @GetMapping(value = "/special/permission/list")
    public ResponseModel specialPermissionList(RoleReq.BaseReq req) {
        return ResponseModel.succ(staffFacade.getSpecialPermissionList());
    }

    /**
     * 启用/禁用员工
     */
    @PostMapping(value = "/staff/toggle/status")
    public ResponseModel staffEnable(@Valid @RequestBody StaffReq.ToggleStatusReq req) {
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(staffFacade.toggleStatus(req));
    }
}
