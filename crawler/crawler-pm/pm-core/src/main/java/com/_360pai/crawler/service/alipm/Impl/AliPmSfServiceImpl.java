package com._360pai.crawler.service.alipm.Impl;

import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.crawler.common.util.ComUtils;
import com._360pai.crawler.config.AsyncConfig;
import com._360pai.crawler.dao.alipm.AliPmSfDao;
import com._360pai.crawler.dao.com.MapAuctionDao;
import com._360pai.crawler.model.alipm.TAliPmSf;
import com._360pai.crawler.model.com.TMapAuction;
import com._360pai.crawler.service.alipm.AliPmSfService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
@Service
public class AliPmSfServiceImpl implements AliPmSfService {

    Logger logger = LoggerFactory.getLogger(AliPmSfServiceImpl.class);


    private static final String ALISF_SOURCE = "aliSf";

    @Autowired
    private AsyncConfig asyncConfig;

    @Resource
    private AliPmSfDao aliPmSfDao;


    @Resource
    private MapAuctionDao mapAuctionDao;

    @Autowired
    private SystemProperties systemProperties;


    @Override
    public TAliPmSf saveAliPmSf(TAliPmSf model) {

        List<TAliPmSf> aliPmSfsList =  aliPmSfDao.getByCode(model.getCode());

        if(aliPmSfsList!=null&&aliPmSfsList.size()>0){
            TAliPmSf sf = aliPmSfsList.get(0);

            model.setId(sf.getId());
            model.setUpdateTime(new Date());
        }

        TAliPmSf pmSf = aliPmSfDao.saveAndFlush(model);

        //将数据同步到总表
        saveMapAuctionInfo(pmSf);

        return pmSf;
    }

    private void saveMapAuctionInfo(TAliPmSf pmSf) {


        TMapAuction auction = new TMapAuction();

        BeanUtils.copyProperties(pmSf, auction);

        auction.setId(null);
        List<TMapAuction> mapAuctionList = mapAuctionDao.getByrelevanceId(String.valueOf(pmSf.getId()),ALISF_SOURCE);

        if(mapAuctionList!=null&&mapAuctionList.size()>0){
            auction.setId(mapAuctionList.get(0).getId());
            auction.setUpdateTime(new Date());
        }
        auction.setStartingPrice(pmSf.getStaringPrice());
        auction.setSource(ALISF_SOURCE);
        auction.setRelevanceId(String.valueOf(pmSf.getId()));

        mapAuctionDao.saveAndFlush(auction);






    }


    @Override
    public void repairData(TAliPmSf model) {

        List<TMapAuction> auctionList = mapAuctionDao.getLatErrorList();


        //调用百度获取经纬度
        for(TMapAuction auctionMap:auctionList){


            String addressDetail = getAddressDetailInfo(auctionMap);

            Map<String,String> map =  ComUtils.getLatLngInfo(addressDetail,auctionMap.getCityName(),systemProperties);
            String lat = map.get("lat");
            String lng =map.get("lng");

            if("999".equals(lat)){
                continue;
            }
            //更新经纬度信息
            auctionMap.setLat(lat);
            auctionMap.setLng(lng);
            auctionMap.setUpdateTime(new Date());

            try {
                Thread.sleep(3);

            }catch (Exception e){

            }


            asyncConfig.aliZcListExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    saveData(auctionMap);

                }
            });

        }






    }

    private String getAddressDetailInfo(TMapAuction auctionMap) {

        //去掉省市区县字段

        String addressDetail = replaceArea(auctionMap.getAddressDetail());

        addressDetail.replace(replaceArea(auctionMap.getProName()),"").replace(replaceArea(auctionMap.getCityName()),"");
        StringBuffer sb = new StringBuffer();
        sb.append(auctionMap.getProName());
        sb.append(auctionMap.getCityName());

        if(!"其它".equals(auctionMap.getAreaName())){
            addressDetail.replace(replaceArea(auctionMap.getAreaName()),"");
            sb.append(auctionMap.getAreaName());
        }
        sb.append(auctionMap.getAddressDetail());

        return sb.toString();
    }

    private String replaceArea(String str) {
        str = str.replace("省","");
        str = str.replace("市","");
        str = str.replace("区","");
        str = str.replace("县","");
        str = str.replace("镇","");

        return str;
    }

    private void saveData(TMapAuction pmSf) {


        //更新auction 表
        mapAuctionDao.saveAndFlush(pmSf);

    }

    private void updateMapAuction(TAliPmSf pmSf) {

        List<TMapAuction> mapAuctionList = mapAuctionDao.getByrelevanceId(String.valueOf(pmSf.getId()),ALISF_SOURCE);

        if(mapAuctionList!=null&&mapAuctionList.size()>0){

            TMapAuction auction = mapAuctionList.get(0);
            auction.setLat(pmSf.getLat());
            auction.setLng(pmSf.getLng());
            auction.setUpdateTime(new Date());
            mapAuctionDao.saveAndFlush(auction);


        }

    }


    @Override
    public void repairAreaData(TAliPmSf model) {


        List<TMapAuction> auctionList = mapAuctionDao.getAreaErrorList();

        for(TMapAuction auction:auctionList){

            if("ali".equals(auction.getSource())||"aliSf".equals(auction.getSource())){


                try {
                    Thread.sleep(20);

                }catch (Exception e){

                }

                asyncConfig.aliZcListExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        auction.setArea(getAreaInfo(auction.getRelevanceId()));
                        auction.setUpdateTime(new Date());
                        mapAuctionDao.saveAndFlush(auction);
                    }
                });









            }

        }


    }


    /**
     *
     *获取建筑面积信息
     */
    private String getAreaInfo(String id) {


        TAliPmSf model =  aliPmSfDao.getById(Integer.valueOf(id));

        if(model==null){
            return null;
        }


        List<String> textStrList = new ArrayList<>();

        textStrList.add(model.getNoticeUrl());
        textStrList.add(model.getInstructionUrl());
        textStrList.add(model.getDetailUrl());

        List<String> tableStrList = new ArrayList<>();

        tableStrList.add(model.getDetailUrl());

        return ComUtils.getAreaInfo(textStrList,tableStrList,ComUtils.URL_TYPE);



    }





}
