
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.AuctionActivityShareStatsCondition;
import com._360pai.core.model.activity.AuctionActivityShareStats;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AuctionActivityShareStats的基础操作Dao</p>
 * @ClassName: AuctionActivityShareStatsDao
 * @Description: AuctionActivityShareStats基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AuctionActivityShareStatsDao extends BaseDao<AuctionActivityShareStats,AuctionActivityShareStatsCondition>{
    void createIfNotExist(Integer activityId, Integer accountId, Integer partyId);

    int activityShareCount(Integer activityId);
}
