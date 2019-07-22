
package com._360pai.core.dao.payment.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.payment.AuctionOrderCondition;
import com._360pai.core.facade.payment.vo.AuctionOrderVo;
import com._360pai.core.facade.payment.vo.ShopAuctionOrderVo;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.payment.AuctionOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>AuctionOrder数据层的基础操作</p>
 * @ClassName: AuctionOrderMapper
 * @Description: AuctionOrder数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
@Mapper
public interface AuctionOrderMapper extends BaseMapper<AuctionOrder, AuctionOrderCondition>{

    List<AuctionOrder> getAuctionOrderList(Map<String, Object> params);

    List<Map<String,Object>> getAuctionOrders(Map<String,Object> params);


    List<ShopAuctionOrderVo> getShopDealCommissionListByPage(Map<String, Object> params);

    List<AuctionOrder> getBuyerNeedToPaidList(@Param("partyId") Integer partyId);

    String getBuyerNameByActivityId(@Param("assetId")Integer assetId);
}
