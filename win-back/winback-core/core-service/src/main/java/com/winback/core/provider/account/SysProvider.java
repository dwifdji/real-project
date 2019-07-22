package com.winback.core.provider.account;

import com.alibaba.dubbo.config.annotation.Service;
import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.SysFacade;
import com.winback.core.facade.account.req.AdminSysReq;
import com.winback.core.facade.account.vo.Module;
import com.winback.core.facade.account.vo.Permission;
import com.winback.core.facade.account.vo.Role;
import com.winback.core.facade.account.vo.Staff;
import com.winback.core.model.account.TSysStaff;
import com.winback.core.service.account.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: SysProvider
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:08
 */
@Slf4j
@Component
@Service(version = "1.0.0")
public class SysProvider implements SysFacade {
    @Autowired
    private PermissionService permissionService;

    @Override
    public Staff getStaff(Integer staffId) {
        TSysStaff staff = permissionService.getStaff(staffId);
        Staff vo = new Staff();
        BeanUtils.copyProperties(staff, vo);
        return vo;
    }

    @Override
    public ListResp<Module> getModuleList() {
        return permissionService.getModuleList();
    }

    @Override
    public List<Module> getModuleList(Integer roleId) {
        return permissionService.getModuleList(roleId);
    }

    @Override
    public Set<String> getAllNormalPermissionCodeList() {
        return permissionService.getAllNormalPermissionCodeList();
    }

    @Override
    public Set<String> getRoleIdList(Integer staffId) {
        return permissionService.getRoleIdList(staffId);
    }

    @Override
    public Set<String> getRolePermissionCodeList(Integer roleId) {
        return permissionService.getRolePermissionCodeList(roleId);
    }

    @Override
    public Set<String> getStaffSpecialPermissionCodeList(Integer staffId) {
        return permissionService.getStaffSpecialPermissionCodeList(staffId);
    }

    @Override
    public ListResp<Permission> getSpecialPermissionList() {
        return permissionService.getSpecialPermissionList();
    }

    @Override
    public PageInfoResp<Staff> getStaffList(AdminSysReq.QueryReq req) {
        return permissionService.getStaffList(req);
    }

    @Override
    public Integer staffAdd(AdminSysReq.StaffAddReq req) {
        return permissionService.staffAdd(req);
    }

    @Override
    public Integer staffUpdate(AdminSysReq.StaffUpdateReq req) {
        return permissionService.staffUpdate(req);
    }

    @Override
    public Integer staffResetPassword(AdminSysReq.StaffResetPasswordReq req) {
        return permissionService.staffResetPassword(req);
    }

    @Override
    public PageInfoResp<Role> getRoleList(AdminSysReq.QueryReq req) {
        return permissionService.getRoleList(req);
    }

    @Override
    public Integer roleAdd(AdminSysReq.RoleAddReq req) {
        return permissionService.roleAdd(req);
    }

    @Override
    public Integer roleUpdate(AdminSysReq.RoleUpdateReq req) {
        return permissionService.roleUpdate(req);
    }

    @Override
    public Integer roleDelete(AdminSysReq.RoleDeleteReq req) {
        return permissionService.roleDelete(req);
    }
}
