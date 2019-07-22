package com.tzCloud.valuation.controller.auction;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.facade.auction.AuctionFacade;
import com.tzCloud.facade.auction.req.ValuationAuctionReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wcq
 * @Title: AuctionController  估价地图 司法信息
 * @ProjectName zeus-parent
 * @Description:
 * @date 2019/06/13 13:09
 */
@RestController
@RequestMapping("/open/valuationMap/auction")
public class AuctionController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);

    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;



    /**
     * 功能描述: 获取司法成交均价
     *
     * @param:
     * @return:
     *
     * @auther: wc
     *
     *
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getAuctionAveragePrice")
    public ResponseModel getAuctionAveragePrice(ValuationAuctionReq.comReq req) {

        //经纬度校验
        if(StringUtils.isBlank(req.getLat())||StringUtils.isBlank(req.getLng())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        if(StringUtils.isEmpty(req.getRadius())){
            req.setRadius("1");
        }


        return auctionFacade.getAuctionAveragePrice(req);
    }




    /**
     * 功能描述: 获取区域拍卖成交数据列表
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getDealList")
    public ResponseModel getDealList(ValuationAuctionReq.comReq req) {
        //经纬度校验
        if(StringUtils.isBlank(req.getLat())||StringUtils.isBlank(req.getLng())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        if(StringUtils.isEmpty(req.getRadius())){
            req.setRadius("1");
        }

        return auctionFacade.getDealList(req);
    }



    /**
     * 功能描述: 获取拍卖成交详情信息
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getDealDetail")
    public ResponseModel getDealDetail(ValuationAuctionReq.comReq req) {
        //经纬度校验
        if(StringUtils.isBlank(req.getId())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        return auctionFacade.getDealDetail(req);
    }




    /**
     * 功能描述: 获取区域走势
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getTrendInfo")
    public ResponseModel getTrendInfo(ValuationAuctionReq.comReq req) {


        return auctionFacade.getTrendInfo(req);
    }




    /**
     * 功能描述: 获取地图标点信息
     *
     * @param:
     * @return:
     * @auther: wcq
     * @date: 2019/06/13
     */
    @GetMapping(value = "/getMapDealList")
    public ResponseModel getMapDealList(ValuationAuctionReq.comReq req) {
        //经纬度校验
        if(StringUtils.isBlank(req.getLat())||StringUtils.isBlank(req.getLng())){

            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        if(StringUtils.isEmpty(req.getRadius())){
            req.setRadius("1");
        }

        return auctionFacade.getMapDealList(req);
    }

}
