
package com._360pai.core.dao.activity.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.condition.activity.AuctionActivityCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.activity.mapper.AuctionActivityMapper;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.facade.activity.req.ActivityJointReq;
import com._360pai.core.facade.activity.vo.AgencyUnionDataVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.activity.vo.AuctionJointVo;
import com._360pai.core.facade.activity.vo.SearchAuctionActivityVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.activity.JointActivityMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class
AuctionActivityDaoImpl extends AbstractDaoImpl<AuctionActivity, AuctionActivityCondition, BaseMapper<AuctionActivity, AuctionActivityCondition>> implements AuctionActivityDao {

    @Resource
    private AuctionActivityMapper auctionActivityMapper;

    @Override
    protected BaseMapper<AuctionActivity, AuctionActivityCondition> daoSupport() {
        return auctionActivityMapper;
    }

    @Override
    protected AuctionActivityCondition blankCondition() {
        return new AuctionActivityCondition();
    }

    @Override
    public String specialDetail(Integer activityId) {
        return auctionActivityMapper.specialDetail(activityId);
    }

    @Override
    public List<Map> activityList(String assetPropertyName, String activityName, String agencyName) {
        return auctionActivityMapper.activityList(assetPropertyName, activityName, agencyName);
    }

    @Override
    public PageInfo getAgencyPortalActivityList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionActivity> list = auctionActivityMapper.getAgencyPortalActivityList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo getAvailablePlatformActivityList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionActivity> list = auctionActivityMapper.getAvailablePlatformActivityList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo getAuctionActivityList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionActivity> list = auctionActivityMapper.getAuctionActivityList(params);
        return new PageInfo<>(list);
    }

    @Override
    public AuctionActivity getById(Integer activityId) {
        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setId(activityId);
        List<AuctionActivity> list = auctionActivityMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public PageInfo search(int page, int perPage, Map<String, Object> params) {
        PageHelper.startPage(page, perPage);
        List<SearchAuctionActivityVo> list = auctionActivityMapper.search(params);
        for (SearchAuctionActivityVo item : list) {
            //if (StringUtils.isNotBlank(item.getAreaName())) {
            //    item.setCityName(item.getAreaName());
            //} else if (StringUtils.isNotBlank(item.getCityName())) {
            //    item.setCityName(item.getCityName());
            //} else if (StringUtils.isNotBlank(item.getProvinceName())) {
            //    item.setCityName(item.getProvinceName());
            //} else {
            //    item.setCityName("");
            //}
            if (StringUtils.isBlank(item.getCityName())) {
                if (StringUtils.isNotBlank(item.getProvinceName())) {
                    item.setCityName(item.getProvinceName());
                } else {
                    item.setCityName("");
                }
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    public int updateStatus(Integer activityId, ActivityEnum.Status status) {
        return updateStatus(activityId, status, null);
    }

    @Override
    public int updateStatus(Integer activityId, ActivityEnum.Status status, Integer operatorId) {
        AuctionActivity auctionActivity = new AuctionActivity();
        auctionActivity.setId(activityId);
        auctionActivity.setStatus(status);
        auctionActivity.setUpdatedAt(new Date());
        if (operatorId != null) {
            auctionActivity.setOperatorId(operatorId);
            auctionActivity.setOperatorAt(new Date());
        }
        return auctionActivityMapper.updateById(auctionActivity);
    }

    @Override
    public Map<String, Object> activityDetail(Integer activityId) {
        return auctionActivityMapper.activityDetail(activityId);
    }

    @Override
    public AuctionActivity getLatestByAssetId(Integer assetId) {
        AuctionActivityCondition condition = new AuctionActivityCondition();
        condition.setAssetId(assetId);
        List<AuctionActivity> list = auctionActivityMapper.selectByCondition(condition);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AuctionActivityVo> getRankActivity(Map<String, Object> params) {
        return auctionActivityMapper.getRankActivity(params);
    }

    @Override
    public String getCategoryName(Integer activityId) {
        return auctionActivityMapper.getCategoryName(activityId);
    }

    @Override
    public int bindStaff(Integer activityId, Integer staffId) {
        return auctionActivityMapper.bindStaff(activityId, staffId);
    }

    @Override
    public int unbindStaff(Integer activityId) {
        return auctionActivityMapper.unbindStaff(activityId);
    }

    @Override
    public List<Integer> getNeedRepairList() {
        return auctionActivityMapper.getNeedRepairList();
    }

    @Override
    public PageInfo getUnionDataList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AgencyUnionDataVo> list = auctionActivityMapper.getUnionDataList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo getAgencyUnionDataList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AgencyUnionDataVo> list = auctionActivityMapper.getAgencyUnionDataList(params);
        return new PageInfo<>(list);
    }

    @Override
    public int addParticipantNumber(Integer activityId) {
        return auctionActivityMapper.addParticipantNumber(activityId);
    }

    @Override
    public List<Map<String, Object>> exportActivityList() {
        return auctionActivityMapper.exportActivityList();
    }

    @Override
    public PageInfo exportActivityList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<Map<String, Object>> list = auctionActivityMapper.exportActivityList();
        return new PageInfo<>(list);
    }

    @Override
    public List<AuctionActivity> getAheadAndBeginAuction() {
        return auctionActivityMapper.getAheadAndBeginAuction();
    }

    @Override
    public int countPlatformActivityNum() {
        return auctionActivityMapper.countPlatformActivityNum();
    }


    @Override
    public List<AuctionJointVo> getAuctionJointList(ActivityJointReq req) {
        return auctionActivityMapper.getAuctionJointList(req);
    }


    @Override
    public void batchSaveJoint(List<JointActivityMap> jointActivityMapList) {
        auctionActivityMapper.batchSaveJoint(jointActivityMapList);
    }

    @Override
    public void batchUpdateJoint(Map jointActivityMapList) {
        auctionActivityMapper.batchUpdateJoint(jointActivityMapList);
    }

    @Override
    public List<JointActivityMap> getJointActivityMapList(Map map ) {
        return auctionActivityMapper.getJointActivityMapList(map);
    }

    @Override
    public List<SearchAuctionActivityVo> getJointSearchList(Map<String, Object> params) {
        return auctionActivityMapper.getJointSearchList(params);
    }

    @Override
    public List<AgencyUnionDataVo> getAgencyJointList(Map<String, Object> params) {

        return auctionActivityMapper.getAgencyJointList(params);

    }

    @Override
    public List<AgencyUnionDataVo> getSelfJointList(Map<String, Object> params) {
        return auctionActivityMapper.getSelfJointList(params);
    }

    @Override
    public PageInfo getPlatformActivityListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<AuctionActivity> list = auctionActivityMapper.getPlatformActivityList(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo getAuctionActivityByAccountId(AcctReq.ViewRecordRequest viewRecordRequest) {
        PageHelper.startPage(viewRecordRequest.getPage(), viewRecordRequest.getPerPage());
        List<SearchAuctionActivityVo> list = auctionActivityMapper.getAuctionActivityByAccountId(viewRecordRequest.getAccountId(), viewRecordRequest.getPartyId());
        return new PageInfo(list);
    }

    @Override
    public PageInfo<AuctionActivity> getListWillEndAt2Days(int page, int perPage, Map<String, Object> params) {
        PageHelper.startPage(page, perPage);
        List<AuctionActivity> list = auctionActivityMapper.getListWillEndAt2Days(params);
        return new PageInfo<>(list);
    }

    @Override
    public PageInfo<AuctionActivity> getListWillEndAtOver2Days(int page, int perPage, Map<String, Object> params) {
        PageHelper.startPage(page, perPage);
        List<AuctionActivity> list = auctionActivityMapper.getListWillEndAtOver2Days(params);
        return new PageInfo<>(list);
    }
}
