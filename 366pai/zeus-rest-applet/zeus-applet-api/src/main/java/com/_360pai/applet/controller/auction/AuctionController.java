package com._360pai.applet.controller.auction;

import com._360pai.applet.controller.AbstractController;
import com._360pai.applet.controller.account.resp.AccountBaseInfo;
import com._360pai.applet.controller.auction.vo.AnnouncementInfoVo;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.common.utils.AnnouncementUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.AlbumFacade;
import com._360pai.core.facade.activity.InstructionsContentFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.AlbumReq;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.assistant.NotifyPartyActivityFacade;
import com._360pai.core.facade.assistant.req.NotifyPartyActivityReq;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.shop.ShopFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 描述：小程序标的模块 controller
 * <p>
 * 作者： wuchuanqi
 * 版本： 1.0.0
 * 时间： 2018/11/26 11:08
 */
@Slf4j
@RestController
public class AuctionController extends AbstractController {

    @Reference(version = "1.0.0")
    private AuctionOrderFacade auctionOrderFacade;

    @Reference(version = "1.0.0")
    private ActivityFacade activityFacade;

    @Reference(version = "1.0.0")
    private InstructionsContentFacade instructionsContentFacade;

    @Reference(version = "1.0.0")
    private AssetFacade assetFacade;
    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;

    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;

    @Reference(version = "1.0.0")
    private NotifyPartyActivityFacade notifyPartyActivityFacade;

    @Reference(version = "1.0.0")
    private ShopFacade shopFacade;

    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;

    @Resource
    private RedisCachemanager redisCachemanager;
    @Reference(version = "1.0.0")
    AlbumFacade albumFacade;

    /**
     * 获取商品详情信息
     */
    @GetMapping(value = "/open/getAuctionDetail")
    public ResponseModel getAuctionDetail(AuctionReq.AuctionInfoReq req) {

        if (StringUtils.isEmpty(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        String shopId = req.getShopId();
        req.setShopId(StringUtils.isNotBlank(shopId)? shopId: accountBaseInfo.getShopId() == null?
                null: String.valueOf(accountBaseInfo.getShopId()));
        req.setAccountId(accountBaseInfo.getAccountId());
        return shopFacade.getAuctionDetail(req);


    }



    /**
     * 获取公告等信息
     */
    @GetMapping(value = "/open/getAnnouncement")
    public ResponseModel getAnnouncement(AuctionReq.AuctionInfoReq req) {
        if (StringUtils.isEmpty(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        InstructionsContentReq specialDetail = new InstructionsContentReq();
        specialDetail.setActivityId(Integer.valueOf(req.getAuctionId()));
        //特别告知
        String html = instructionsContentFacade.specialDetail(specialDetail);
        //获取模板的参数
        JSONObject json = getTemplateParam(req);
        //公告
        String announcementStr = getAnnouncementInfo(req,json);

        //须知
        String mustKnowStr = getMustKnowInfo(req,json);
        AnnouncementInfoVo vo = new AnnouncementInfoVo();
        vo.setAnnouncement(StringUtils.isEmpty(announcementStr)?"暂无数据":announcementStr);
        vo.setInform(StringUtils.isEmpty(html)?"暂无数据":html);
        vo.setInstructions(StringUtils.isEmpty(mustKnowStr)?"暂无数据":mustKnowStr);
        // 过滤字体大小
        if (StringUtils.isNotEmpty(vo.getAnnouncement())) {
            vo.setAnnouncement(vo.getAnnouncement().replaceAll("font-size: \\d*px;", ""));
        }
        // 过滤字体大小
        if (StringUtils.isNotEmpty(vo.getInform())) {
            vo.setInform(vo.getInform().replaceAll("font-size: \\d*px;", ""));
        }
        // 过滤字体大小
        if (StringUtils.isNotEmpty(vo.getInstructions())) {
            vo.setInstructions(vo.getInstructions().replaceAll("font-size: \\d*px;", ""));
        }
        return ResponseModel.succ(vo);
    }


    private String getMustKnowInfo(AuctionReq.AuctionInfoReq req, JSONObject json) {
        InstructionsContentReq activityIdReq = new  InstructionsContentReq();
        activityIdReq.setActivityId(Integer.valueOf(req.getAuctionId()));

        String mustKnowTemplate = instructionsContentFacade.buyerMustKnow(activityIdReq);
        String mustKnow = AnnouncementUtil.parse("{{", "}}", mustKnowTemplate, json);
        return mustKnow.replace("\t", "");

    }

    private String getAnnouncementInfo(AuctionReq.AuctionInfoReq req, JSONObject json) {
        InstructionsContentReq activityIdReq = new  InstructionsContentReq();
        activityIdReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        String announcementTemplate = instructionsContentFacade.announcement(activityIdReq);
        String announcement = AnnouncementUtil.parse("{{", "}}", announcementTemplate, json);
        return  announcement.replace("\t", "");

    }

    private JSONObject getTemplateParam(AuctionReq.AuctionInfoReq req) {
        //公告
        JSONObject json = new JSONObject();
        InstructionsContentReq activityIdReq = new InstructionsContentReq();
        activityIdReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        Object activity = instructionsContentFacade.getActivityById(activityIdReq);
        if (activity == null) {
            return json;
        }

        json = JsonUtil.toJSONObject(activity);

        String mode = json.getString("mode");
        Integer agencyId = json.getInteger("agencyId");
        if (StringUtils.isEmpty(mode)||agencyId == null) {
            return json;
        }
        AgencyResp agency = accountFacade.getAgencyById(agencyId);
        if (agency == null) {
            return json;
        }
        json.put("agencyName", agency.getName());

        Integer assetId = json.getInteger("assetId");
        if (assetId == null) {
            log.info("拍卖公告查询失败assetId={}", json);
            return json;
        }
        AssetResp asset = assetFacade.getAsset(assetId);
        json.put("handoverDays", asset.getAsset().getHandoverDays());
        json.put("payDays", asset.getAsset().getPayDays());
        json.put("contactName", asset.getAsset().getContactPerson().getName());
        json.put("contactPhone", asset.getAsset().getContactPerson().getMobile());

        Integer staffId = json.getInteger("staffId");
        if (staffId != null && staffId > 0) {
            StaffReq.BaseReq param = new StaffReq.BaseReq();
            param.setStaffId(staffId);
            StaffResp.BasicResp staffBasic = staffFacade.getStaffBasic(param);
            json.put("staffPhone", staffBasic.getMobile());
        }

        json.put("assetModeStr", ActivityEnum.ActivityMode.getKeyByValue(mode));



        return json;


    }

    /**
     * 获取出价记录列表
     */
    @GetMapping(value = "/open/getBidList")
    public ResponseModel getBidList(AuctionReq.AuctionInfoReq req) {
        if (StringUtils.isEmpty(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();

        ActivityReq.ActivityId activityIdReq = new ActivityReq.ActivityId();
        activityIdReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        activityIdReq.setPage(req.getPage());
        activityIdReq.setPerPage(req.getPerPage());
        activityIdReq.setPartyId(accountBaseInfo.getCurrentPartyId());
        Object object = auctionOrderFacade.bidOrder(activityIdReq);
        return ResponseModel.succ(object);
    }

    /**
     * 获取债权信息
     */
    @GetMapping(value = "/open/getAssetInfo")
    public ResponseModel getLead(AuctionReq.AuctionInfoReq req) {
        if (StringUtils.isEmpty(req.getAssetId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AssetReq.AddReq addReq = new AssetReq.AddReq();
        addReq.setAssetId(Integer.valueOf(req.getAssetId()));

        req.setSideType(SideType.PLATFORM);
        Map<String, Object> pageInfo = assetFacade.productDetail(addReq);

        return ResponseModel.succ(pageInfo);
    }




    /**
     * 关注/取消关注
     */
    @PostMapping(value = "/confined/favorAuction")
    public ResponseModel favorAuction(@RequestBody AuctionReq.AuctionInfoReq req) {
        if (StringUtils.isEmpty(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();
//        if(accountBaseInfo.getCurrentPartyId()==null){
//            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);
//
//        }
        ActivityReq.ActivityId activityReq = new ActivityReq.ActivityId();
        activityReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        activityReq.setPartyId(accountBaseInfo.getCurrentPartyId());
        String shopId = StringUtils.isBlank(req.getShopId()) ? "1" : req.getShopId();
        activityReq.setShopId(Integer.parseInt(shopId));

        activityReq.setType(AssetEnum.FavorType.SHOP.getKey());
        activityReq.setResourceId(Integer.parseInt(shopId));
        activityReq.setAccountId(accountBaseInfo.getAccountId());
        if("1".equals(req.getType())){
            activityFacade.activityFavor(activityReq);
        }else if("0".equals(req.getType())){
            activityFacade.unFavor(activityReq);
        }
        return ResponseModel.succ();
    }


    /**
     * 设置/取消提醒
     */
    @PostMapping(value = "/confined/notifyAuction")
    public ResponseModel notifyAuction(@RequestBody AuctionReq.AuctionInfoReq req) {
        if (StringUtils.isEmpty(req.getAuctionId())) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();

        if(accountBaseInfo.getCurrentPartyId()==null){
            return ResponseModel.fail(ApiCallResult.NO_AUTH_ACCOUNT);

        }

        NotifyPartyActivityReq.notifyReq notifyReq = new NotifyPartyActivityReq.notifyReq();

        notifyReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        notifyReq.setPartyId(accountBaseInfo.getCurrentPartyId());
        notifyReq.setPathType((short) 1);

        if("1".equals(req.getType())){

            notifyPartyActivityFacade.notifyMe(notifyReq);
         }else if("0".equals(req.getType())){

            notifyPartyActivityFacade.cancel(notifyReq, notifyReq.getPartyId());

        }
        return ResponseModel.succ();
    }

    /**
     * 功能描述: 店铺我的关注
     *
     * @param:
     * @return:
     * @auther: liuhaolei
     * @date: 2018/12/28 15:09
     */
    @GetMapping(value = "/confined/activity/myFavor")
    public ResponseModel myFavor(AuctionReq.AuctionInfoReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        PageInfoResp pageInfoResp = new PageInfoResp();
//        if (accountBaseInfo.getCurrentPartyId() == null) {
//            pageInfoResp.setList(Collections.EMPTY_LIST);
//            return ResponseModel.succ(pageInfoResp);
//        }
        req.setPartyId(accountBaseInfo.getCurrentPartyId());
        req.setAccountId(accountBaseInfo.getAccountId());
        PageInfo pageInfo = activityFacade.getMyShopFavor(req);
        pageInfoResp.setTotal(pageInfo.getTotal());
        pageInfoResp.setHasNextPage(pageInfo.isHasNextPage());
        pageInfoResp.setList(pageInfo.getList());
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 判断该活动是否被其他店铺关注
     * @param req
     * @return
     */
    @GetMapping(value = "/confined/activity/getConcernedFlag")
    public ResponseModel getConcernedFlag(AuctionReq.AuctionInfoReq req){
        if(StringUtils.isBlank(req.getAuctionId()) ) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo  accountBaseInfo = loadCurLoginAccountInfo();

        req.setShopId(String.valueOf(accountBaseInfo.getShopId()));

        return activityFacade.getConcernedFlag(req);
    }

    /**
     * 活动分享信息接口
     */
    @GetMapping(value = "/open/activity/share/info")
    public ResponseModel getShareInfo(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getType(), "type不能为空");
        req.setAppletFlag(true);
        if ("0".equals(req.getType())) {
            return activityFacade.getShareInfo(req);
        } else {
            return enrollingWebFacade.getShareInfo(req);
        }
    }

    /**
     * 首页拍卖专场列表
     */
    @GetMapping(value = "/open/album/sticky/list")
    public ResponseModel stickyList(AlbumReq.AlbumIdReq req) {
        return ResponseModel.succ(albumFacade.getStickyList(req));
    }
}
