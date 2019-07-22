package com.winback.core.facade.account;

import com.winback.arch.common.ListResp;
import com.winback.arch.common.PageInfoResp;
import com.winback.core.facade.account.req.AdminSysReq;
import com.winback.core.facade.account.vo.Module;
import com.winback.core.facade.account.vo.Permission;
import com.winback.core.facade.account.vo.Role;
import com.winback.core.facade.account.vo.Staff;

import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: SysFacade
 * @ProjectName winback
 * @Description:
 * @date 2019/1/30 14:08
 */
public interface SysFacade {
    Staff getStaff(Integer staffId);

    ListResp<Module> getModuleList();

    List<Module> getModuleList(Integer roleId);

    Set<String> getAllNormalPermissionCodeList();

    Set<String> getRoleIdList(Integer staffId);

    Set<String> getRolePermissionCodeList(Integer roleId);

    Set<String> getStaffSpecialPermissionCodeList(Integer staffId);

    ListResp<Permission> getSpecialPermissionList();

    PageInfoResp<Staff> getStaffList(AdminSysReq.QueryReq req);

    Integer staffAdd(AdminSysReq.StaffAddReq req);

    Integer staffUpdate(AdminSysReq.StaffUpdateReq req);

    Integer staffResetPassword(AdminSysReq.StaffResetPasswordReq req);

    PageInfoResp<Role> getRoleList(AdminSysReq.QueryReq req);

    Integer roleAdd(AdminSysReq.RoleAddReq req);

    Integer roleUpdate(AdminSysReq.RoleUpdateReq req);

    Integer roleDelete(AdminSysReq.RoleDeleteReq req);
}
