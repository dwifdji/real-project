package com.tzCloud.facade.auction;


import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.facade.auction.req.ValuationAuctionReq;

public interface AuctionFacade {

    ResponseModel getAuctionAveragePrice(ValuationAuctionReq.comReq req);


    ResponseModel getDealList(ValuationAuctionReq.comReq req);


    ResponseModel getDealDetail(ValuationAuctionReq.comReq req);


    ResponseModel getTrendInfo(ValuationAuctionReq.comReq req);


    ResponseModel getMapDealList(ValuationAuctionReq.comReq req);



}
