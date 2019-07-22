
package com._360pai.core.dao.applet.mapper;

import com._360pai.core.facade.applet.vo.ShopEnrollingAndAuctionListVO;
import com._360pai.core.facade.applet.vo.ShopEnrollingListVO;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.applet.TAppletEnrollingMapCondition;
import com._360pai.core.model.applet.TAppletEnrollingMap;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TAppletEnrollingMap数据层的基础操作</p>
 * @ClassName: TAppletEnrollingMapMapper
 * @Description: TAppletEnrollingMap数据层的基础操作
 * @author Generator
 * @date 2019年02月25日 15时52分17秒
 */
@Mapper
public interface TAppletEnrollingMapMapper extends BaseMapper<TAppletEnrollingMap, TAppletEnrollingMapCondition>{

    List<ShopEnrollingListVO> getShopWebEnrollingList(ShopEnrollingSearchDto params);

    List<ShopEnrollingListVO> getShopEnrollingList(ShopEnrollingSearchDto params);

    void saveBatchAppletEnrollingList(List<TAppletEnrollingMap> saveTAppletEnrollingMaps);

    void updateBatchAppletEnrollingList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps);

    void deleteEnrollingHomePage(@Param("shopId") String shopId);

    void setEnrollingHomePage(List<TAppletEnrollingMap> tAppletEnrollingMaps);

    List<ShopEnrollingAndAuctionListVO> getSearchAuctionAndEnrollingList(ShopEnrollingSearchDto enrollingParams);

    List<String> getHotPushIds(ShopEnrollingSearchDto params);

    int countProduct(@Param("shopId") Integer shopId);
}
