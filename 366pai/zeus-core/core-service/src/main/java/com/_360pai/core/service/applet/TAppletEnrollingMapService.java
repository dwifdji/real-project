package com._360pai.core.service.applet;

import com._360pai.core.facade.applet.vo.ShopEnrollingAndAuctionListVO;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletEnrollingMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface TAppletEnrollingMapService {

    PageInfo getShopWebEnrollingList(ShopEnrollingSearchDto params);

    PageInfo getShopEnrollingList(ShopEnrollingSearchDto params);

    TAppletEnrollingMap getEnrollingByShopParams(String shopId, String outOfStock, String isdel, String status);

    void saveBatchAppletEnrollingList(List<TAppletEnrollingMap> saveTAppletEnrollingMaps);

    void updateBatchAppletEnrollingList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps);

    void deleteEnrollingHomePage(String shopId);

    void setEnrollingHomePage(ShopReq.HomePageReq homePageReq);

    PageInfo getSearchAuctionAndEnrollingList(ShopEnrollingSearchDto enrollingParams);

    List<String> getHotPushIds(ShopEnrollingSearchDto params);

    Integer getShopEnrollingSize(String shopId);
}
