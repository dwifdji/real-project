
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.AuctionActivityAlbumMapCondition;
import com._360pai.core.model.activity.AuctionActivityAlbumMap;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AuctionActivityAlbumMap的基础操作Dao</p>
 * @ClassName: AuctionActivityAlbumMapDao
 * @Description: AuctionActivityAlbumMap基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AuctionActivityAlbumMapDao extends BaseDao<AuctionActivityAlbumMap,AuctionActivityAlbumMapCondition>{

    Integer getAuctionActivityCountByAlbumId(Integer id);

    int delete(AuctionActivityAlbumMap model);

    AuctionActivityAlbumMap getByAlbumIdAndActivityId(Integer albumId, Integer activityId, String activityType);
}
