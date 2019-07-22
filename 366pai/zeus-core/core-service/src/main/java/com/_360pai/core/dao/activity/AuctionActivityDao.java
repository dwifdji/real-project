
package com._360pai.core.dao.activity;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.activity.req.ActivityJointReq;
import com._360pai.core.facade.activity.vo.AgencyUnionDataVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.AuctionJointVo;
import com._360pai.core.facade.activity.vo.SearchAuctionActivityVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.JointActivityMap;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionActivity的基础操作Dao</p>
 * @ClassName: AuctionActivityDao
 * @Description: AuctionActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AuctionActivityDao extends BaseDao<AuctionActivity,AuctionActivityCondition>{

    String specialDetail(Integer activityId);

    List<Map> activityList(String assetPropertyName, String activityName, String agencyName);

    PageInfo getAgencyPortalActivityList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getAvailablePlatformActivityList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getAuctionActivityList(int page, int perPage, Map<String, Object> params, String orderBy);

    AuctionActivity getById(Integer activityId);

    PageInfo search(int page, int perPage, Map<String, Object> params);

    int updateStatus(Integer activityId, ActivityEnum.Status status);

    int updateStatus(Integer activityId, ActivityEnum.Status status, Integer operatorId);

    Map<String, Object> activityDetail(Integer activityId);

    AuctionActivity getLatestByAssetId(Integer assetId);

    List<AuctionActivityVo> getRankActivity(Map<String, Object> params);

    String getCategoryName(@Param("activityId") Integer activityId);

    int bindStaff(Integer activityId, Integer staffId);

    int unbindStaff(Integer activityId);

    List<Integer> getNeedRepairList();

    PageInfo getUnionDataList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getAgencyUnionDataList(int page, int perPage, Map<String, Object> params, String orderBy);

    int addParticipantNumber(Integer activityId);

    List<Map<String, Object>> exportActivityList();

    PageInfo exportActivityList(int page, int perPage);

    List<AuctionActivity> getAheadAndBeginAuction();

    int countPlatformActivityNum();


    List<AuctionJointVo> getAuctionJointList(ActivityJointReq req);


    void  batchSaveJoint(List<JointActivityMap> jointActivityMapList);


    void  batchUpdateJoint(Map jointActivityMapList);


    List<JointActivityMap> getJointActivityMapList(Map map);


    List<SearchAuctionActivityVo> getJointSearchList (Map<String, Object> params);



    List<AgencyUnionDataVo>  getAgencyJointList (Map<String, Object> params);


    List<AgencyUnionDataVo>  getSelfJointList (Map<String, Object> params);

    PageInfo getPlatformActivityListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getAuctionActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest);

    PageInfo<AuctionActivity> getListWillEndAt2Days(int page, int perPage, Map<String, Object> params);

    PageInfo<AuctionActivity> getListWillEndAtOver2Days(int page, int perPage, Map<String, Object> params);
}
