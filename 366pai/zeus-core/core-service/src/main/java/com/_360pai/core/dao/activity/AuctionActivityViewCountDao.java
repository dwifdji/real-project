
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.AuctionActivityViewCountCondition;
import com._360pai.core.model.activity.AuctionActivityViewCount;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AuctionActivityViewCount的基础操作Dao</p>
 * @ClassName: AuctionActivityViewCountDao
 * @Description: AuctionActivityViewCount基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AuctionActivityViewCountDao extends BaseDao<AuctionActivityViewCount,AuctionActivityViewCountCondition>{
    int viewCount(Integer activityId);
}
