package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.ShopEnum;
import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.dao.applet.TAppletAuctionMapDao;
import com._360pai.core.dao.applet.TAppletShopDao;
import com._360pai.core.dao.assistant.DepositDao;
import com._360pai.core.facade.applet.vo.AppletSearchAuctionActivityVo;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletAuctionMap;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.service.applet.TAppletAuctionMapService;
import com._360pai.core.utils.RespConvertUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TAppletAuctionMapServiceImpl implements TAppletAuctionMapService {

    @Autowired
    private TAppletAuctionMapDao tAppletAuctionMapDao;
    @Autowired
    private DepositDao depositDao;
    @Autowired
    private TAppletShopDao tAppletShopDao;

    @Override
    public List<Integer> getAppletAuctionMapList(String shopId) {
        TAppletAuctionMapCondition tAppletAuctionMapCondition = new TAppletAuctionMapCondition();
        tAppletAuctionMapCondition.setShopId(shopId);

        List<Integer> auctionIds = tAppletAuctionMapDao.getFilterIds(tAppletAuctionMapCondition);
        
        return auctionIds;
    }

    @Override
    public void updateAllAuciton(String shopId) {
        tAppletAuctionMapDao.updateAllAuciton(shopId);
    }

    @Override
    public void setHomePage(ShopReq.HomePageReq homePageReq) {

        List<TAppletAuctionMap> updateList = new ArrayList<>();

        for(int i = 0; i <  homePageReq.getHomePageArray().length; i++) {
            String auctionId = homePageReq.getHomePageArray()[i];

            TAppletAuctionMapCondition tAppletAuctionMapCondition = new TAppletAuctionMapCondition();
            tAppletAuctionMapCondition.setAuctionId(Integer.parseInt(auctionId));
            tAppletAuctionMapCondition.setShopId(homePageReq.getShopId());

            TAppletAuctionMap map = tAppletAuctionMapDao.selectFirst(tAppletAuctionMapCondition);
            map.setPushDesc(homePageReq.getHomePageArray().length - i);
            map.setUpdateTime(DateUtil.getSysTime());
            updateList.add(map);
        }
        tAppletAuctionMapDao.batchUpdateAuctionMap(updateList);
    }

    @Override
    @Transactional
    public void batchDeleteStocks(ShopReq.OutOfStocks outOfStocks) {

        Map<String, Object> params = new HashMap<>();
        params.put("outOfStocks", outOfStocks.getOutOfStocks());
        params.put("shopId", outOfStocks.getShopId());

        if("1".equals(outOfStocks.getDeleteFlag())) {
            tAppletAuctionMapDao.batchSetDeleteStocks(params);
        }else{
            tAppletAuctionMapDao.batchDeleteStocks(params);
        }

    }


    @Override
    public void batchUpOfAuction(List<TAppletAuctionMap> tAppletAuctionMaps, String shopId) {

        tAppletAuctionMapDao.batchUpOfAuction(tAppletAuctionMaps);
    }

    @Override
    public PageInfo searchShopAuctionList(ShopAuctionSearchDto req) {

        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<AppletSearchAuctionActivityVo> list;
        if("4".equals(req.getCategoryId())){
            req.setCategoryId("16");
        }
        //shopID 为 1 时为平台的小店 查询全部拍品
        if("1".equals(req.getShopId())){
            list = tAppletAuctionMapDao.searchWebShopAuctionList(req);
        }else{
             list = tAppletAuctionMapDao.searchShopAuctionList(req);
        }

        if(list != null && list.size() > 0) {
            getAppletSearchAuctionList(list);
        }

        return new PageInfo<>(list);
    }

    private void getAppletSearchAuctionList(List<AppletSearchAuctionActivityVo> list) {
        for(AppletSearchAuctionActivityVo vo:list){
            ShopEnum.NewOnlineStatus onlineStatus = RespConvertUtil.getOnlineStatus(vo.getStatus(), DateUtil.strToDateLong(vo.getBeginAt()));
            vo.setStatus("FAILURE");
            vo.setStatusStr("失效状态");
            if(onlineStatus!=null){
                vo.setStatus(onlineStatus.getKey());
                vo.setStatusStr(onlineStatus.getValue());
            }
            vo.setModeStr(ActivityEnum.ActivityMode.getKeyByValue(vo.getMode()));
            vo.setBeginTimestamp(DateUtil.strToDateLong(vo.getBeginAt()).getTime());
            vo.setEndTimestamp(DateUtil.strToDateLong(vo.getEndAt()).getTime());
            vo.setCategoryName(getCategoryName(vo.getAssetCategoryGroupId()));
            vo.setEndAt(DateUtil.formatNormDate(DateUtil.strToDateLong(vo.getEndAt())));
            if (StringUtils.isNotEmpty(vo.getIncrementAt())) {
                vo.setIncrementTimestamp(DateUtil.strToDateLong(vo.getIncrementAt()).getTime());
            }
            vo.setCurrentTimestamp(System.currentTimeMillis());
            vo.setBeginAt(DateUtil.formatNormDate(DateUtil.strToDateLong(vo.getBeginAt())));
            //一口价明标的时候 起拍价就是保留价 暗标就是无 2019-01-09 李凯绅确认
            if(ActivityEnum.ActivityMode.PUBLIC.getKey().equals(vo.getMode())){
                vo.setStartingPrice(vo.getReservePrice());
            }
             vo.setDeposit(formatStr(vo.getDeposit()));
             vo.setStartingPrice(formatStr(vo.getStartingPrice()));
             vo.setReservePrice("");//隐藏掉保留价
        }
    }

    private String formatStr(String str) {
        if(StringUtils.isEmpty(str)){
            return  "无";
        }

        if(str.contains(".00")){
            return str.substring(0, str.length() - 3);
        }

        return str;
    }


    private String getCategoryName(String assetCategoryGroupId) {
        String categoryName = "债权";
        if("16".equals(assetCategoryGroupId)){
            categoryName = "物权";
        }

        return categoryName;
    }

    @Override
    public TAppletAuctionMap getAppletAuctionByParams(Map<String, Object> params) {
        TAppletAuctionMapCondition tAppletAuctionMapCondition = new TAppletAuctionMapCondition();
        tAppletAuctionMapCondition.setShopId(params.get("shopId").toString());
        tAppletAuctionMapCondition.setAuctionId(Integer.parseInt(params.get("activityId").toString()));

        TAppletAuctionMap tAppletAuctionMap = tAppletAuctionMapDao.selectFirst(tAppletAuctionMapCondition);
        return tAppletAuctionMap;
    }

    @Override
    public void batchUpdateStocks(Map<String, Object> params, Integer upCount) {

        tAppletAuctionMapDao.batchUpdateStocks(params);
    }

    @Override
    public List<String> getHotPushIds(ShopAuctionSearchDto params) {
        return tAppletAuctionMapDao.getHotPushIds(params);
    }

    @Override
    public PageInfo getSearchAuctionList( ShopAuctionSearchDto req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<AppletSearchAuctionActivityVo> list = tAppletAuctionMapDao.searchWebShopAuctionList(req);
        if(list != null && list.size() > 0) {
            getAppletSearchAuctionList(list);
        }
        return new PageInfo<>(list);
    }

    @Override
    public void batchSetDeleteStocks(Map<String, Object> params) {
        tAppletAuctionMapDao.batchSetDeleteStocks(params);
    }


    @Override
    public TAppletAuctionMap getAppletAuctionByCondition(TAppletAuctionMapCondition condition) {
        return tAppletAuctionMapDao.selectFirst(condition);
    }

    @Override
    public List<TAppletAuctionMap> getAppletAuctionList(TAppletAuctionMapCondition condition) {
        return tAppletAuctionMapDao.selectList(condition);
    }


}
