package com._360pai.core.facade.enrolling;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com._360pai.core.facade.enrolling.req.EnrollingReq.*;

/**
 * 描述：预招商主站FACADE
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/8/20 11:11
 */
public interface EnrollingWebFacade {

	/**
	 *
	 *查询预招商列表
	 * @param  req
	 */
	ResponseModel getEnrollingActivityList(EnrollingReq.searchReq req);


	/**
	 *
	 *获取预招商基本信息
	 * @param  req
	 */
	ResponseModel getBaseInfo(EnrollingReq.activityIdTypeReq req);


	/**
	 *
	 *获取预招商排行榜列表
	 *
	 */
	ResponseModel getActivityRanking();

	/**
	 *
	 * 根据tabId 获取预招商信息
	 *
	 */
	ResponseModel getActivityByTabId(EnrollingReq.tabReq req);


	/**
	 *
	 *获取预招商详情接口
	 * @param  req
	 */
	ResponseModel getDetail(EnrollingReq.activityIdReq req);

	/**
	 *
	 *获取预招商提醒列表
	 * @param  req
	 */
	ResponseModel remindList(activityIdTypeReq req);


	/**
	 *
	 *获取预招商关注列表
	 * @param  req
	 */
	ResponseModel focusList(activitiesListReq req);


	/**
	 *
	 *预招商报名订单列表
	 * @param  req
	 */
	ResponseModel enrollmentsOrders(EnrollingReq.activitiesListReq req);


	/**
	 * 个人中心我的预招商列表
	 */
	ResponseModel myEnrollingActivities(activitiesListReq req);


	/**
	 * 设置预招商提醒
	 */
	ResponseModel activityRemind(EnrollingReq.activityIdTypeReq req);


	/**
	 * 预招商报名
	 */
	ResponseModel activityApply(EnrollingReq.activityIdTypeReq req);


	/**
	 * 预招商关注
	 */
	ResponseModel activityFocus(EnrollingReq.activityIdTypeReq req);



	/**
	 * 获取全网连拍列表
	 */
	ResponseModel getActivityJoint(activityIdReq req);


	/**
	 * 动态获取预招商模板
	 */
	ResponseModel getEAannouncementById(activityAnnouncement activityAnnouncement);

	/**
	 * 个人中心我报名的预测招商列表
	 */
	ResponseModel myApplyActivity(activityIdTypeReq req);

	/**
	 * 主页不同类型列表页预招商列表
	 */
	ResponseModel getAllTypeActivity(activitiesListReq req);

	/**
	 * 主页分页查询预招商列表
	 * @param req
	 * @return
	 */
	ResponseModel searchHomeActivity(searchReq req);

	/**
	 * 个人中心预招商详细信息
	 * @param req
	 * @return
	 */
	ResponseModel getMyActivityInfo(activityIdReq req);

	/**
	 * 查询封装全部查询
	 * @return
	 */
	ResponseModel getCityStatusType();


	/**
	 * 根据类型获取不同的预招商活动信息
	 * @param req
	 * @return
	 */
	ResponseModel getActivityByTab(tabReq req);


	/**
	 * 保存预招商信息
	 * @param req
	 * @return
	 */
	ResponseModel saveActivity(EnrollingReq.saveActivityReq req);
	/**
	 * 保存预招商信息--第三方录入
	 * @param req
	 * @return
	 */
	ResponseModel saveActivityFromThirdParty(EnrollingReq.saveActivityThirdPartyReq req);

	/**
	 * 取消预招商提醒
	 * @param req
	 * @return
	 */
	ResponseModel cancelMyRemind(activityIdTypeReq req);


	/**
	 * 取消关注
	 * @param req
	 * @return
	 */
	ResponseModel cancelMyFocus(activityIdTypeReq req);


	/**
	 * 取个人中心-查看活动报名信息列表
	 * @param req
	 * @return
	 */
	ResponseModel applyActivityList(activityIdReq req);


	/**
	 * 取个人中心-查看活动报名信息列表
	 * @param req
	 * @return
	 */
	ResponseModel saveProgress(EnrollingReq.saveProgress req);


	/**
	 * 个人中心-抵押物预招商发布招商结果
	 * @param req
	 * @return
	 */
	ResponseModel saveResult(EnrollingReq.saveResult req);


	/**
	 * 个人中心-抵押物预招商发布招商结果
	 * @param req
	 * @return
	 */
	ResponseModel resultInfo(EnrollingReq.activityIdTypeReq req);


	/**
	 * 个人中心-抵押物预招商支付佣金
	 * @param req
	 * @return
	 */
	ResponseModel payCommission(EnrollingReq.payCommission req);



	/**
	 * 个人中心-抵押物查看进度
	 * @param req
	 * @return
	 */
	ResponseModel getProgressList(EnrollingReq.activityIdReq req);




	/**
	 * 个人中心-撤销我的预招商活动
	 * @param req
	 * @return
	 */
	ResponseModel backOutActivity(EnrollingReq.activityIdReq req);


	/**
	 * 个人中心-签署委托协议
	 * @param req
	 * @return
	 */
	ResponseModel signContract(EnrollingReq.activityIdTypeReq req);

	/**
	 * 批量取消关注
	 * @param req
	 * @return
	 */
	ResponseModel cancelMyFocusList(String[] req, Integer partyId, Integer accountId);



	/**
	 * 个人中心 - 预招商详情
	 * @return
	 */
	ResponseModel myActivityInfo(EnrollingReq.activityIdTypeReq req);


	/**
	 * 获取标的信息
	 * @return
	 */
	ResponseModel getMatterInfo(EnrollingReq.activityIdTypeReq req);



	/**
	 *
	 * @return
	 */
	ResponseModel signCallBack(EnrollingReq.signCallBack req);



	void updateActivityStatus(Integer activityId);



	/**
	 * 获取支付订单的状态
	 * @return
	 */
	ResponseModel getSubmitOrderStatus(EnrollingReq.submitOrder req);


	/**
	 * j
	 * @return
	 */
	void submitOrderCallBack(EnrollingReq.submitOrder req);


	/**
	 * 修改的时候获取活动详情
	 * @return
	 */
	ResponseModel getEnrollingDetail(EnrollingReq.activityIdReq req);






	/**
	 * 修改预招商活动
	 * @param req
	 * @return
	 */
	ResponseModel updateActivity(saveActivityReq req);

	ResponseModel updateActivityThirdParty(saveActivityThirdPartyReq req);
	/**
	 * 获取模板预招商填充字段
	 * @param req
	 * @return
	 */
	ResponseModel getActivityModelInfo(activityIdReq req);


	/**
	 * 获取关注预招商总数统计
	 * @param partyPrimaryId
	 * @return
	 */
	ResponseModel getFoucseCount(Integer partyPrimaryId, Integer accountId);



	/**
	 * 登录用户 分享插入数据
	 */
	ResponseModel saveShareInfo(EnrollingReq.activityIdTypeReq req);


	/**
	 * 项目经理操作
	 */
	ResponseModel projectManagerOperation(EnrollingReq.projectManagerOperation req);



	/**
	 * 获取项目经理信息
	 */
	ResponseModel getProjectManager(EnrollingReq.activityIdReq req);

	/**
	 * 预招商活动即将结束短信提醒
	 * @param noticeModelId
	 */
	void sentNoticeMessage(Integer noticeModelId);



	/**
	 * 保存上拍协议
	 * @param req
	 */
	ResponseModel saveProof(EnrollingReq.saveProofReq req);

	/**
	 *
	 * @return
	 */
	ResponseModel initEnrollingActivityEndTime(EnrollingReq.activityEndTime req);


	/**
	 * 微信支付
	 */
	ResponseModel wxPay(EnrollingReq.activityIdTypeReq req);


	ResponseModel getThirdExtraInfo(String activityId);

	ResponseModel getShareInfo(ActivityReq.ActivityId req);


	ResponseModel oldDataAddBusTypeName();


	ResponseModel fundDisposalOperation(EnrollingReq.fundDisposalOperation req);
}
