
package com._360pai.core.dao.applet.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.facade.applet.vo.AppletSearchAuctionActivityVo;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.applet.TAppletAuctionMap;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletAuctionMap数据层的基础操作</p>
 * @ClassName: TAppletAuctionMapMapper
 * @Description: TAppletAuctionMap数据层的基础操作
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
@Mapper
public interface TAppletAuctionMapMapper extends BaseMapper<TAppletAuctionMap, TAppletAuctionMapCondition>{

    List<Integer> getFilterIds(TAppletAuctionMapCondition tAppletAuctionMapCondition);

    void updateAllAuciton(@Param("shopId") String shopId);

    void batchDeleteStocks(Map<String, Object> params);

    void batchUpOfAuction(List<TAppletAuctionMap> tAppletAuctionMaps);

    List<AuctionActivity> getActivityListByPage(Map<String, Object> params);

    List<AppletSearchAuctionActivityVo> searchShopAuctionList(ShopAuctionSearchDto params);

    void batchUpdateAuctionMap(List<TAppletAuctionMap> tAppletAuctionMaps);

    void batchUpdateStocks(Map<String, Object> params);

    List<String> getHotPushIds(ShopAuctionSearchDto params);


    AuctionDetailVo getAppletAuctionDetail(Map<String, Object> params);

    List<AppletSearchAuctionActivityVo> searchWebShopAuctionList(ShopAuctionSearchDto req);

    void batchSetDeleteStocks(Map<String, Object> params);

    int countProduct(@Param("shopId") Integer shopId);
}
