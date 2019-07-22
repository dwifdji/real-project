
package com._360pai.core.dao.enrolling;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.enrolling.EnrollingActivityCondition;
import com._360pai.core.dto.enrolling.ActivityPersionDto;
import com._360pai.core.dto.enrolling.ActivityTabDto;
import com._360pai.core.dto.enrolling.EnrollingListReqDto;
import com._360pai.core.dto.enrolling.PageReqDto;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.vo.enrolling.AgencyActivityDetailVO;
import com._360pai.core.vo.enrolling.EnrollingActivityVo;
import com._360pai.core.vo.enrolling.EnrollingContractParamVO;
import com._360pai.core.vo.enrolling.web.*;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>EnrollingActivity的基础操作Dao</p>
 * @ClassName: EnrollingActivityDao
 * @Description: EnrollingActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分20秒
 */
public interface EnrollingActivityDao extends BaseDao<EnrollingActivity,EnrollingActivityCondition>{


    /**
     * 获取后台admin预招商列表
     *
     * @param  params 查询请求参数
     */
    List<EnrollingActivityVo> getEnrollingActivityList(EnrollingListReqDto params);

    /**
     * 获取机构后台平台预招商业务列表
     * @param params
     * @return
     */
	List<EnrollingActivityVo> getAvailableEnrollingActivities(EnrollingListReqDto params);

	/**
	 * 根据patyId获取某个用户关注的预招商列表
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> focusList(EnrollingListReqDto params);

	/**
	 * 根据partyId获取个人中心提醒预招商列表
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> remindList(ActivityPersionDto params);


	List<EnrollingHomeVo> getActivityByTabId(String tabId);
	
	/**
	 * 个人中心根据已partyId获取已经报名预招商列表
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> myApplyActivity(EnrollingListReqDto params);
	
	/**
	 * 首页分类查询预招商活动列表
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> getAllTypeActivity(PageReqDto params);

	/**
	 * 首页分页查询预招商活动列表
	 * @param params
	 * @return
	 */
	List<MyEnrollingActivityVO> searchHomeActivity(EnrollingListReqDto params);
	
	/**
	 * 查询所有的预招商活动状态
	 */
	List<EnrollingStatusVO> getAllActivityStatus();

	/**
	 * 根据tabId获取预招商列表信息
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> getActivityByTab(ActivityTabDto params);
	
	/**
	 * 获取具体排行榜信息
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> leaderboardEnrolling(EnrollingListReqDto params);
	
	/**
	 * 查询详情预招商活动详细信息
	 * @param params
	 * @return
	 */
	EnrollingDetailInfoVo getEnrollingDetailInfoVo(EnrollingListReqDto params);

	/**
	 * 查询全网联拍预招商列表
	 * @param
	 * @return
	 */
	List<LinkageEnrollingActivityVO> getActivityJoint(String activityId);



	/**
	 * 获取预招商签章的信息
	 * @param params
	 * @return
	 */
	EnrollingSignVO getSignInfo(EnrollingListReqDto params);


	/**
	 * 我的招商会
	 * @param params
	 * @return
	 */
	List<HomeEnrollingActivityVO> myEnrollingActivities(EnrollingListReqDto params);
	
	/**
	 * 机构后台获取预招商详情
	 * @param activityId
	 * @return
	 */
	AgencyActivityDetailVO getAgencyActivityInfo(String activityId);

	/**
	 * 获取签章的参数
	 * @param activityId
	 * @return
	 */
	EnrollingContractParamVO getEnrollingContractParam(String activityId);

	/**
	 * 根据名称查询已经存在的预招商活动信息
	 * @param name
	 * @return
	 */
	EnrollingActivity getActivityByName(String name);
	
	/**
	 * 获取预招商具体模板信息
	 * @param activityId
	 * @return
	 */
	EnrollingAnnouncementVO getActivityModelInfo(String activityId);

	/**
	 * 查询我关注的所有预招商活动总数
	 * @param partyId
	 * @return
	 */
	Integer getFoucseCount(Integer partyId, Integer accountId);


	/**
	 * 批量减少关注信息
	 */
	void batchUpdateFocusNum(List<String> activityIdList);


    List<MyEnrollingActivityVO> getActivitysByAccountId(Integer accountId, Integer partyId);

	PageInfo getListByPage(int page, int perPage);

	int countPlatformActivityNum();

	PageInfo<EnrollingActivity> getListWillEndAt2Days(int page, int perPage, Map<String, Object> params);

	PageInfo<EnrollingActivity> getListWillEndAtOver2Days(int page, int perPage, Map<String, Object> params);
}
