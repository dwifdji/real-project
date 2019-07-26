package com._360pai.crawler.service.Impl;

import cn.wanghaomiao.seimi.struct.Response;
import com._360pai.crawler.common.util.DateUtil;
import com._360pai.crawler.common.util.HttpUtilNew;
import com._360pai.crawler.common.util.HttpUtilNewModel;
import com._360pai.crawler.dao.caa123.Caa123TransactionDataDao;
import com._360pai.crawler.dao.com.MapAuctionDao;
import com._360pai.crawler.dao.gpai.GPaiPmAreaDao;
import com._360pai.crawler.dao.gpai.GPaiPmDao;
import com._360pai.crawler.model.caa123.Caa123TranscationData;
import com._360pai.crawler.model.com.TMapAuction;
import com._360pai.crawler.model.gpai.GPaiAreaModel;
import com._360pai.crawler.model.gpai.GPaiPm;
import com._360pai.crawler.service.CaaPmService;
import com._360pai.crawler.service.GPaiPmService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class CaaPmServiceImpl implements CaaPmService {

    private static final String CAA_SOURCE = "caa";


    @Autowired
    private Caa123TransactionDataDao caa123TransactionDataDao;

    @Autowired
    private MapAuctionDao mapAuctionDao;


    @Override
    @Transactional
    public Caa123TranscationData saveData(Caa123TranscationData data) {


        Caa123TranscationData transcationData =caa123TransactionDataDao.selectOneByCode(Integer.valueOf(data.getCode()));

        if(transcationData!=null){
            data.setId(transcationData.getId());
            data.setUpdateTime(new Date());
        }

        caa123TransactionDataDao.saveAndFlush(data);

        if("done".equals(data.getStatusDesc())||"failure".equals(data.getStatusDesc())){
            saveOrUpdateMapData(data);

        }


        return data;
    }

    private void saveOrUpdateMapData(Caa123TranscationData data) {


        TMapAuction auction = new TMapAuction();

        auction.setRelevanceId(String.valueOf(data.getId()));
        auction.setSource(CAA_SOURCE);
        auction.setLng(data.getLng());
        auction.setLat(data.getLat());
        auction.setStartingPrice(bigDecimalToStr(data.getStartPrice()));
        auction.setProName(data.getProName());
        auction.setCityName(data.getCityName());
        auction.setAreaName(data.getAreaName());
        auction.setLooker(data.getLooker());
        auction.setItemUrl(data.getItemUrl());
        auction.setTitle(data.getName());
        auction.setAddressDetail(data.getAddressDetail());
        auction.setTypeName(data.getCategoryName());
        auction.setStartDate(DateUtil.format(data.getStartTime(),DateUtil.STYLE_1));
        auction.setEndDate(DateUtil.format(data.getEndTime(),DateUtil.STYLE_1));
        auction.setStage(data.getStage());
        auction.setSellType(data.getSellType());
        auction.setArea(data.getArea());
        auction.setRaisePrice(bigDecimalToStr(data.getRateLadder()));
        auction.setAmount(bigDecimalToStr(data.getNowPrice()));
        auction.setBidNum(data.getBidNum());///
        auction.setAppler(String.valueOf(data.getAppler()));
        auction.setDeposit(bigDecimalToStr(data.getCashDeposit()));
        //auction.setReminder(String.valueOf(gPaiPm.getRemindCount()));
        auction.setConsultPrice(bigDecimalToStr(data.getAssessPrice()));
        auction.setCurrentPrice(bigDecimalToStr(data.getNowPrice()));
        auction.setMarketPrice(bigDecimalToStr(data.getAssessPrice()));
        auction.setDeleteFlag(0);
        auction.setStatus(data.getStatusDesc());
        auction.setCreateTime(new Date());


        //获取公拍网相关的数据
        List<TMapAuction> mapAuctionList = mapAuctionDao.getByrelevanceId(String.valueOf(data.getId()),CAA_SOURCE);
        if(mapAuctionList!=null&&mapAuctionList.size()>0){
            auction.setId(mapAuctionList.get(0).getId());
            auction.setUpdateTime(new Date());
            auction.setCreateTime(mapAuctionList.get(0).getCreateTime());
        }


        mapAuctionDao.saveAndFlush(auction);




    }



    private  String bigDecimalToStr(BigDecimal decimal){

        if(decimal==null){
            return null;
        }

        return String.valueOf(decimal);
    }






}
