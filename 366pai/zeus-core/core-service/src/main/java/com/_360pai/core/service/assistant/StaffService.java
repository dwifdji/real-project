package com._360pai.core.service.assistant;


import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.PersonaReq;
import com._360pai.core.facade.account.req.RoleReq;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.PersonaResp;
import com._360pai.core.facade.account.resp.RoleResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.facade.account.vo.RoleVo;
import com._360pai.core.facade.account.vo.StaffVo;
import com._360pai.core.model.account.SysPermission;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.model.assistant.TBackstageOperationRecord;

import java.util.List;
import java.util.Set;

public interface StaffService{
    Staff getById(Integer id);

    StaffResp.BasicResp getStaffBasic(StaffReq.BaseReq req);

    StaffResp.DetailResp getStaff(StaffReq.BaseReq req);

    PageInfoResp<StaffVo> getStaffListByPage(StaffReq.QueryReq req);

    StaffResp createStaff(StaffReq.CreateReq req);

    StaffResp updateStaff(StaffReq.UpdateReq req);

    StaffResp resetStaffPassword(StaffReq.BaseReq req);

    StaffResp staffModifyPassword(StaffReq.ModifyPasswordReq req);

    PageInfoResp<RoleVo> getRoleListByPage(RoleReq.QueryReq req);

    RoleResp.DetailResp getRole(RoleReq.BaseReq req);

    RoleResp createRole(RoleReq.CreateReq req);

    RoleResp updateRole(RoleReq.UpdateReq req);

    StaffResp.PermissionResp getStaffPermissionInfo(StaffReq.BaseReq req);

    Set<String> getPermissionCodeList(Integer staffId);

    Set<String> getRoleIdList(Integer staffId);

    Set<String> getRolePermissionCodeList(Integer roleId);

    Set<String> getStaffSpecialPermissionCodeList(Integer staffId);

    Set<String> getAllNormalPermissionCodeList();

    List<ModuleVo> getAllModuleList();

    List<ModuleVo> getRoleModuleList(Integer roleId);

    ListResp<PermissionVo> getSpecialPermissionList();

    PersonaResp login(PersonaReq req) throws Exception;

    StaffResp toggleStatus(StaffReq.ToggleStatusReq req);

    boolean saveOperationRecord(TBackstageOperationRecord record);
}