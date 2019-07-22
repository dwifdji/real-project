package com._360pai.core.facade.shop;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.applet.vo.ShopUpdateApplyRecordVo;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.shop.req.ShopReq;

/**
 * @author: liuhaolei
 * @Title: ShopProvider
 * @ProjectName: core-service
 * @Description: 小程序店铺facade
 * @Date: 2018-11-22
 */
public interface ShopFacade {


    ResponseModel getSearchAuctionList(ShopReq.ShopListReq shopListReq);

    ResponseModel setHomePage(ShopReq.HomePageReq homePageReq);

    ResponseModel outOfstocks(ShopReq.OutOfStocks outOfStocks);

    ResponseModel upOfStocks(ShopReq.UpOfStocks upOfStocks);

    ResponseModel getSearchRecordList(ShopReq.SearchRecordReq searchRecordReq);

    ResponseModel deleteSearchRecord(ShopReq.DeleteRecordReq deleteRecordReq);

    ResponseModel getShopMessageList(ShopReq.ShopMessageReq shopMessageReq);

    ResponseModel getShopMessageDetail(ShopReq.ShopMessageDetailReq shopMessageDetailReq);
 	ResponseModel getRemainingGuides(ShopReq.ShopGuidesReq shopGuidesReq);

    ResponseModel updateRemainingGuide(ShopReq.ShopGuideUpdateReq shopGuideUpdateReq);

    ResponseModel favor(ShopReq.BaseReq req);

    ResponseModel cancelFavor(ShopReq.BaseReq req);

    PageInfoResp<ShopVo> getFavoriteShopList(ShopReq.BaseReq req);

    ShopVo getShop(ShopReq.BaseReq req);

    ResponseModel updateApply(ShopReq.UpdateApplyReq req);

    ResponseModel update(ShopReq.UpdateReq req);

    ResponseModel updateContactPhone(ShopReq.UpdateContactPhoneReq req);

    ResponseModel updateApplyApprove(ShopReq.BaseReq req);

    ResponseModel updateApplyReject(ShopReq.BaseReq req);

    ResponseModel getShopAuctionList(ShopReq.ShopListReq shopListReq);

    ResponseModel getMyShopMessage(ShopReq.ShopMessageReq shopMessageReq);

    ResponseModel getShopListByPage(ShopReq.QueryReq req);

    ShopVo getShopDetail(ShopReq.BaseReq req);

    PageInfoResp<ShopUpdateApplyRecordVo> getUpdateApplyRecordListByPage(ShopReq.QueryReq req);

    ShopUpdateApplyRecordVo getUpdateApplyRecord(ShopReq.BaseReq req);

    ResponseModel createShop(ShopReq.CreateReq req);

    ResponseModel getAuctionDetail(AuctionReq.AuctionInfoReq req );

    ResponseModel getSearchDetails();

    ResponseModel getHomeRecommend(ShopReq.ShopListReq req);

    ResponseModel getShopWebEnrollingList(ShopReq.EnrollingReq req);

    ResponseModel getShopEnrollingList(ShopReq.EnrollingReq req);

    ResponseModel upOrDownShopEnrolling(ShopReq.UpOrDownEnrollingReq req);

    ResponseModel setEnrollingHomePage(ShopReq.HomePageReq homePageReq);

    ResponseModel getEnrollingSearchParams();

    ResponseModel getSearchAuctionAndEnrollingList(ShopReq.AuctionEnrollingReq shopListReq);

    PageInfoResp<ShopVo> getInvitedShopListByPage(ShopReq.QueryReq req);

}