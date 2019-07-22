package com._360pai.core.service.enrolling;


import com._360pai.core.dto.enrolling.ActivityPersionDto;
import com._360pai.core.dto.enrolling.ActivityTabDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.dto.enrolling.PageReqDto;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.agencyUpdateReq;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.AgencyActivityDetailVO;
import com._360pai.core.vo.enrolling.EnrollingActivityVo;
import com._360pai.core.vo.enrolling.EnrollingContractParamVO;
import com._360pai.core.vo.enrolling.web.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface EnrollingActivityService{

    /**
     *
     *获取预招商列表
     * @param
     * @param
      */
    PageInfo getEnrollingActivityList(EnrollingListReqDto params);
    
    

    /**
     *
     *获取预招商列表
     * @param
     * @param
      */
    PageInfo myEnrollingActivities(EnrollingListReqDto params);



    /**
     *
     *根据id 获取预招商信息
     * @param
     * @param
     */
    EnrollingDetailInfoVo getEnrollingActivityBaseInfo(String  id);


    /**
     *
     * 根据id 获取预招商信息
     * @param
     * @param
     */
    EnrollingActivity getEnrollingActivityById(String  id);


    int updateEnrollingActivityById(EnrollingActivity activity);
    
    /**
     * 获取机构后台平台预招商活动列表
	 *
     * @param params
     * @return
     */
	PageInfo getAvailableEnrollingActivities(EnrollingListReqDto params);

	/**
	 * 根据个人id获取我关注的预招商列表
	 * @param params
	 * @return
	 */
	PageInfo focusList(EnrollingListReqDto params);
	
	/**
	 * 获取个人中心预招商信息提醒列表
	 * @param params
	 * @return
	 */
	PageInfo remindList(ActivityPersionDto params);


	/**
	 * 根据tabId 获取预招商列表信息
	 * @param
	 * @return
	 */
	PageInfo getActivityByTabId(String tabId);


	/**
	 * 根据partyId 获取我报名的预招商列表
	 * @param
	 * @return
	 */
	PageInfo myApplyActivity(EnrollingListReqDto params);


	/**
	 * 分页获取不同类型预招商活动列表
	 * @param
	 * @return
	 */
	PageInfo getAllTypeActivity(PageReqDto params);

	/**
	 * 分页查询某个类型的预招商活动列表
	 * @param
	 * @return
	 */
	PageInfo searchHomeActivity(EnrollingListReqDto params);


	/**
	 * 查询所有的预测招商状态
	 * @return
	 */
	List<EnrollingStatusVO> getAllActivityStatus();



	/**
	 * 获取预招商查询列表信息
	 * @param
	 * @return
	 */
	PageInfo getWebSearchActivity(EnrollingListReqDto params);


	/**
	 * 根据tabId获取所有不同的预招商列表数据
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> getActivityByTab(ActivityTabDto params);


	/**
	 * 获取排行榜具体列表
	 * @param params
	 * @return
	 */
	PageInfo leaderboardEnrolling(EnrollingListReqDto params);



	/**
	 * 保存预招商信息
	 *
	 */
	int saveActivity(EnrollingActivity params);



	PageInfo getActivityJoint(String activityId,Integer page ,Integer perPage);



	//获取签章内容
	EnrollingSignVO getSignInfo(EnrollingListReqDto dto);



	Integer updateAgencyActivity(agencyUpdateReq agencyUpdateReq);


	/**
	 * 机构后台获取详情
	 * @param activityId
	 * @return
	 */
	AgencyActivityDetailVO getAgencyActivityInfo(String activityId);



	/**
	 * 获取生成签章合同的参数
	 * @param activityId
	 * @return
	 */
    EnrollingContractParamVO getEnrollingContractParam(String activityId);


    /**
     * 根据名称对预招商名称做唯一校验
     * @param
     * @return
     */
	EnrollingActivity getActivityByName(String name);


	/**
	 * 修改预招商活动信息
	 * @param params
	 * @return
	 */
	Integer updateActivity(EnrollingActivity params);


	/**
	 * 获取公告实体
	 * @param activityId
	 * @return
	 */
	EnrollingAnnouncementVO getActivityModelInfo(String activityId);


	/**
	 * 获取关注数量
	 * @param partyPrimaryId
	 * @return
	 */
	Integer getFoucseCount(Integer partyPrimaryId, Integer accountId);


	/**
	 * 批量更新 关注数量

	 */
	void batchUpdateFocusNum(List<String> activityIdList);

	/**
	 *  根据状态查询所有的预招商信息
	 * @return
	 */
	void initEnrollingActivityEndTime(EnrollingReq.activityEndTime req);

	/**
	 * 预招商导出数据
	 * @return
	 * @param req
	 */
    List<EnrollingActivityVo> getExportEnrollingActivities(EnrollingListReqDto req);

	/**
	 * 根据accountId获取已经浏览过的活动
	 * @param
	 * @return
	 */
	PageInfo getActivitysByAccountId(AcctReq.ViewRecordRequest viewRecordRequest);

	Map<String, Object> getShareInfo(ActivityReq.ActivityId req);


	/**
	 * 预招商导出数据
	 * @return
	 * @param req
	 */
	PageInfo getExportEnrollingActivitiesInfo(EnrollingListReqDto req);
}