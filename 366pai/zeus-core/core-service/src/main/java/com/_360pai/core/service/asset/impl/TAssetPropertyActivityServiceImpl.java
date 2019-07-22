package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.condition.asset.TAssetPropertyActivityCondition;
import com._360pai.core.dao.asset.TAssetPropertyActivityDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.asset.TAssetPropertyActivity;
import com._360pai.core.model.enrolling.EnrollingActivity;
import com._360pai.core.model.enrolling.EnrollingActivityData;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.asset.TAssetPropertyActivityService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivityServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/18 20:30
 */
@Service
public class TAssetPropertyActivityServiceImpl implements TAssetPropertyActivityService {
    @Autowired
    private TAssetPropertyActivityDao tAssetPropertyActivityDao;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;

    @Override
    public PageInfo propertySearch(int page, int perPage, Integer assetPropertyId) {
        PageHelper.startPage(page, perPage);
        List<Map> maps = tAssetPropertyActivityDao.propertySearch(assetPropertyId);
        for (Map item : maps) {
            if (ActivityServiceProviderEnum.ActivityType.Auction.getKey().equals(item.get("activityType"))) {
                String categoryName = (String) item.get("categoryName");
                if (StringUtils.isBlank(categoryName) && item.get("category").equals("-1")) {
                    item.put("categoryName", "租赁权拍卖");
                }
            }
        }
        return new PageInfo<>(maps);
    }

    @Override
    @Transactional
    public int addTAssetPropertyActivity(TAssetPropertyActivity req) {
        TAssetPropertyActivityCondition condition = new TAssetPropertyActivityCondition();
        condition.setActivityId(req.getActivityId());
        condition.setAssetPropertyId(req.getAssetPropertyId());
        condition.setActivityType(req.getActivityType());
        condition.setDelFlag(0);
        List<TAssetPropertyActivity> tAssetPropertyActivities = tAssetPropertyActivityDao.selectList(condition);
        if (!tAssetPropertyActivities.isEmpty()) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动已存在");
        }
        int insert = tAssetPropertyActivityDao.insert(req);
        if (insert <= 0) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "属性添加失败");
        }
        if (ActivityServiceProviderEnum.ActivityType.Enrolling.getKey().equals(req.getActivityType())) {
            EnrollingActivity enrollingActivity = enrollingActivityDao.selectById(req.getActivityId());
            if (enrollingActivity == null) {
                throw new BusinessException("活动查询失败");
            }
            return insert;
        } else {
            AuctionActivity auctionActivity = auctionActivityService.getById(req.getActivityId());
            if (auctionActivity == null) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动查询失败");
            }
            auctionActivity.setAssetPropertyId(req.getAssetPropertyId());
            boolean b = auctionActivityService.updateActivityById(auctionActivity);
            if (b) {
                return insert;
            } else {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动更新失败");
            }
        }

    }

    @Override
    public int editTAssetPropertyActivity(TAssetPropertyActivity req) {
        TAssetPropertyActivity assetPropertyActivity = tAssetPropertyActivityDao.selectById(req.getId());
        if (assetPropertyActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动不存在");
        }
        return tAssetPropertyActivityDao.updateById(req);
    }

    @Override
    public int deleteTAssetPropertyActivity(TAssetPropertyActivity req) {
        TAssetPropertyActivity assetPropertyActivity = tAssetPropertyActivityDao.selectById(req.getId());
        if (assetPropertyActivity == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "活动不存在");
        }
        req.setDelFlag(1);
        return tAssetPropertyActivityDao.updateById(req);
    }
}
