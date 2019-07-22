
package com._360pai.core.dao.enrolling.mapper;

import com._360pai.arch.core.abs.BaseMapper;
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
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>EnrollingActivity数据层的基础操作</p>
 * @ClassName: EnrollingActivityMapper
 * @Description: EnrollingActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分20秒
 */
@Mapper
public interface EnrollingActivityMapper extends BaseMapper<EnrollingActivity, EnrollingActivityCondition>{

    List<EnrollingActivityVo> getEnrollingActivityList(EnrollingListReqDto params);

	List<EnrollingActivityVo> getAvailableEnrollingActivities(EnrollingListReqDto params);

	List<HomeEnrollingActivityVO> focusList(EnrollingListReqDto params);

	List<HomeEnrollingActivityVO> remindList(ActivityPersionDto params);

	List<HomeEnrollingActivityVO> myApplyActivity(EnrollingListReqDto params);

	List<HomeEnrollingActivityVO> getAllTypeActivity(PageReqDto params);

	List<MyEnrollingActivityVO> searchHomeActivity(EnrollingListReqDto params);

	List<EnrollingStatusVO> getAllActivityStatus();

	List<HomeEnrollingActivityVO> getActivityByTab(ActivityTabDto params);

	List<HomeEnrollingActivityVO> leaderboardEnrolling(EnrollingListReqDto params);

	EnrollingDetailInfoVo getEnrollingDetailInfoVo(EnrollingListReqDto params);

	List<LinkageEnrollingActivityVO> getActivityJoint(@Param("activityId")String activityId);


	EnrollingSignVO getSignInfo(EnrollingListReqDto params);


	List<HomeEnrollingActivityVO> myEnrollingActivities(EnrollingListReqDto params);

	AgencyActivityDetailVO getAgencyActivityInfo(String activityId);

	EnrollingContractParamVO getEnrollingContractParam(String activityId);

	EnrollingActivity getActivityByName(@Param("name")String name);

	EnrollingAnnouncementVO getActivityModelInfo(@Param("activityId")String activityId);

	Integer getFoucseCount(@Param("partyId")Integer partyId, @Param("accountId") Integer accountId);


	void batchUpdateFocusNum(@Param("activityIdList")List<String> activityIdList);

	List<MyEnrollingActivityVO> getActivitysByAccountId(@Param("accountId")Integer accountId, @Param("partyId")Integer partyId);

	int countPlatformActivityNum();

	List<EnrollingActivity> getListWillEndAt2Days(Map<String, Object> params);

	List<EnrollingActivity> getListWillEndAtOver2Days(Map<String, Object> params);
}
