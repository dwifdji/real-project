
package com._360pai.core.dao.applet;

import com._360pai.core.condition.applet.TAppletEnrollingMapCondition;
import com._360pai.core.facade.applet.vo.ShopEnrollingAndAuctionListVO;
import com._360pai.core.facade.applet.vo.ShopEnrollingListVO;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletEnrollingMap;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAppletEnrollingMap的基础操作Dao</p>
 * @ClassName: TAppletEnrollingMapDao
 * @Description: TAppletEnrollingMap基础操作的Dao
 * @author Generator
 * @date 2019年02月25日 15时52分17秒
 */
public interface TAppletEnrollingMapDao extends BaseDao<TAppletEnrollingMap,TAppletEnrollingMapCondition>{

    List<ShopEnrollingListVO> getShopWebEnrollingList(ShopEnrollingSearchDto params);

    List<ShopEnrollingListVO> getShopEnrollingList(ShopEnrollingSearchDto params);

    void saveBatchAppletEnrollingList(List<TAppletEnrollingMap> saveTAppletEnrollingMaps);

    void updateBatchAppletEnrollingList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps);

    void deleteEnrollingHomePage(String shopId);

    void setEnrollingHomePage(List<TAppletEnrollingMap> tAppletEnrollingMaps);

    List<ShopEnrollingAndAuctionListVO> getSearchAuctionAndEnrollingList(ShopEnrollingSearchDto enrollingParams);

    List<String> getHotPushIds(ShopEnrollingSearchDto params);

    int countProduct(Integer shopId);
}
