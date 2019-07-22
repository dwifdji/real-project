
package com._360pai.core.dao.applet;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.facade.applet.vo.AppletSearchAuctionActivityVo;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.model.applet.TAppletAuctionMap;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TAppletAuctionMap的基础操作Dao</p>
 * @ClassName: TAppletAuctionMapDao
 * @Description: TAppletAuctionMap基础操作的Dao
 * @author Generator
 * @date 2018年11月22日 14时40分30秒
 */
public interface TAppletAuctionMapDao extends BaseDao<TAppletAuctionMap,TAppletAuctionMapCondition>{

    List<Integer> getFilterIds(TAppletAuctionMapCondition tAppletAuctionMapCondition);

    void updateAllAuciton(String shopId);

    void batchDeleteStocks(Map<String, Object> params);

    void batchUpOfAuction(List<TAppletAuctionMap> tAppletAuctionMaps);

    PageInfo getActivityListByPage(int page, int perPage, Map<String, Object> params, String orderBy);

    List<AppletSearchAuctionActivityVo> searchShopAuctionList(ShopAuctionSearchDto req);


    void batchUpdateAuctionMap(List<TAppletAuctionMap> tAppletAuctionMaps);

    void batchUpdateStocks(Map<String, Object> params);

    List<String> getHotPushIds(ShopAuctionSearchDto params);


    AuctionDetailVo getAppletAuctionDetail(Map<String, Object> params);


    List<AppletSearchAuctionActivityVo> searchWebShopAuctionList(ShopAuctionSearchDto req);

    void batchSetDeleteStocks(Map<String, Object> params);

    int countProduct(Integer shopId);
}
