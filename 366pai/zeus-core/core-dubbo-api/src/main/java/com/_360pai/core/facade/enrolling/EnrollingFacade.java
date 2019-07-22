package com._360pai.core.facade.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.activitiesListReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.agencyUpdateReq;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * 描述：预招商Facade接口
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:11
 */
public interface EnrollingFacade {

    /**
     *
     *查询预招商列表
     * @param  req
     */
    ResponseModel getEnrollingActivityList(EnrollingReq.activitiesListReq req);


    /**
     *
     *获取预招商基本信息
     * @param  req
     */
    ResponseModel getBaseInfo(EnrollingReq.activityIdReq req);

    /**
     *
     *获取预招商详情接口
     * @param  req
     */
    ResponseModel getDetail(EnrollingReq.activityIdReq req);


    /**
     *
     *获取预招商报名列表
     * @param  req
     */
    ResponseModel enrollingOrders(EnrollingReq.activityIdReq req);


    /**
     *
     *获取预招商提醒列表
     * @param  req
     */
    ResponseModel notifiedList(EnrollingReq.activityIdReq req);


    /**
     *
     *获取预招商提醒列表
     * @param  req
     */
    ResponseModel shareList(EnrollingReq.activityIdReq req);


    /**
     *
     *获取预招商关注列表
     * @param  req
     */
    ResponseModel focusList(EnrollingReq.activityIdReq req);


    /**
     *
     *获取预招商进展列表
     * @param  req
     */
    ResponseModel progressRecords(EnrollingReq.activityIdReq req);

    /**
     *
     *获取预招商结果信息
     * @param  req
     */
    ResponseModel result(EnrollingReq.activityIdReq req);


    /**
     *
     *编辑预招商详情接口
     * @param  req
     */
    ResponseModel updateDetail(EnrollingReq.updateDetailReq req);


    /**
     *
     *编辑预招商可见等级
     * @param  req
     */
    ResponseModel updateVisibilityLevel(EnrollingReq.updateVisibilityLevelReq req);


    /**
     *
     *预招商报名订单列表
     * @param  req
     */
    ResponseModel enrollmentsOrders(EnrollingReq.activitiesListReq req);

    /**
     *
     *佣金订单列表
     * @param  req
     */
    ResponseModel commissionOrders(EnrollingReq.activitiesListReq req);


    /**
     *
     *获取所以的城市信息
     */
    ResponseModel getAllCities();

    /**
     *
     *结束活动
     */
    ResponseModel finishActivity(EnrollingReq.activityIdReq req);


    /**
     *
     *审核列表
     */
    ResponseModel getAuditList(EnrollingReq.activitiesListReq req);

    /**
     * 机构后台平台预招商列表
     *
     */
	ResponseModel getAvailableEnrollingActivities(activitiesListReq req);


    /**
     * 预招商审核
     *
     */
    ResponseModel auditEnrollingActivities(EnrollingReq.auditEnrolling req);


    /**
     * 保证金操作
     *
     */
    ResponseModel marginOperation(EnrollingReq.marginOperation req);


    /**
     * 生成佣金支付订单
     *
     */
    ResponseModel saveCommissionOrder(EnrollingReq.saveCommissionOrder req); 	
    
    /**
     * 机构预招商审核
     *
     */
	ResponseModel updateAgencyActivity(agencyUpdateReq agencyUpdateReq);



    /**
     * 预招商佣金支付回调
     *
     */
    ResponseModel payCommissionCallBack(EnrollingReq.payCommissionCallBack payCommissionCallBack);

    /**
     * 获取所有预招商列表信息
     * @param req
     * @return
     */
    List<Map<String, Object>> getExportEnrollingActivities(activitiesListReq req);



    /**
     * 获取所有预招商列表信息
     * @param req
     * @return
     */
    PageInfo getExportEnrollingActivitiesInfo(activitiesListReq req);

}
