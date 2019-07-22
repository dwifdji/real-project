
package com.tzCloud.core.dao.auction;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.auction.TMapAuctionCondition;
import com.tzCloud.core.model.auction.TMapAuction;

import java.util.List;
import java.util.Map;

/**
 * <p>TMapAuction的基础操作Dao</p>
 * @ClassName: TMapAuctionDao
 * @Description: TMapAuction基础操作的Dao
 * @author Generator
 * @date 2019年06月14日 16时07分02秒
 */
public interface TMapAuctionDao extends BaseDao<TMapAuction,TMapAuctionCondition> {

    List<TMapAuction> getMapAuctionListByParam(Map<String, String> param);

}
