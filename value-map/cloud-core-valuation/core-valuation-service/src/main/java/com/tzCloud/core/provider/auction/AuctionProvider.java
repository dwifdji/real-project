package com.tzCloud.core.provider.auction;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.arch.common.enums.AuctionEnum;
import com.tzCloud.arch.common.utils.DateUtil;
import com.tzCloud.core.model.auction.TMapAuction;
import com.tzCloud.core.service.auction.AuctionService;
import com.tzCloud.core.utils.MapUtil;
import com.tzCloud.core.utils.MathUtil;
import com.tzCloud.core.vo.auction.AuctionDealInfoVO;
import com.tzCloud.core.vo.auction.AuctionDealListVO;
import com.tzCloud.core.vo.auction.AuctionDealVO;
import com.tzCloud.facade.auction.AuctionFacade;
import com.tzCloud.facade.auction.req.ValuationAuctionReq;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

@Component
@Service(version = "1.0.0")
public class AuctionProvider implements AuctionFacade {

    @Autowired
    private AuctionService auctionService;

    @Override
    public ResponseModel getAuctionAveragePrice(ValuationAuctionReq.comReq req) {


        return auctionService.getAuctionAveragePrice(req);
    }

    @Override
    public ResponseModel getDealList(ValuationAuctionReq.comReq req) {

        PageSerializable respInfo = new PageSerializable();


        PageInfo info = auctionService.getDealList(req);


        respInfo.setTotal(info.getTotal());

        respInfo.setList(formatDate(info.getList(),null,null,null));
        return ResponseModel.succ(respInfo);
    }

    private List<AuctionDealListVO> formatDate(List<TMapAuction> auctionList,String type,Integer leastLat,Integer reqNum) {
        List<AuctionDealListVO> dealList = new ArrayList<>();

        Integer totalNum = 0; //过滤的总数

        for(TMapAuction  auction :auctionList){

            totalNum ++;
            Boolean has =false;
            for(AuctionDealListVO listVO:dealList){
                if(String.valueOf(auction.getLng()).equals(listVO.getLng())&&String.valueOf(auction.getLat()).equals(listVO.getLat())){
                    has =true;
                }
            }
            if(has){
                continue;
            }
            AuctionDealListVO vo = new AuctionDealListVO();
            vo.setLat(String.valueOf(auction.getLat()));
            vo.setLng(String.valueOf(auction.getLng()));
            Map<String,String> totalAuctionMap = getTotalAuctionNum(auction,auctionList);
            vo.setNum(totalAuctionMap.get("num"));
            vo.setTotalAveragePrice(totalAuctionMap.get("totalAveragePrice"));
            vo.setAssetType("1".equals(vo.getNum())?auction.getTypeName().replace("用房",""):"");


            dealList.add(vo);

            //
            if("mapList".equals(type)&&totalNum>=reqNum&&dealList.size()>=leastLat){

                break;
            }

        }


        for(AuctionDealListVO dealListVO :dealList){

            List<AuctionDealVO> childrenListInfo = getChildrenListInfo(dealListVO,auctionList);

            dealListVO.setChildrenList(childrenListInfo);
            dealListVO.setNum(String.valueOf(childrenListInfo.size()));


        }





        return dealList;
    }

    private List<AuctionDealVO> getChildrenListInfo(AuctionDealListVO dealListVO, List<TMapAuction> auctionList) {
        List<AuctionDealVO> auctionDealVOList = new ArrayList<>();

        for(TMapAuction mapAuction :auctionList){
            if(String.valueOf(mapAuction.getLat()).equals(dealListVO.getLat())&&String.valueOf(mapAuction.getLng()).equals(dealListVO.getLng())){

                AuctionDealVO dealVO = new AuctionDealVO();
                dealVO.setAmount(MathUtil.formatToCurl("done".equals(mapAuction.getStatus())?mapAuction.getAmount():null));
                dealVO.setAssetName(mapAuction.getTitle());
                dealVO.setTagList(getTagListInfo(mapAuction));
                dealVO.setStaringPrice(MathUtil.formatToCurl(mapAuction.getStartingPrice()));
                dealVO.setConsultPrice(MathUtil.formatToCurl(mapAuction.getConsultPrice()));
                dealVO.setLat(String.valueOf(mapAuction.getLat()));
                dealVO.setLng(String.valueOf(mapAuction.getLng()));
                dealVO.setPrimaryKey(String.valueOf(mapAuction.getId()));
                dealVO.setDealDate(formatDateInfo(mapAuction.getEndDate()));
                dealVO.setAveragePrice(getAveragePriceInfo(mapAuction));
                dealVO.setAssetType(mapAuction.getTypeName().replace("用房",""));
                auctionDealVOList.add(dealVO);
            }

        }




        return auctionDealVOList;
    }

    private List<String> getTagListInfo(TMapAuction auction) {

        List<String> list = new ArrayList<>();
        list.add(auction.getTypeName().replace("用房",""));
        list.add(auction.getStage());
        list.add(AuctionEnum.STATUS_CODE.getVal(auction.getStatus()) );

        return list;
    }

    private String getAveragePriceInfo(TMapAuction auction) {
        String amount =formatNum(auction.getAmount());
        String area =formatNum(auction.getArea()) ;
        if(!"done".equals(auction.getStatus())||isNotValidData(amount,area)){
            return null;
        }

        return  MathUtil.divide(amount,area);
    }

    private String formatNum(String data) {

        if(StringUtils.isEmpty(data)){
            return null;
        }

        data = data.trim();

        return data.replace(",","").replace(" ","");
    }

    /**
     *
     *不为有效数据
     */
    private boolean isNotValidData(String amount, String area) {


        if(StringUtils.isEmpty(amount)||StringUtils.isEmpty(area)){
            return true;
        }



        return !MathUtil.isNumber(amount)||!MathUtil.isNumber(area)||MathUtil.scopeData(amount,area);

     }

    private Map<String,String> getTotalAuctionNum(TMapAuction auction, List<TMapAuction> auctionList) {

        Map<String,String> totalAuctionMap = new HashMap<>();

        Integer count=0;

        Integer totalCount=0;


        BigDecimal  average =   BigDecimal.ZERO;


        for(TMapAuction tmapAuction :auctionList){

            //经纬度相同的统计
            if(auction.getLat().equals(tmapAuction.getLat())&&auction.getLng().equals(tmapAuction.getLng())){
                //成交价
                String amount =formatNum(tmapAuction.getAmount());
                //建筑面积
                String area =formatNum(tmapAuction.getArea());

                totalCount++;
                //判断数据有效性
                if(!"done".equals(tmapAuction.getStatus())||isNotValidData(amount,area)){
                    continue;
                }

                average = MathUtil.add(average,MathUtil.divide(new BigDecimal(amount),new BigDecimal(area)));

                count++;

            }

        }


        totalAuctionMap.put("num",String.valueOf(totalCount));
        if(count==0){
            totalAuctionMap.put("totalAveragePrice","暂无均价");
        }else{
            totalAuctionMap.put("totalAveragePrice","￥"+MathUtil.divide(String.valueOf(average),String.valueOf(count))+"m²");

        }


        return totalAuctionMap;
    }



    @Override
    public ResponseModel getDealDetail(ValuationAuctionReq.comReq req) {

        TMapAuction mapAuction = auctionService.getDealDetail(req);

        if(mapAuction==null){
            return ResponseModel.fail(ApiCallResult.DATA_IS_EMPTY);
        }


        AuctionDealInfoVO vo = formatDealInfo(mapAuction);

        return ResponseModel.succ(vo);
    }

    private AuctionDealInfoVO formatDealInfo(TMapAuction mapAuction) {

        AuctionDealInfoVO vo = new AuctionDealInfoVO();

        vo.setAddress(mapAuction.getAddressDetail());
        vo.setName(mapAuction.getTitle());
        vo.setAmount(MathUtil.formatToCurl("done".equals(mapAuction.getStatus())?mapAuction.getAmount():null));
        vo.setAssetName(mapAuction.getTypeName());
        vo.setConsultPrice(MathUtil.formatToCurl(mapAuction.getConsultPrice()));
        vo.setPrimaryKey(String.valueOf(mapAuction.getId()));
        vo.setDeposit(MathUtil.formatToCurl(mapAuction.getDeposit()));
        vo.setDealDate(formatDateInfo(mapAuction.getEndDate()));
        vo.setRaisePrice(MathUtil.formatToCurl(mapAuction.getRaisePrice()));
        vo.setStaringPrice(MathUtil.formatToCurl(mapAuction.getStartingPrice()));
        vo.setStage(mapAuction.getStage());
        vo.setSellType(mapAuction.getSellType());
        vo.setBuildingArea(MathUtil.isAreaScope(mapAuction.getArea())?null:mapAuction.getArea());
        vo.setLandArea(MathUtil.isAreaScope(mapAuction.getLandArea())?null:mapAuction.getLandArea());
        vo.setWebUrl(mapAuction.getItemUrl());
        vo.setEndDate(formatDateInfo(mapAuction.getEndDate()));
        vo.setLat(String.valueOf(mapAuction.getLat()));
        vo.setLng(String.valueOf(mapAuction.getLng()));
        vo.setAveragePrice(getAveragePriceInfo(mapAuction));
        vo.setStaringDate(formatDateInfo(mapAuction.getStartDate()));
        vo.setBuildingType(mapAuction.getTypeName());
        vo.setPremium(getPremiumInfo(mapAuction));
        vo.setStatus(mapAuction.getStatus());
        return vo;
    }

    private String formatDateInfo(String date) {

        if(StringUtils.isEmpty(date)){

            return "暂无";
        }


        return  DateUtil.formatStrDate(date,DateUtil.NORM_DATE_PATTERN);

    }

    private String getPremiumInfo(TMapAuction mapAuction) {
        if(!"done".equals(mapAuction.getStatus())){
            return "暂无";
        }
        //出价一次说明是按起拍价成交
        if("1".equals(mapAuction.getBidNum())){
            return "无";
        }

        //溢价率 成交价/起拍价 -1
        BigDecimal decimalAmount = new BigDecimal(mapAuction.getAmount().replace(",",""));

        BigDecimal decimalStartingPrice = new BigDecimal(mapAuction.getStartingPrice().replace(",",""));

        BigDecimal premium =MathUtil.subtract(MathUtil.divide(decimalAmount,decimalStartingPrice,4),new BigDecimal("1"));

        //结果格式化
        String formatPremium =String.valueOf(MathUtil.multiply(premium,new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_DOWN)) ;



        return formatPremium+"%";
    }


    @Override
    public ResponseModel getTrendInfo(ValuationAuctionReq.comReq req) {



        return auctionService.getTrendInfo(req);
    }


    @Override
    public ResponseModel getMapDealList(ValuationAuctionReq.comReq req) {

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


        List<TMapAuction> list = auctionService.getAuctionList(searchParams);

        //根据距离 获取要展示的基础数量
        Integer totalLatNum = getLatNumInfo(req.getRadius());

        //获取点到的
        Integer totalReqNum = req.getPage() * req.getPerPage()>list.size()?list.size():req.getPage() * req.getPerPage();

        List<AuctionDealListVO> formatDate = formatDate(list,"mapList",totalLatNum,totalReqNum);


        return ResponseModel.succ(formatDate);
    }



    private Integer getLatNumInfo(String radius) {
        if("1.000".equals(radius)||"1".equals(radius)){
            return 20;
        }

        return 20 + (int) Math.floor(Double.valueOf(radius))*5;
    }
}
