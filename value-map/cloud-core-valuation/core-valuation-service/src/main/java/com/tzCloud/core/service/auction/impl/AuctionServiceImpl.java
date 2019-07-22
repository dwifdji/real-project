package com.tzCloud.core.service.auction.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.enums.AuctionEnum;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.core.dao.auction.TMapAuctionDao;
import com.tzCloud.core.model.auction.TMapAuction;
import com.tzCloud.core.service.auction.AuctionService;
import com.tzCloud.core.utils.MapUtil;
import com.tzCloud.core.utils.MathUtil;
import com.tzCloud.core.vo.auction.AuctionAveragePriceVO;
import com.tzCloud.core.vo.auction.AuctionTrendVO;
import com.tzCloud.facade.auction.req.ValuationAuctionReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private TMapAuctionDao mapAuctionDao;


    @Override
    public ResponseModel getAuctionAveragePrice(ValuationAuctionReq.comReq req) {

        AuctionAveragePriceVO vo= new AuctionAveragePriceVO();

        List<TMapAuction> auctionList =  getDoneMapAuctionInfo(req);

        Map<Integer,BigDecimal> averageMap = getAverageInfo(auctionList);

        BigDecimal  average =   BigDecimal.ZERO;

        int count = 0;

        for(Integer key : averageMap.keySet()){
            count = key;
            average = averageMap.get(key);
         }

        vo.setAveragePrice(count==0?null:MathUtil.divide(String.valueOf(average),String.valueOf(count)));
        vo.setSixMonAveragePrice(getSixMonAveragePrice(auctionList));

        JSONObject jsonInfo = getLatelyAveragePrice(auctionList);

        vo.setLatelyAveragePrice(jsonInfo.getString("latelyAveragePrice"));
        vo.setLatelyId(jsonInfo.getString("latelyId"));
        vo.setLat(jsonInfo.getString("lat"));
        vo.setLng(jsonInfo.getString("lng"));


        return ResponseModel.succ(vo);
    }

    private JSONObject getLatelyAveragePrice(List<TMapAuction> auctionList) {

        JSONObject jsonObject = new JSONObject();


        for(TMapAuction auction:auctionList){

            //成交价
            String amount =formatNum(auction.getAmount());
            //建筑面积
            String area =formatNum(auction.getArea());

            //判断数据有效性
            if(isNumber(amount)&&isNumber(area)&&!MathUtil.scopeData(amount,area)){

                jsonObject.put("latelyId",auction.getId());
                jsonObject.put("latelyAveragePrice",MathUtil.divide(amount,area));
                jsonObject.put("lat",auction.getLat());
                jsonObject.put("lng",auction.getLng());

                return  jsonObject;
            }

        }

        return jsonObject;
    }


    /**
     *
     *获取六个月的均价
     */
    private String getSixMonAveragePrice(List<TMapAuction> auctionList) {


        List<TMapAuction> sixMonAuctionList = new ArrayList<>();

        for(TMapAuction auction :auctionList){
            if(sixData(auction)){
                sixMonAuctionList.add(auction);
            }

        }

        Map<Integer,BigDecimal> averageMap = getAverageInfo(sixMonAuctionList);

        BigDecimal  average =   BigDecimal.ZERO;

        int count = 0;

        for(Integer key : averageMap.keySet()){
            count = key;
            average = averageMap.get(key);
        }

        return count==0?null:MathUtil.divide(String.valueOf(average),String.valueOf(count));

     }

    private boolean sixData(TMapAuction auction) {

        //格式化年月
        String  mon = DateUtil.formatStrDate(auction.getEndDate(),DateUtil.NORM_DATE_MONTH).replace("-","");

        //上个月
        String sixMonth =DateUtil.getRecentMonth(new Date(),-6,DateUtil.yyyyMM);

        //上个月
        String oneMonth =DateUtil.getRecentMonth(new Date(),-1,DateUtil.yyyyMM);

        if(Integer.valueOf(sixMonth)<=Integer.valueOf(mon)&&Integer.valueOf(mon)<=Integer.valueOf(oneMonth)){
            return true;
        }


        return false;
    }

    private Map<Integer,BigDecimal> getAverageInfo(List<TMapAuction> auctionList) {

        Map<Integer,BigDecimal> map = new HashMap<>();

        BigDecimal  average =   BigDecimal.ZERO;

        int count = 0;

        for(TMapAuction auction :auctionList){

            //成交价
            String amount =formatNum(auction.getAmount());
            //建筑面积
            String area =formatNum(auction.getArea());

            //判断数据有效性
            if(!isNumber(amount)||!isNumber(area)||MathUtil.scopeData(amount,area)){
                continue;
            }

            average = MathUtil.add(average,MathUtil.divide(new BigDecimal(amount),new BigDecimal(area)));

            count++;
        }



        map.put(count,average);

        return map;
    }

    private String formatNum(String data) {

        if(StringUtils.isEmpty(data)){
            return null;
        }

        data = data.trim();

        return data.replace(",","");
    }



    private List<TMapAuction> getDoneMapAuctionInfo(ValuationAuctionReq.comReq req) {
        Map<String, String> searchParams = new HashMap<>();

        if("trendInfo".equals(req.getReqType())){
            searchParams.put("proName",req.getProvince());
            searchParams.put("cityName",req.getCity());
            searchParams.put("areaName",req.getArea());

            searchParams.put("proName1",replaceArea(req.getProvince()));
            searchParams.put("cityName1",replaceArea(req.getCity()));
            searchParams.put("areaName1",replaceArea(req.getArea()));

        }else{
            searchParams = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());
            searchParams.put("radius",MathUtil.multiply(req.getRadius(),"1000"));
            searchParams.put("distanceNum","asc");
            searchParams.put("assetType",AuctionEnum.ASSET_TYPE.getVal(req.getAssetType()));
            searchParams.put("lat",req.getLat());
            searchParams.put("lng",req.getLng());
        }

        searchParams.put("status","done");


        return    mapAuctionDao.getMapAuctionListByParam(searchParams);

    }

    /**
     *
     *过滤省市区 字段
     */
    private String replaceArea(String str) {
        str = str.replace("省","");
        str = str.replace("市","");
        str = str.replace("区","");
        str = str.replace("县","");
        str = str.replace("镇","");

        return str;
    }


    public static boolean isNumber(String string) {
        if (StringUtils.isBlank(string)){
            return false;

        }
        Pattern pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$");
        return pattern.matcher(string).matches();
    }

    @Override
    public PageInfo getDealList(ValuationAuctionReq.comReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        Map<String, String> searchParams = MapUtil.getSearchParams(req.getLat(), req.getLng(), req.getRadius());

        searchParams.put("assetType",AuctionEnum.ASSET_TYPE.getVal(req.getAssetType()));
        searchParams.put("searchKey",req.getSearchKey());
        searchParams.put("distanceNum","asc");
        searchParams.put("radius",MathUtil.multiply(req.getRadius(),"1000"));


        searchParams.put("lat",req.getLat());
        searchParams.put("lng",req.getLng());
        if("1".equals(req.getSixMonFlag())){
            searchParams.put("startEndDate",DateUtil.getRecentMonth(new Date(),-6,DateUtil.NORM_DATE_MONTH));
            searchParams.put("endEndDate",DateUtil.getRecentMonth(new Date(),-1,DateUtil.NORM_DATE_MONTH));
        }
        //searchParams.put("status","done");



        List<TMapAuction> auctionList =   mapAuctionDao.getMapAuctionListByParam(searchParams);


        return new PageInfo<>(auctionList);


    }

    private String getSixMonDate() {



        return null;
    }

    @Override
    public TMapAuction getDealDetail(ValuationAuctionReq.comReq req) {


        return  mapAuctionDao.selectById(req.getId());
    }


    @Override
    public ResponseModel getTrendInfo(ValuationAuctionReq.comReq req) {
        req.setReqType("trendInfo");

        //获取区域内的全部成交数据
        List<TMapAuction> auctionList =  getDoneMapAuctionInfo(req);

        List<TMapAuction> monthData = new ArrayList<>();

        List<AuctionTrendVO> trendVOS = new ArrayList<>();

        Map<String,Object > respMap = new HashMap<>();


        //六个月
        for(int i = 6;i>0;i--){

            AuctionTrendVO trendVO = new AuctionTrendVO();
            //获取月份
            String month =DateUtil.getRecentMonth(new Date(),-i,DateUtil.NORM_DATE_MONTH);
            List<TMapAuction> monthAuction = new ArrayList<>();
            //根据月份划分数据
            for(TMapAuction auction :auctionList){

                if(isMonthData(month,auction)){
                    monthAuction.add(auction);
                    monthData.add(auction);
                }

            }

            trendVO.setMonth(month);
            trendVO.setAveragePrice(getMonthAveragePrice(monthAuction,"0"));
            trendVO.setNum(monthAuction.size());
            trendVOS.add(trendVO);
        }

        respMap.put("monthList",trendVOS);
        respMap.put("totalPrice",getMonthAveragePrice(monthData,null));
        respMap.put("risePercent",getRisePercent(trendVOS));


        return ResponseModel.succ(respMap);
    }


    /**
     *
     *获取上升趋势百分比
     */
    private String getRisePercent(List<AuctionTrendVO> trendVOS) {
        //倒叙排列
       // Collections.reverse(trendVOS);

        String  oneAveragePrice = trendVOS.get(trendVOS.size()-1).getAveragePrice();

        String twoAveragePrice = trendVOS.get(trendVOS.size()-2).getAveragePrice();

        if("0".equals(oneAveragePrice)||"0".equals(twoAveragePrice)){

            return null;
        }

        String divide=  MathUtil.divide(oneAveragePrice,twoAveragePrice,4);



        String  risePercent = MathUtil.subtract(divide,"1");


        return MathUtil.formatData(MathUtil.multiply(risePercent,"100"));
    }


    /**
     *
     *获取月份均价
     */
    private String getMonthAveragePrice(List<TMapAuction> monthAuction,String type) {


        Map<Integer,BigDecimal> averageMap = getAverageInfo(monthAuction);

        BigDecimal  average =   BigDecimal.ZERO;

        int count = 0;

        for(Integer key : averageMap.keySet()){
            count = key;
            average = averageMap.get(key);
        }
        if(count==0){
            if("0".equals(type)){
                return "0";
            }
            return null;
        }

        return MathUtil.divide(String.valueOf(average),String.valueOf(count));
    }

    /**
     *
     *判断是当个月的数据
     */
    private boolean isMonthData(String month, TMapAuction auction) {

        return month.equals(DateUtil.formatStrDate(auction.getEndDate(),DateUtil.NORM_DATE_MONTH));

     }


    @Override
    public List<TMapAuction> getAuctionList(Map<String, String> map) {
        return mapAuctionDao.getMapAuctionListByParam(map);
    }
}
