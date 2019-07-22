package com._360pai.core.provider.account;

import com._360pai.arch.common.ListResp;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.common.constants.BackstageOperationEnum;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.RoleReq;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.RoleResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.account.vo.ModuleVo;
import com._360pai.core.facade.account.vo.PermissionVo;
import com._360pai.core.facade.account.vo.RoleVo;
import com._360pai.core.facade.account.vo.StaffVo;
import com._360pai.core.model.assistant.TBackstageOperationRecord;
import com._360pai.core.service.assistant.StaffService;
import com.alibaba.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author xdrodger
 * @Title: StaffProvider
 * @ProjectName zeus
 * @Description:
 * @date 2018/10/7 17:01
 */
@Component
@Service(version = "1.0.0")
public class StaffProvider implements StaffFacade {
    public static final Logger LOGGER = LoggerFactory.getLogger(StaffProvider.class);
    @Autowired
    private StaffService staffService;

    @Override
    public PageInfoResp<StaffVo> getStaffListByPage(StaffReq.QueryReq req) {
        return staffService.getStaffListByPage(req);
    }

    @Override
    public StaffResp.BasicResp getStaffBasic(StaffReq.BaseReq req) {
        return staffService.getStaffBasic(req);
    }

    @Override
    public StaffResp.DetailResp getStaff(StaffReq.BaseReq req) {
        return staffService.getStaff(req);
    }

    @Override
    public StaffResp createStaff(StaffReq.CreateReq req) {
        return staffService.createStaff(req);
    }

    @Override
    public StaffResp updateStaff(StaffReq.UpdateReq req) {
        return staffService.updateStaff(req);
    }

    @Override
    public StaffResp resetStaffPassword(StaffReq.BaseReq req) {
        return staffService.resetStaffPassword(req);
    }

    @Override
    public StaffResp staffModifyPassword(StaffReq.ModifyPasswordReq req) {
        return staffService.staffModifyPassword(req);
    }

    @Override
    public PageInfoResp<RoleVo> getRoleListByPage(RoleReq.QueryReq req) {
        return staffService.getRoleListByPage(req);
    }

    @Override
    public RoleResp.DetailResp getRole(RoleReq.BaseReq req) {
        return staffService.getRole(req);
    }

    @Override
    public ListResp<ModuleVo> getModuleList(RoleReq.BaseReq req) {
        ListResp<ModuleVo> resp = new ListResp<>();
        List<ModuleVo> modules = staffService.getAllModuleList();
        Iterator<ModuleVo> itr = modules.iterator();
        while (itr.hasNext()) {
            ModuleVo moduleVo = itr.next();
            if ("nbgl".equals(moduleVo.getModuleCode())) {
                itr.remove();
            }
        }
        resp.setList(modules);
        return resp;
    }

    @Override
    public RoleResp createRole(RoleReq.CreateReq req) {
        return staffService.createRole(req);
    }

    @Override
    public RoleResp updateRole(RoleReq.UpdateReq req) {
        return staffService.updateRole(req);
    }

    @Override
    public StaffResp.PermissionResp getStaffPermissionInfo(StaffReq.BaseReq req) {
        return staffService.getStaffPermissionInfo(req);
    }

    @Override
    public Set<String> getRoleIdList(Integer staffId) {
        return staffService.getRoleIdList(staffId);
    }

    @Override
    public Set<String> getRolePermissionCodeList(Integer roleId) {
        return staffService.getRolePermissionCodeList(roleId);
    }

    @Override
    public Set<String> getStaffSpecialPermissionCodeList(Integer staffId) {
        return staffService.getStaffSpecialPermissionCodeList(staffId);
    }

    @Override
    public Set<String> getAllNormalPermissionCodeList() {
        return staffService.getAllNormalPermissionCodeList();
    }

    @Override
    public List<ModuleVo> getAllModuleList() {
        return staffService.getAllModuleList();
    }

    @Override
    public List<ModuleVo> getRoleModuleList(Integer roleId) {
        return staffService.getRoleModuleList(roleId);
    }

    @Override
    public ListResp<PermissionVo> getSpecialPermissionList() {
        return staffService.getSpecialPermissionList();
    }

    @Override
    public StaffResp toggleStatus(StaffReq.ToggleStatusReq req) {
        return staffService.toggleStatus(req);
    }

    @Override
    public boolean saveAuctionActivityOperationRecord(Integer operatorId, String content, Integer activityId) {
        try {
            LOGGER.info("拍卖活动后台操作记录保存，operatorId={}，content={}，refId={}", operatorId, content, activityId);
            TBackstageOperationRecord record = new TBackstageOperationRecord();
            record.setType(BackstageOperationEnum.Type.AUCTION_ACTIVITY.getKey());
            record.setOperatorId(operatorId);
            record.setContent(content);
            record.setRefId(activityId.longValue());
            record.setCreateTime(new Date());
            return staffService.saveOperationRecord(record);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("拍卖活动后台操作记录保存失败，operatorId={}，content={}，refId={}", operatorId, content, activityId);
        }
        return false;
    }

    @Override
    public boolean saveEnrollingActivityOperationRecord(Integer operatorId, String content, Integer activityId) {
        try {
            LOGGER.info("预招商活动后台操作记录保存，operatorId={}，content={}，refId={}", operatorId, content, activityId);
            TBackstageOperationRecord record = new TBackstageOperationRecord();
            record.setType(BackstageOperationEnum.Type.ENROLLING_ACTIVITY.getKey());
            record.setOperatorId(operatorId);
            record.setContent(content);
            record.setRefId(activityId.longValue());
            record.setCreateTime(new Date());
            return staffService.saveOperationRecord(record);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("预招商活动后台操作记录保存失败，operatorId={}，content={}，refId={}", operatorId, content, activityId);
        }
        return false;
    }

    @Override
    public boolean saveAuctionOrderOperationRecord(Integer operatorId, String content, Long orderId) {
        try {
            LOGGER.info("拍卖订单后台操作记录保存，operatorId={}，content={}，refId={}", operatorId, content, orderId);
            TBackstageOperationRecord record = new TBackstageOperationRecord();
            record.setType(BackstageOperationEnum.Type.AUCTION_ORDER.getKey());
            record.setOperatorId(operatorId);
            record.setContent(content);
            record.setRefId(orderId);
            record.setCreateTime(new Date());
            return staffService.saveOperationRecord(record);
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("拍卖订单后台操作记录保存失败，operatorId={}，content={}，refId={}", operatorId, content, orderId);
        }
        return false;
    }
}
