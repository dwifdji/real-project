package com.tzCloud.core.service.auction;

import com.github.pagehelper.PageInfo;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.model.auction.TMapAuction;
import com.tzCloud.facade.auction.req.ValuationAuctionReq;

import java.util.List;
import java.util.Map;

public interface AuctionService {


    ResponseModel getAuctionAveragePrice(ValuationAuctionReq.comReq req);


    PageInfo getDealList(ValuationAuctionReq.comReq req);


    TMapAuction getDealDetail(ValuationAuctionReq.comReq req);

    ResponseModel getTrendInfo(ValuationAuctionReq.comReq req);


    List<TMapAuction> getAuctionList(Map<String,String> map);

}
