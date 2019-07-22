package com._360pai.admin.controller.enrolling;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.utils.EnrollingExcelUtils;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：预招商接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:09
 */
@RestController
public class EnrollingAdminController extends AbstractController{

    @Reference(version = "1.0.0")
    private EnrollingFacade enrollingFacade;


    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;

    @Reference(version = "1.0.0")
    StaffFacade staffFacade;


    /**
     * 获取预招商列表
     */
    @RequiresPermissions("yzsgl_dywgl:list")
    @GetMapping(value = "/admin/enrolling_activity/enrolling_activities")
    public ResponseModel enrollingActivities(EnrollingReq.activitiesListReq req) {
        //参数校验

        if("4".equals(req.getType())){
            req.setType(null);
            req.setNoThirdPartyStatus(4);

        }else{
            req.setType("1");
            req.setNoThirdPartyStatus(0);

        }
        return enrollingFacade.getEnrollingActivityList(req);
    }



    /**
     * 获取债权预招商列表
     */
    @RequiresPermissions("yzsgl_zqwqgl:list")
    @GetMapping(value = "/admin/enrolling_activity/creditor_enrolling_activities")
    public ResponseModel creditorEnrollingActivities(EnrollingReq.activitiesListReq req) {
        //type 为4 时 查询全部
        if("4".equals(req.getType())){
            req.setType(null);
        }else{
            req.setType("2");

        }
        req.setNoThirdPartyStatus(0);
        return enrollingFacade.getEnrollingActivityList(req);
    }

    /**
     * 获取物权预招商列表
     */
    @RequiresPermissions("yzsgl_zqwqgl:right_enrolling_activity_list")
    @GetMapping(value = "/admin/enrolling_activity/right_enrolling_activities")
    public ResponseModel rightEnrollingActivities(EnrollingReq.activitiesListReq req) {
        //参数校验
        if("4".equals(req.getType())){
            req.setType(null);
        }else{
            req.setType("3");

        }
        req.setNoThirdPartyStatus(0);
        return enrollingFacade.getEnrollingActivityList(req);
    }


    /**
     * 预招商查看基本信息接口
     */
    @GetMapping(value = "/admin/enrolling_activity/base_info")
    public ResponseModel baseInfo(EnrollingReq.activityIdTypeReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        req.setReqType(EnrollingEnum.REQ_TYPE.ADMIN.getType());
        return enrollingWebFacade.myActivityInfo(req);

    }

    /**
     * 预招商活动详情
     */
    @GetMapping(value = "/admin/enrolling_activity/detail")
    public ResponseModel detail(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return enrollingFacade.getDetail(req);
    }

    /**
     * 预招商报名列表
     */
    @GetMapping(value = "/admin/enrolling_activity/enrolling_orders")
    public ResponseModel enrollingOrders(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.enrollingOrders(req);
    }



    /**
     * 预招商提醒列表
     */
    @GetMapping(value = "/admin/enrolling_activity/notified_list")
    public ResponseModel notifiedList(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.notifiedList(req);
    }

    /**
     * 预招商分享列表
     */
    @GetMapping(value = "/admin/enrolling_activity/share_list")
    public ResponseModel shareList(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.shareList(req);
    }

    /**
     * 预招商关注列表
     */
    @GetMapping(value = "/admin/enrolling_activity/focus_list")
    public ResponseModel focusList(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());

        }
        return enrollingFacade.focusList(req);
    }
    /**
     * 预招商进展列表
     */
    @GetMapping(value = "/admin/enrolling_activity/progress_records")
    public ResponseModel progressRecords(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return enrollingFacade.progressRecords(req);
    }


    /**
     * 预招商结果信息
     */
    @GetMapping(value = "/admin/enrolling_activity/result")
    public ResponseModel result(EnrollingReq.activityIdReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.result(req);
    }


    /**
     * 编辑预招商详情接口
     */
    @PostMapping(value = "/admin/enrolling_activity/update_detail")
    public ResponseModel updateDetail(@RequestBody EnrollingReq.updateDetailReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.updateDetail(req);
    }


    /**
     * 编辑可见设置
     */
    @PostMapping(value = "/admin/enrolling_activity/update_visibility_level")
    public ResponseModel updateVisibilityLevel(@RequestBody EnrollingReq.updateVisibilityLevelReq req) {

        //校验参数
        if(StringUtils.isEmpty(req.getActivityId())){

            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        return enrollingFacade.updateVisibilityLevel(req);
    }


    /**
     * 预招商报名订单列表
     */
    @GetMapping(value = "/admin/enrolling_activity/view_enrollments_orders")
    public ResponseModel enrollmentsOrders(EnrollingReq.activitiesListReq req) {


        return enrollingFacade.enrollmentsOrders(req);
    }


    /**
     * 佣金订单列表
     */
    @RequiresPermissions("yzsgl_dywgl:commission_order_list")
    @GetMapping(value = "/admin/enrolling_activity/commission_orders")
    public ResponseModel commissionOrders(EnrollingReq.activitiesListReq req) {


        return enrollingFacade.commissionOrders(req);
    }

    /**
     * 结束活动
     */
    @PostMapping(value = "/admin/enrolling_activity/finish_activity")
    public ResponseModel getAllCities(@RequestBody EnrollingReq.activityIdReq req) {

        return enrollingFacade.finishActivity(req);
    }



    /**
     * 审核列表
     */
    @RequiresPermissions("yzsgl_dywgl:audit_list")
    @GetMapping(value = "/admin/enrolling_activity/audit_list")
    public ResponseModel auditList(EnrollingReq.activitiesListReq req) {
        //参数校验

        return enrollingFacade.getAuditList(req);
    }

    /**commission_order_list
     * 预招商活动审核
     */
    @PostMapping(value = "/admin/enrolling_activity/audit_enrolling_activities")
    public ResponseModel auditEnrollingActivities(@RequestBody EnrollingReq.auditEnrolling req) {
        //参数校验
        staffFacade.saveEnrollingActivityOperationRecord(loadCurLoginId(),"预招商活动审核",Integer.parseInt( req.getActivityId()));
        req.setOperateId(String.valueOf(loadCurLoginId()));
        return enrollingFacade.auditEnrollingActivities(req);
    }



    /**
     * 抵押物预招商生成佣金订单
     */
    @PostMapping(value = "/admin/enrolling_activity/save_commission_order")
    public ResponseModel saveCommissionOrder(@RequestBody EnrollingReq.saveCommissionOrder req) {
        //参数校验

        return enrollingFacade.saveCommissionOrder(req);
    }


    /**
     * 抵押物保证金操作
     */
    @PostMapping(value = "/admin/enrolling_activity/margin_operation")
    public ResponseModel marginOperation(@RequestBody EnrollingReq.marginOperation req) {
        //参数校验

        return enrollingFacade.marginOperation(req);
    }

    /**
     * 获取标的信息
     */
    @GetMapping("/admin/enrolling_activity/get_matter_info")
    public ResponseModel getMatterInfo(EnrollingReq.activityIdTypeReq req) {


        return enrollingWebFacade.getMatterInfo(req);
    }

    /**
     * 后台修改获取活动信息
     */
    @PostMapping("/admin/enrolling_activity/get_enrolling_detail")
    public ResponseModel getEnrollingDetail(@RequestBody EnrollingReq.activityIdReq req) {


        return enrollingWebFacade.getEnrollingDetail(req);
    }


    /**
     * 主站后台进行活动数据修改
     * @param req
     * @return
     */
    @PostMapping("/admin/enrolling_activity/update_activity")
    public ResponseModel updateActivity(@RequestBody EnrollingReq.saveActivityReq req) {
        Integer loadCurLoginId = loadCurLoginId();

        if(loadCurLoginId==null){
            return ResponseModel.fail("请先登录！");
        }
        req.setUpdateType("1");
        staffFacade.saveEnrollingActivityOperationRecord(loadCurLoginId(),"修改数据预招商活动",Integer.parseInt( req.getActivityId()));
        req.setOperateId(String.valueOf(loadCurLoginId));
        return enrollingWebFacade.updateActivity(req);
    }



    /**
     * 获取预招商项目经理
     * @param req
     * @return
     */
    @GetMapping("/admin/enrolling_activity/get_project_manager")
    public ResponseModel getProjectManager(EnrollingReq.activityIdReq req) {
        Integer loadCurLoginId = loadCurLoginId();

        if(loadCurLoginId==null){
            return ResponseModel.fail("请先登录！");
        }

        return enrollingWebFacade.getProjectManager(req);
    }



    /**
     * 项目经理操作
     * @param req
     * @return
     */
    @PostMapping("/admin/enrolling_activity/project_manager_operation")
    public ResponseModel projectManagerOperation(@RequestBody EnrollingReq.projectManagerOperation req) {
        Integer loadCurLoginId = loadCurLoginId();

        if(loadCurLoginId==null){
            return ResponseModel.fail("请先登录！");
        }

        return enrollingWebFacade.projectManagerOperation(req);
    }


    /**
     * 活动数据下载
     */
    @GetMapping(value = "/admin/enrolling_activity/download")
    public void download(EnrollingReq.activitiesListReq req, HttpServletRequest request, HttpServletResponse response) {
        req.setNoThirdPartyStatus(0);
        req.setPage(1);
        req.setPerPage(5000);

        List<Map<String, Object>> totalMaps = new ArrayList<>();
        PageInfo pageInfo = enrollingFacade.getExportEnrollingActivitiesInfo(req);
        totalMaps.addAll(pageInfo.getList());

        if(pageInfo.getTotal() > req.getPerPage()){
            for (int i = 2; i < pageInfo.getTotal()/ req.getPerPage() + 2; i++) {
                req.setPage(i);
                PageInfo page = enrollingFacade.getExportEnrollingActivitiesInfo(req);
                totalMaps.addAll(page.getList());
            }
        }
        EnrollingExcelUtils.buildExcel(req.getType(),0,totalMaps,request,response);
    }



    /**
     * 处置服务 资金服务商操作
     * @param req
     * @return
     */
    @PostMapping("/admin/enrolling_activity/fund_disposal_operation")
    public ResponseModel fundDisposalOperation(@RequestBody EnrollingReq.fundDisposalOperation req) {
        Integer loadCurLoginId = loadCurLoginId();

        if(loadCurLoginId==null){
            return ResponseModel.fail("请先登录！");
        }

        if(StringUtils.isEmpty(req.getActivityId())){
            return ResponseModel.fail(ApiCallResult.EMPTY);

        }

        return enrollingWebFacade.fundDisposalOperation(req);
    }
}
