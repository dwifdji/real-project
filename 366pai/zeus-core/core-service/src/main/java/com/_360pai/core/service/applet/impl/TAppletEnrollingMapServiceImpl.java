package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.utils.OSUtil;
import com._360pai.core.condition.applet.TAppletEnrollingMapCondition;
import com._360pai.core.dao.applet.TAppletEnrollingMapDao;
import com._360pai.core.facade.applet.vo.ShopEnrollingAndAuctionListVO;
import com._360pai.core.facade.applet.vo.ShopEnrollingListVO;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.model.applet.TAppletEnrollingMap;
import com._360pai.core.service.applet.TAppletEnrollingMapService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.PAData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TAppletEnrollingMapServiceImpl implements TAppletEnrollingMapService {
    @Autowired
    private TAppletEnrollingMapDao tAppletEnrollingMapDao;

    @Override
    public PageInfo getShopWebEnrollingList(ShopEnrollingSearchDto params) {
        PageHelper.startPage(params.getPage(), params.getPerPage());
        List<ShopEnrollingListVO> shopWebEnrollingVOS = tAppletEnrollingMapDao.getShopWebEnrollingList(params);

        return new PageInfo(shopWebEnrollingVOS);
    }

    @Override
    public PageInfo getShopEnrollingList(ShopEnrollingSearchDto params) {
        PageHelper.startPage(params.getPage(), params.getPerPage());
        List<ShopEnrollingListVO> shopWebEnrollingVOS = tAppletEnrollingMapDao.getShopEnrollingList(params);

        return new PageInfo(shopWebEnrollingVOS);
    }

    @Override
    public TAppletEnrollingMap getEnrollingByShopParams(String shopId, String enrollingId, String isDle, String status) {
        TAppletEnrollingMapCondition params = new TAppletEnrollingMapCondition();
        params.setShopId(shopId.toString());
        params.setEnrollingId(Integer.parseInt(enrollingId));
        params.setIsDel(isDle);
        params.setStatus(status);

        return tAppletEnrollingMapDao.selectOneResult(params);
    }

    @Override
    public void saveBatchAppletEnrollingList(List<TAppletEnrollingMap> saveTAppletEnrollingMaps) {
        tAppletEnrollingMapDao.saveBatchAppletEnrollingList(saveTAppletEnrollingMaps);
    }

    @Override
    public void updateBatchAppletEnrollingList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps) {
        tAppletEnrollingMapDao.updateBatchAppletEnrollingList(updateTAppletEnrollingMaps);
    }

    @Override
    public void deleteEnrollingHomePage(String shopId) {
        tAppletEnrollingMapDao.deleteEnrollingHomePage(shopId);
    }

    @Override
    public void setEnrollingHomePage(ShopReq.HomePageReq homePageReq) {
        List<TAppletEnrollingMap> tAppletEnrollingMaps = new ArrayList<>();
        String[] homePageArray = homePageReq.getHomePageArray();
        for(int i = 0; i < homePageArray.length; i++) {


            TAppletEnrollingMapCondition tAppletEnrollingMapCondition = new TAppletEnrollingMapCondition();
            tAppletEnrollingMapCondition.setIsDel("0");
            tAppletEnrollingMapCondition.setEnrollingId(Integer.parseInt(homePageArray[i]));
            tAppletEnrollingMapCondition.setShopId(homePageReq.getShopId());

            TAppletEnrollingMap tAppletEnrollingMap = tAppletEnrollingMapDao.selectOneResult(tAppletEnrollingMapCondition);
            tAppletEnrollingMap.setPushDesc(i + 1);
            tAppletEnrollingMap.setUpdateTime(new Date());

            tAppletEnrollingMaps.add(tAppletEnrollingMap);
        }

        tAppletEnrollingMapDao.setEnrollingHomePage(tAppletEnrollingMaps);
    }

    @Override
    public PageInfo getSearchAuctionAndEnrollingList(ShopEnrollingSearchDto enrollingParams) {
        PageHelper.startPage(enrollingParams.getPage(), enrollingParams.getPerPage());

        List<ShopEnrollingAndAuctionListVO> searchAuctionAndEnrollingList =
                tAppletEnrollingMapDao.getSearchAuctionAndEnrollingList(enrollingParams);
        return new PageInfo(searchAuctionAndEnrollingList);
    }

    @Override
    public List<String> getHotPushIds(ShopEnrollingSearchDto params) {
        return tAppletEnrollingMapDao.getHotPushIds(params);
    }

    @Override
    public Integer getShopEnrollingSize(String shopId) {

        TAppletEnrollingMapCondition tAppletEnrollingMapCondition = new TAppletEnrollingMapCondition();
        tAppletEnrollingMapCondition.setShopId(shopId);
        tAppletEnrollingMapCondition.setIsDel("0");
        tAppletEnrollingMapCondition.setStatus("0");

        return  tAppletEnrollingMapDao.selectList(tAppletEnrollingMapCondition).size();
    }
}
