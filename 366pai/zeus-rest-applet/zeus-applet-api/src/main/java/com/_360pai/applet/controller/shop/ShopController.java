package com._360pai.applet.controller.shop;


import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.core.common.constants.SmsType;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.assistant.CityFacade;
import com._360pai.core.facade.assistant.NotifyPartyActivityFacade;
import com._360pai.core.facade.assistant.req.CityReq;
import com._360pai.core.facade.assistant.req.NotifyPartyActivityReq;
import com._360pai.core.facade.assistant.resp.CityResp;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.req.ShopReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.apache.http.protocol.ResponseServer;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author: liuhaolei
 * @Title: ShopProvider
 * @ProjectName: zeus-applet-api
 * @Description: 小程序店铺controller
 * @Date: 2018-11-22
 */
@RestController
public class ShopController extends AbstractController {

    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;
    @Reference(version = "1.0.0")
    private NotifyPartyActivityFacade notifyPartyActivityFacade;
    @Reference(version = "1.0.0")
    private AuctionOrderFacade auctionOrderFacade;
    @Reference(version = "1.0.0")
    private CityFacade cityFacade;


    /**
     * 店铺列表高级查询
     * @param shopListReq
     * @return
     */
    @GetMapping(value = "/confined/shop/getShopAuctionList")
    public ResponseModel getShopAuctionList(ShopReq.ShopListReq shopListReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();


         shopListReq.setShopId(StringUtils.isEmpty(shopListReq.getShopId()) ?
                String.valueOf(accountBaseInfo.getShopId()==null?0:accountBaseInfo.getShopId()) : shopListReq.getShopId());

        if (StringUtils.isBlank(shopListReq.getShopId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        return shopFacade.getShopAuctionList(shopListReq);
    }

    /**
     *
     * 平台列表高级查询
     *
     */
    @GetMapping("/confined/shop/getSearchAuctionList")
    public ResponseModel getSearchAuctionList(ShopReq.ShopListReq shopListReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getOpenId() == null || accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        shopListReq.setShopId(String.valueOf(accountBaseInfo.getShopId()));
        shopListReq.setOpenId(String.valueOf(accountBaseInfo.getOpenId()));

        return shopFacade.getSearchAuctionList(shopListReq);
    }


    /**
     * 设置商品首页展示
     */
    @PostMapping("/confined/shop/setHomePage")
    public ResponseModel setHomePage(@RequestBody ShopReq.HomePageReq homePageReq){
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(homePageReq.getHomePageArray() == null
                || homePageReq.getHomePageArray().length <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        homePageReq.setShopId(accountBaseInfo.getShopId().toString());

        return shopFacade.setHomePage(homePageReq);
    }

    /**
     * 批量下架店铺商品
     * @return
     */
    @PostMapping("/confined/shop/outOfstocks")
    public ResponseModel outOfstocks(@RequestBody ShopReq.OutOfStocks outOfStocks) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(outOfStocks.getOutOfStocks() == null
                || outOfStocks.getOutOfStocks().length <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        outOfStocks.setShopId(String.valueOf(accountBaseInfo.getShopId()));
        return shopFacade.outOfstocks(outOfStocks);
    }

    /**
     * 批量上架店铺商品
     * @return
     */
    @PostMapping("/confined/shop/upOfstocks")
    public ResponseModel upOfstocks(@RequestBody ShopReq.UpOfStocks upOfStocks) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(upOfStocks.getUpOfStocks() == null
                || upOfStocks.getUpOfStocks().length <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        upOfStocks.setShopId(String.valueOf(accountBaseInfo.getShopId()));

        return shopFacade.upOfStocks(upOfStocks);
    }

    /**
     * 获取搜索记录
     *
     */
    @GetMapping("/confined/shop/getSearchRecords")
    public ResponseModel getSearchRecordList(ShopReq.SearchRecordReq searchRecordReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null || accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        searchRecordReq.setShopId(String.valueOf(accountBaseInfo.getShopId()));
        searchRecordReq.setOpenId(String.valueOf(accountBaseInfo.getOpenId()));
        return shopFacade.getSearchRecordList(searchRecordReq);
    }

    /**
     *
     *  删除搜索记录
     */
    @PostMapping("/confined/shop/deleteSearchRecords")
    public ResponseModel deleteSearchRecord(@RequestBody ShopReq.DeleteRecordReq deleteRecordReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null || accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(deleteRecordReq.getDeleteType() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if("1".equals(deleteRecordReq.getDeleteType())) {
            if(deleteRecordReq.getRecords() == null || deleteRecordReq.getRecords().length < 0) {
                return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
            }
        }

        deleteRecordReq.setShopId(String.valueOf(accountBaseInfo.getShopId()));
        deleteRecordReq.setOpenId(String.valueOf(accountBaseInfo.getOpenId()));
        return shopFacade.deleteSearchRecord(deleteRecordReq);
    }

    /**
     * 进入我的店铺信息首页
     * @return
     */
    @RequestMapping("/confined/shop/getMyShopMessage")
    public ResponseModel getMyShopMessage(ShopReq.ShopMessageReq shopMessageReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null || accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        shopMessageReq.setShopId(accountBaseInfo.getShopId().toString());
        shopMessageReq.setOpenId(accountBaseInfo.getOpenId().toString());

        return shopFacade.getMyShopMessage(shopMessageReq);
    }



    /**
     * 获取店铺推送的信息
     * @param shopMessageReq
     * @return
     */
    @GetMapping(value = "/confined/shop/getShopMessageList")
    public ResponseModel getShopMessageList(ShopReq.ShopMessageReq shopMessageReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null || accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        shopMessageReq.setOpenId(accountBaseInfo.getOpenId());
        shopMessageReq.setShopId(String.valueOf(accountBaseInfo.getShopId()));

        return shopFacade.getShopMessageList(shopMessageReq);
    }

    /**
     *  获取信息详情
     * @return
     */
    @GetMapping(value = "/confined/shop/getShopMessageDetail")
    public ResponseModel getShopMessageDetail(ShopReq.ShopMessageDetailReq shopMessageDetailReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null || accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(StringUtils.isBlank(shopMessageDetailReq.getMessageId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        shopMessageDetailReq.setOpenId(accountBaseInfo.getOpenId());
        shopMessageDetailReq.setShopId(String.valueOf(accountBaseInfo.getShopId()));
        return shopFacade.getShopMessageDetail(shopMessageDetailReq);
    }

  	/**
     * 关注店铺接口
     */
    @PostMapping(value = "/confined/favor/shop")
    public ResponseModel favorShop(@RequestBody ShopReq.BaseReq req) {
        Assert.notNull(req.getShopId(), "shopId 不能为空");
        req.setOpenId(loadCurLoginOpenId());
        return shopFacade.favor(req);
    }

    /**
     * 取消关注店铺接口
     */
    @PostMapping(value = "/confined/cancel/favor/shop")
    public ResponseModel cancelFavorShop(@RequestBody ShopReq.BaseReq req) {
        Assert.notNull(req.getShopId(), "shopId 不能为空");
        req.setOpenId(loadCurLoginOpenId());
        return shopFacade.cancelFavor(req);
    }

    /**
     * 我关注的店铺列表接口
     */
    @GetMapping(value = "/confined/favorite/shop/list")
    public ResponseModel favoriteShopList(ShopReq.BaseReq req) {
        req.setOpenId(loadCurLoginOpenId());
        return ResponseModel.succ(shopFacade.getFavoriteShopList(req));
    }

    /**
     * 获取店铺信息接口
     */
    @GetMapping(value = "/confined/get/shop/info")
    public ResponseModel getShopInfo(ShopReq.BaseReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (req.getShopId() == null) {
            if (accountBaseInfo.getShopId() == null) {
                if (accountBaseInfo.getAccountId() != null) {
                    ShopVo shop = new ShopVo();
                    shop.setPageTitleName("我的资产小店");
                    shop.setName(accountBaseInfo.getNickName() + "的小店");
                    shop.setLogoUrl(accountBaseInfo.getAvatarUrl());
                    shop.setMobile(ToolUtil.maskMobile(accountBaseInfo.getMobile()));
                    shop.setContactPhone(ToolUtil.maskMobile(accountBaseInfo.getMobile()));
                    shop.setIsMyShop(true);
                    return ResponseModel.succ(shop);
                } else {
                    req.setShopId(SystemConstant.DEFAULT_APPLET_SHOP);
                }
            } else {
                req.setShopId(accountBaseInfo.getShopId());
            }
        }
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setOpenId(accountBaseInfo.getOpenId());
        return ResponseModel.succ(shopFacade.getShop(req));
    }

    /**
     * 店铺信息修改申请接口
     */
    @PostMapping(value = "/confined/shop/update/apply")
    public ResponseModel updateApply(@Valid @RequestBody ShopReq.UpdateApplyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail("还没有店铺");
        }
        req.setShopId(accountBaseInfo.getShopId());
        return shopFacade.updateApply(req);
    }

 	/**
     *
     * 获取某个用户已经引导的步骤
     */
    @GetMapping("/confined/shop/getRemainingGuides")
    public ResponseModel getRemainingGuides(ShopReq.ShopGuidesReq shopGuidesReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }
        shopGuidesReq.setOpenId(accountBaseInfo.getOpenId());

        return shopFacade.getRemainingGuides(shopGuidesReq);
    }

    /**
     *
     *  更新用户引导即新增
     */
    @PostMapping("/confined/shop/updateRemainingGuide")
    public ResponseModel updateRemainingGuide(@RequestBody ShopReq.ShopGuideUpdateReq shopGuideUpdateReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getOpenId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        if(StringUtils.isBlank(shopGuideUpdateReq.getType())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        shopGuideUpdateReq.setOpenId(accountBaseInfo.getOpenId());

        return shopFacade.updateRemainingGuide(shopGuideUpdateReq);
    }

    /**
     *  设置或者取消提醒
     * @param req
     * @return
     */
    @PostMapping("/confined/shop/notifyOrCancel")
    public ResponseModel notifyMe(@RequestBody ShopReq.ShopNotifyReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(StringUtils.isBlank(req.getType()) || StringUtils.isBlank(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        NotifyPartyActivityReq.notifyReq notifyReq = new NotifyPartyActivityReq.notifyReq();
        ResponseModel model = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
//        if(accountBaseInfo.getCurrentPartyId()==null){
//            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
//
//        }
        notifyReq.setAccountId(accountBaseInfo.getAccountId());
        notifyReq.setPartyId(accountBaseInfo.getCurrentPartyId());
        notifyReq.setActivityId(Integer.parseInt(req.getAuctionId()));
        notifyReq.setPathType((short)1);

        if("1".equals(req.getType())) {
            notifyPartyActivityFacade.notifyMe(notifyReq);
        }else {
            notifyPartyActivityFacade.cancel(notifyReq,accountBaseInfo.getCurrentPartyId());
        }

        return model;
    }

    /**
     * 获取搜索条件
     * @return
     */
    @GetMapping("/open/shop/getSearchDetails")
    public ResponseModel getSearchDetails() {

        return shopFacade.getSearchDetails();
    }

    /**
     * 获取所有省份
     * @return
     */
    @GetMapping(value = "/confined/shop/getAllProvinces")
    public ResponseModel getAllProvinces() {
        CityResp resp = cityFacade.getAllProvinces();
        return ResponseModel.succ(resp.getProvinces());
    }

    /**
     * 获取所有城市
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/shop/getCitiesByProvince")
    public ResponseModel getCitiesByProvince(CityReq req) {
        CityResp resp = cityFacade.getCitiesByProvinceId(req);
        return ResponseModel.succ(resp.getCities());
    }

    /**
     * 店铺信息修改接口
     */
    @PostMapping(value = "/confined/shop/update")
    public ResponseModel update(@Valid @RequestBody ShopReq.UpdateReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail("还没有店铺");
        }
        req.setShopId(accountBaseInfo.getShopId());
        return shopFacade.update(req);
    }

    /**
     * 修改店铺联系电话接口
     */
    @PostMapping(value = "/confined/shop/update/contact/phone")
    public ResponseModel updateContactPhone(@Valid @RequestBody ShopReq.UpdateContactPhoneReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail("还没有店铺");
        }
        // 校验 验证码
        if (!checkSmsCode(req.getContactPhone(), SmsType.SMS_MODIFY_SHOP_CONTACT_PHONE.getKey(), req.getSmsCode())) {
            return ResponseModel.fail(ApiCallResult.VERIFICATION);
        }
        // 清除验证码
        removeSmsCode(accountBaseInfo.getMobile(), SmsType.SMS_MODIFY_SHOP_CONTACT_PHONE.getKey());
        req.setShopId(accountBaseInfo.getShopId());
        return shopFacade.updateContactPhone(req);
    }

    /**
     * 获取首页热门推荐接口
     */
    @GetMapping(value = "/open/shop/getHomeRecommend")
    public ResponseModel getHomeRecommend(ShopReq.ShopListReq req) {
        return shopFacade.getHomeRecommend(req);
    }

    /**
     * 查询平台的招商活动
     * @param
     * @return
     */
    @GetMapping(value = "/confined/shop/getShopWebEnrollingList")
    public ResponseModel getShopWebEnrollingList(ShopReq.EnrollingReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setShopId(accountBaseInfo.getShopId() == null ?  null : accountBaseInfo.getShopId());

        return shopFacade.getShopWebEnrollingList(req);
    }

    /**
     * 高级查询不同状态的招商活动
     * @param
     * @return
     */
    @GetMapping(value = "/confined/shop/getShopEnrollingList")
    public ResponseModel getShopEnrollingList(ShopReq.EnrollingReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();

        req.setShopId(req.getShopId() == null ? accountBaseInfo.getShopId(): req.getShopId());
        return shopFacade.getShopEnrollingList(req);
    }


    /**
     * 上架或者下架店铺招商活动
     * @return
     */
    @PostMapping("/confined/shop/upOrDownShopEnrolling")
    public ResponseModel upOrDownShopEnrolling(@RequestBody ShopReq.UpOrDownEnrollingReq req) {

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail("您还没有创建店铺");
        }

        if(req.getOutOfStocks() == null || req.getOutOfStocks().length <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        if(StringUtils.isNotBlank(req.getDeleteFlag()) && !"1".equals(req.getOutFlag())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        req.setShopId(accountBaseInfo.getShopId().toString());

        return shopFacade.upOrDownShopEnrolling(req);
    }


    /**
     * 预招商设置商品首页展示
     */
    @PostMapping("/confined/shop/setEnrollingHomePage")
    public ResponseModel setEnrollingHomePage(@RequestBody ShopReq.HomePageReq homePageReq){
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail("您还没有创建店铺");
        }

        if(homePageReq.getHomePageArray() == null
                || homePageReq.getHomePageArray().length <= 0) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        homePageReq.setShopId(accountBaseInfo.getShopId().toString());

        return shopFacade.setEnrollingHomePage(homePageReq);
    }

    /**
     * 查询小程序内高级查询的所有参数
     * @return
     */
    @GetMapping("/confined/shop/getEnrollingSearchParams")
    public ResponseModel getEnrollingSearchParams() {
        return shopFacade.getEnrollingSearchParams();
    }


    /**
     *
     * 平台列表高级查询
     *
     */
    @GetMapping("/confined/shop/getSearchAuctionAndEnrollingList")
    public ResponseModel getSearchAuctionAndEnrollingList(ShopReq.AuctionEnrollingReq shopListReq) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if(accountBaseInfo.getOpenId() == null || accountBaseInfo.getShopId() == null) {
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT.getDesc());
        }

        shopListReq.setShopId(accountBaseInfo.getShopId());
        shopListReq.setOpenId(accountBaseInfo.getOpenId());
        return shopFacade.getSearchAuctionAndEnrollingList(shopListReq);
    }


}
