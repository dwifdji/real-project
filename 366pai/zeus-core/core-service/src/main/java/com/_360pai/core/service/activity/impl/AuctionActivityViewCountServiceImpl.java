package com._360pai.core.service.activity.impl;

import com._360pai.core.condition.activity.AuctionActivityViewCountCondition;
import com._360pai.core.dao.activity.AuctionActivityViewCountDao;
import com._360pai.core.model.activity.AuctionActivityViewCount;
import com._360pai.core.service.activity.AuctionActivityViewCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionActivityViewCountServiceImpl implements AuctionActivityViewCountService {

    @Autowired
    private AuctionActivityViewCountDao auctionActivityViewCountDao;

    @Override
    public AuctionActivityViewCount getByActivityId(Integer activityId) {
        AuctionActivityViewCountCondition condition = new AuctionActivityViewCountCondition();
        condition.setActivityId(activityId);
        return auctionActivityViewCountDao.selectFirst(condition);
    }

    @Override
    public int updateViewCountByActivityId(Integer activityId, Integer incrementViewCountNum) {
        // 影响数据的条数
        int                               result;
        AuctionActivityViewCountCondition countCondition = new AuctionActivityViewCountCondition();
        countCondition.setActivityId(activityId);
        AuctionActivityViewCount auctionActivityViewCount = auctionActivityViewCountDao.selectOneResult(countCondition);
        if (auctionActivityViewCount == null) {
            AuctionActivityViewCount viewCount = new AuctionActivityViewCount();
            viewCount.setActivityId(activityId);
            viewCount.setViewCount(incrementViewCountNum);
            result = auctionActivityViewCountDao.insert(viewCount);
        } else {
            auctionActivityViewCount.setViewCount(auctionActivityViewCount.getViewCount() + incrementViewCountNum);
            result = auctionActivityViewCountDao.updateById(auctionActivityViewCount);
        }
        return result;
    }

    @Override
    public Integer viewCount(Integer activityId) {
        return auctionActivityViewCountDao.viewCount(activityId);
    }
}