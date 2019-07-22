package com._360pai.core.service.applet;

import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletAuctionMap;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface TAppletAuctionMapService {


    List<Integer> getAppletAuctionMapList(String shopId);

    void updateAllAuciton(String shopId);

    void setHomePage(ShopReq.HomePageReq homePageReq);

    void batchDeleteStocks(ShopReq.OutOfStocks outOfStocks);

    void batchUpOfAuction(List<TAppletAuctionMap> tAppletAuctionMaps, String shopId);

    PageInfo searchShopAuctionList(ShopAuctionSearchDto params);

    TAppletAuctionMap getAppletAuctionByParams(Map<String, Object> params);

    void batchUpdateStocks(Map<String, Object> params, Integer count);

    List<String> getHotPushIds(ShopAuctionSearchDto params);

    PageInfo getSearchAuctionList(ShopAuctionSearchDto params);

    void batchSetDeleteStocks(Map<String, Object> params);


    TAppletAuctionMap getAppletAuctionByCondition(TAppletAuctionMapCondition condition);



    List<TAppletAuctionMap> getAppletAuctionList(TAppletAuctionMapCondition condition);
}
