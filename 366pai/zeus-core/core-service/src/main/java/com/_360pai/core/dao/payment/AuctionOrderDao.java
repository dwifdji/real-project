
package com._360pai.core.dao.payment;

import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.payment.AuctionOrder;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionOrder的基础操作Dao</p>
 * @ClassName: AuctionOrderDao
 * @Description: AuctionOrder基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
public interface AuctionOrderDao extends BaseDao<AuctionOrder,AuctionOrderCondition>{
    PageInfo getAuctionOrderList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<Map<String,Object>> getAuctionOrders(Map<String, Object> params);

    PageInfo getShopDealCommissionListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    List<AuctionOrder> getBuyerNeedToPaidList(Integer partyId);

    String getBuyerNameByActivityId(Integer assetId);
}
