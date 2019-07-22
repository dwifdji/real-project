
package com._360pai.core.dao.activity.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.facade.activity.req.ActivityJointReq;
import com._360pai.core.facade.activity.vo.AgencyUnionDataVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.AuctionJointVo;
import com._360pai.core.facade.activity.vo.SearchAuctionActivityVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.JointActivityMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionActivity数据层的基础操作</p>
 * @ClassName: AuctionActivityMapper
 * @Description: AuctionActivity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
@Mapper
public interface AuctionActivityMapper extends BaseMapper<AuctionActivity, AuctionActivityCondition>{

    String specialDetail(@Param("activityId") Integer activityId);

    List<Map> activityList(@Param("assetPropertyName") String assetPropertyName,
                           @Param("activityName") String activityName,
                           @Param("agencyName") String agencyName);

    List<AuctionActivity> getAgencyPortalActivityList(Map<String, Object> params);

    List<AuctionActivity> getAvailablePlatformActivityList(Map<String, Object> params);

    List<AuctionActivity> getAuctionActivityList(Map<String, Object> params);

    List<SearchAuctionActivityVo> search(Map<String, Object> params);

    Map<String, Object> activityDetail(@Param("activityId") Integer activityId);

    List<AuctionActivityVo> getRankActivity(Map<String, Object> params);

    String getCategoryName(@Param("activityId") Integer activityId);

    int bindStaff(@Param("activityId") Integer activityId, @Param("staffId") Integer staffId);

    int unbindStaff(@Param("activityId") Integer activityId);

    List<Integer> getNeedRepairList();

    List<AgencyUnionDataVo> getUnionDataList(Map<String, Object> params);

    List<AgencyUnionDataVo> getAgencyUnionDataList(Map<String, Object> params);

    int addParticipantNumber(@Param("activityId") Integer activityId);

    List<Map<String, Object>> exportActivityList();

    List<AuctionActivity> getAheadAndBeginAuction();

    int countPlatformActivityNum();


    List<AuctionJointVo> getAuctionJointList(ActivityJointReq req);

    void  batchSaveJoint(List<JointActivityMap> jointActivityMapList);


    void  batchUpdateJoint(Map jointActivityMapList);


    List<JointActivityMap> getJointActivityMapList(Map map);


    List<SearchAuctionActivityVo> getJointSearchList(Map<String, Object> params);


    List<AgencyUnionDataVo> getAgencyJointList(Map<String, Object> params);


    List<AgencyUnionDataVo>  getSelfJointList (Map<String, Object> params);

    List<AuctionActivity> getPlatformActivityList(Map<String, Object> params);

    List<SearchAuctionActivityVo> getAuctionActivityByAccountId(@Param("accountId")Integer accountId, @Param("partyId")Integer partyId);

    List<AuctionActivity> getListWillEndAt2Days(Map<String, Object> params);

    List<AuctionActivity> getListWillEndAtOver2Days(Map<String, Object> params);
}
