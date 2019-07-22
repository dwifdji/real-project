package com._360pai.core.service.activity;


import com._360pai.core.model.activity.AuctionActivityViewCount;

public interface AuctionActivityViewCountService {
    AuctionActivityViewCount getByActivityId(Integer activityId);

    int updateViewCountByActivityId(Integer activityId, Integer incrementViewCountNum);

    Integer viewCount(Integer activityId);
}