package com._360pai.core.facade.account;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.RoleReq;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.RoleResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.facade.account.vo.RoleVo;
import com._360pai.core.facade.account.vo.StaffVo;

import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: StaffFacade
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 17:01
 */
public interface StaffFacade {

    PageInfoResp<StaffVo> getStaffListByPage(StaffReq.QueryReq req);

    StaffResp.BasicResp getStaffBasic(StaffReq.BaseReq req);

    StaffResp.DetailResp getStaff(StaffReq.BaseReq req);

    StaffResp createStaff(StaffReq.CreateReq req);

    StaffResp updateStaff(StaffReq.UpdateReq req);

    StaffResp resetStaffPassword(StaffReq.BaseReq req);

    StaffResp staffModifyPassword(StaffReq.ModifyPasswordReq req);

    PageInfoResp<RoleVo> getRoleListByPage(RoleReq.QueryReq req);

    RoleResp.DetailResp getRole(RoleReq.BaseReq req);

    ListResp<ModuleVo> getModuleList(RoleReq.BaseReq req);

    RoleResp createRole(RoleReq.CreateReq req);

    RoleResp updateRole(RoleReq.UpdateReq req);

    StaffResp.PermissionResp getStaffPermissionInfo(StaffReq.BaseReq req);

    Set<String> getRoleIdList(Integer staffId);

    Set<String> getRolePermissionCodeList(Integer roleId);

    Set<String> getStaffSpecialPermissionCodeList(Integer staffId);

    Set<String> getAllNormalPermissionCodeList();

    List<ModuleVo> getAllModuleList();

    List<ModuleVo> getRoleModuleList(Integer roleId);

    ListResp<PermissionVo> getSpecialPermissionList();

    StaffResp toggleStatus(StaffReq.ToggleStatusReq req);

    boolean saveAuctionActivityOperationRecord(Integer operatorId, String content, Integer activityId);

    boolean saveEnrollingActivityOperationRecord(Integer operatorId, String content, Integer activityId);

    boolean saveAuctionOrderOperationRecord(Integer operatorId, String content, Long orderId);
}
