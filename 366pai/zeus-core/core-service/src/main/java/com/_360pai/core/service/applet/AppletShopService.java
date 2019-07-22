package com._360pai.core.service.applet;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.condition.applet.TAppletShopCondition;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.applet.vo.ShopUpdateApplyRecordVo;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import com._360pai.core.model.applet.TAppletShop;

/**
 * @author xdrodger
 * @Title: AppletShopService
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/26 17:05
 */
public interface AppletShopService {
    ResponseModel favor(ShopReq.BaseReq req);

    ResponseModel cancelFavor(ShopReq.BaseReq req);

    PageInfoResp<ShopVo> getFavoriteShopList(ShopReq.BaseReq req);

    ShopVo getShop(ShopReq.BaseReq req);

    ResponseModel updateApply(ShopReq.UpdateApplyReq req);

    ResponseModel update(ShopReq.UpdateReq req);

    ResponseModel updateContactPhone(ShopReq.UpdateContactPhoneReq req);

    ResponseModel updateApplyApprove(ShopReq.BaseReq req);

    ResponseModel updateApplyReject(ShopReq.BaseReq req);

    ResponseModel getShopListByPage(ShopReq.QueryReq req);

    ShopVo getShopDetail(ShopReq.BaseReq req);

    PageInfoResp<ShopUpdateApplyRecordVo> getUpdateApplyRecordListByPage(ShopReq.QueryReq req);

    ShopUpdateApplyRecordVo getUpdateApplyRecord(ShopReq.BaseReq req);

    ResponseModel createShop(ShopReq.CreateReq req);


    AuctionDetailVo getAppletAuctionDetail(AuctionReq.AuctionInfoReq req);


    void updateShopById(TAppletShop req);


    TAppletShop getAppletShopInfo(TAppletShopCondition req);


    TAppletShop getAppletShopById(Integer id);

    PageInfoResp<AppletHallActivityVO> getAppletHallActivityList(ShopReq.ShopListReq req);

    PageInfoResp<ShopVo> getInvitedShopListByPage(ShopReq.QueryReq req);

}
