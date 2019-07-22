package com._360pai.core.provider.shop;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.FilterRichTextUtil;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.arch.core.redis.RedisLock;
import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.core.aspact.ExceptionEmailService;
import com._360pai.core.aspact.GatewayMqSender;
import com._360pai.core.common.constants.*;
import com._360pai.core.condition.applet.TAppletAuctionMapCondition;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.condition.assistant.PlatformAnnouncementCondition;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;
import com._360pai.core.facade.account.resp.AccountBaseDto;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.vo.BidRecord;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.applet.vo.AppletSearchAuctionActivityVo;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.applet.vo.ShopUpdateApplyRecordVo;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.assistant.CommonFacade;
import com._360pai.core.facade.payment.AuctionOrderFacade;
import com._360pai.core.facade.shop.ShopFacade;
import com._360pai.core.facade.shop.dto.SearchRecordListDto;
import com._360pai.core.facade.shop.dto.ShopAuctionSearchDto;
import com._360pai.core.facade.shop.dto.ShopEnrollingSearchDto;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.*;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TParty;
import com._360pai.core.model.applet.*;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.core.model.assistant.PlatformAnnouncement;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.service.account.AccountService;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.account.AgencyService;
import com._360pai.core.service.account.PartyService;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.applet.*;
import com._360pai.core.service.assistant.*;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.core.utils.ServiceActivityUtils;
import com._360pai.core.vo.assistant.ProvinceCityVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageSerializable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author: liuhaolei
 * @Title: ShopProvider
 * @ProjectName: core-service
 * @Description: 小程序店铺生产者
 * @Date: 2018-11-22
 */
@Service(version = "1.0.0")
@Slf4j
public class ShopProvider implements ShopFacade {

    @Autowired
    private TAppletAuctionMapService tAppletAuctionMapService;
    @Autowired
    private AuctionActivityService auctionActivityService;
    @Autowired
    private TAppletSearchRecordService tAppletSearchRecordService;
    @Autowired
    private TAppletMessageService tAppletMessageService;
    @Autowired
    private TAppletLeadService tAppletLeadService;
    @Autowired
    private AppletShopService shopService;
    @Autowired
    private PlatformAnnouncementService platformAnnouncementService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AcctService acctService;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Autowired
    private DepositService depositService;
    @Autowired
    private PartyService partyService;

    @Reference(version = "1.0.0")
    private AuctionOrderFacade auctionOrderFacade;
    @Reference(version = "1.0.0")
    protected CommonFacade commonFacade;
    @Autowired
    private NotifyPartyActivityDao notifyPartyActivityDao;
    @Autowired
    private FavoriteActivityDao favoriteActivityDao;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private CityService  cityService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private AuctionActivityDao activityDao;
    @Autowired
    private GatewayMqSender mqSender;

    @Autowired
    private ExceptionEmailService exceptionEmailService;

    @Resource
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private FavoriteActivityService favoriteActivityService;
    @Autowired
    private NotifyPartyActivityService notifyPartyActivityService;
    @Autowired
    private TAppletEnrollingMapService appletEnrollingMapService;
    @Autowired
    private ServiceActivityUtils        serviceActivityUtils;

    @Override
    public ResponseModel getSearchAuctionList(ShopReq.ShopListReq shopListReq) {

        ShopAuctionSearchDto params = new ShopAuctionSearchDto();
        BeanUtils.copyProperties(shopListReq, params);

        PageInfo auctionPageInfo = tAppletAuctionMapService.getSearchAuctionList(params);
        PageInfoResp auctionResp = new PageInfoResp(auctionPageInfo);

        return ResponseModel.succ(auctionResp);
    }

    @Override
    @Transactional
    public ResponseModel setHomePage(ShopReq.HomePageReq homePageReq) {
        tAppletAuctionMapService.updateAllAuciton(homePageReq.getShopId());

        tAppletAuctionMapService.setHomePage(homePageReq);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel outOfstocks(ShopReq.OutOfStocks outOfStocks) {

        upOrOutOfStocks(null,outOfStocks);

        return ResponseModel.succ();
    }

    @Override
    @Transactional
    public ResponseModel upOfStocks(ShopReq.UpOfStocks upOfStocks) {

        //上架或下架商品 添加分布式锁 故合成一个方法
        upOrOutOfStocks(upOfStocks,null);

        return ResponseModel.succ();
    }

    private void upOrOutOfStocks(ShopReq.UpOfStocks upOfStocks, ShopReq.OutOfStocks outOfStocks) {

        String shopId = upOfStocks==null?outOfStocks.getShopId():upOfStocks.getShopId();

        String key = "appletUpOrOutOfStocks:{" + shopId + "}";

        RedisLock lock = getRedisLock(key);

        try {

            if(lock.lock()){
                //上架商品
                if(outOfStocks==null){

                    saveUpOfStocks(upOfStocks);
                    //下架商品
                }else {
                    tAppletAuctionMapService.batchDeleteStocks(outOfStocks);
                    updateShopProductCount(outOfStocks.getShopId());
                }
            }

        }catch (Exception e){


            lock.unlock();

            exceptionEmailService.sendExceptionEmail(ExceptionEmailEnum.IMPORTANT_LEVEL.SERIOUS,ExceptionEmailEnum.MODULE_TYPE.APPLET,"小程序设置上架下架异常",key,exceptionEmailService.exceptionToStr(e));

            log.info("出现异常，释放redis锁，key为{}", shopId);

        }finally {
            lock.unlock();
        }

    }

    private RedisLock getRedisLock(String key) {


        return new RedisLock(redisTemplate, key, 3000, 1000);


    }

    private void saveUpOfStocks(ShopReq.UpOfStocks upOfStocks) {

        Map<String, Object> params;
        List<TAppletAuctionMap> saveTAppletAuctionMaps = new ArrayList<>();
        List<Integer> updateStocks = new ArrayList<>();

        for(int i = 0; i < upOfStocks.getUpOfStocks().length; i++) {
            params = new HashMap<>();
            params.put("shopId", upOfStocks.getShopId());
            params.put("activityId",upOfStocks.getUpOfStocks()[i]);
            TAppletAuctionMap tAppletAuctionMap;
            tAppletAuctionMap = tAppletAuctionMapService.getAppletAuctionByParams(params);

            if(tAppletAuctionMap != null) {
                updateStocks.add(tAppletAuctionMap.getAuctionId());
            }else {
                tAppletAuctionMap = new TAppletAuctionMap();
                tAppletAuctionMap.setPushDesc(0);
                tAppletAuctionMap.setAuctionId(Integer.parseInt(upOfStocks.getUpOfStocks()[i]));
                tAppletAuctionMap.setCreateTime(new Date());
                tAppletAuctionMap.setUpdateTime(new Date());
                tAppletAuctionMap.setIsDel(String.valueOf(0));
                tAppletAuctionMap.setshopId(upOfStocks.getShopId());
                tAppletAuctionMap.setStatus(String.valueOf(0));
                saveTAppletAuctionMaps.add(tAppletAuctionMap);
            }
        }

        //已经存在的进行修改
        if(updateStocks.size() > 0) {
            params = new HashMap<>();
            params.put("shopId", upOfStocks.getShopId());
            params.put("upOfStocks", updateStocks.toArray());
            tAppletAuctionMapService.batchUpdateStocks(params, updateStocks.toArray().length);
        }

        //未存在的进行新增
        if(saveTAppletAuctionMaps.size() > 0) {
            tAppletAuctionMapService.batchUpOfAuction(saveTAppletAuctionMaps,upOfStocks.getShopId());
        }

        //修改商品的数量
        updateShopProductCount(upOfStocks.getShopId());

    }

    private void updateShopProductCount(String shopId) {
        //获取店铺上拍的数量
        TAppletAuctionMapCondition condition = new TAppletAuctionMapCondition();
        condition.setShopId(shopId);
        condition.setIsDel("0");
        condition.setStatus("0");

        Integer enrollingCount = appletEnrollingMapService.getShopEnrollingSize(shopId);

        Integer auctionCount = tAppletAuctionMapService.getAppletAuctionList(condition).size();
        TAppletShop tAppletShop = new TAppletShop();
        tAppletShop.setId(Integer.valueOf(shopId));
        tAppletShop.setProductCount(auctionCount + enrollingCount);
        tAppletShop.setUpdateTime(DateUtil.getSysTime());

        shopService.updateShopById(tAppletShop);

    }

    @Override
    public ResponseModel getSearchRecordList(ShopReq.SearchRecordReq searchRecordReq) {
        SearchRecordListDto params = new SearchRecordListDto();
        BeanUtils.copyProperties(searchRecordReq, params);

        PageInfo pageInfo = tAppletSearchRecordService.getSearchRecordList(params);
        List<SearchRecordListVO> homeList = pageInfo.getList();

        PageSerializable page = new PageSerializable();
        page.setList(homeList);
        page.setTotal(pageInfo.getTotal());

        return ResponseModel.succ(page);
    }

    @Override
    public ResponseModel deleteSearchRecord(ShopReq.DeleteRecordReq deleteRecordReq) {

        Map<String, Object> params = new HashMap<>();
        params.put("shopId", deleteRecordReq.getShopId());
        params.put("records", deleteRecordReq.getRecords());
        params.put("deleteType", deleteRecordReq.getDeleteType());
        tAppletSearchRecordService.deleteSearchRecord(params);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getShopMessageList(ShopReq.ShopMessageReq shopMessageReq) {
        PageInfo pageInfo = null;
        List<AppletMessageVO> homeList = null;

        if(ShopEnum.MessageTypeEnum.MESSAGE_TYPE.getTypeNum().equals(shopMessageReq.getType())) {
            pageInfo = tAppletMessageService.getShopMessageList(shopMessageReq);
            homeList = pageInfo.getList();

        }else if(ShopEnum.MessageTypeEnum.ANNOUNCEMENT_TYPE.getTypeNum().equals(shopMessageReq.getType())){
            pageInfo = tAppletMessageService.getAnnoucementList(shopMessageReq);
            homeList = pageInfo.getList();

        }
        //过滤富文本标签
        for(AppletMessageVO vo:homeList){
            vo.setContext(FilterRichTextUtil.getTextFromHtml(vo.getContext()));
        }

        PageInfoResp page   = new PageInfoResp<>();
        page.setList(homeList);
        page.setTotal(pageInfo.getTotal());
        page.setHasNextPage(pageInfo.isHasNextPage());
        return ResponseModel.succ(page);
    }



    private List<AppletMessageVO> getHomeList(List<PlatformAnnouncement> platformAnnouncements) {
        List<AppletMessageVO> homeList =  new ArrayList<>();
        if(platformAnnouncements != null && platformAnnouncements.size() > 0) {
            for (PlatformAnnouncement platformAnnouncement:platformAnnouncements) {
                AppletMessageVO appletMessageVO = new AppletMessageVO();
                appletMessageVO.setContext(platformAnnouncement.getDetail());
                appletMessageVO.setCreateTime(DateUtil.formatYYYYDate(platformAnnouncement.getPublicAt()));
                appletMessageVO.setId(platformAnnouncement.getId());
                appletMessageVO.setType(ShopEnum.MessageTypeEnum.getTypeNumByType(platformAnnouncement.getCategory()));
                appletMessageVO.setName(platformAnnouncement.getTitle());

                homeList.add(appletMessageVO);
            }
        }

        return homeList;
    }

    @Override
    public ResponseModel getShopMessageDetail(ShopReq.ShopMessageDetailReq shopMessageDetailReq) {
        ResponseModel result = new ResponseModel().warpSuccess().setTimestamp(System.currentTimeMillis());
        if(ShopEnum.MessageTypeEnum.MESSAGE_TYPE.getTypeNum().equals(shopMessageDetailReq.getType())) {
            TAppletMessage tAppletMessage = tAppletMessageService.getShopMessageById(shopMessageDetailReq.getMessageId());
            result.setContent(tAppletMessage, PropertyFilterFactory.
                    create(new String[]{"shopId", "openId", "isDel", "updateTime"}));

        }else if(ShopEnum.MessageTypeEnum.ANNOUNCEMENT_TYPE.getTypeNum().equals(shopMessageDetailReq.getType())){
            PlatformAnnouncementCondition condition = new PlatformAnnouncementCondition();
            condition.setCategory(ShopEnum.MessageTypeEnum.getTypeByNum(shopMessageDetailReq.getType()));
            condition.setId(Integer.parseInt(shopMessageDetailReq.getMessageId()));
            PlatformAnnouncement platformAnnouncement = platformAnnouncementService.getDetailById(condition);

            TAppletMessage tAppletMessage = new TAppletMessage();
            tAppletMessage.setContext(platformAnnouncement.getDetail());
            tAppletMessage.setCreateTime(platformAnnouncement.getPublicAt());
            tAppletMessage.setName(platformAnnouncement.getTitle());
            tAppletMessage.setType(ShopEnum.MessageTypeEnum.getTypeNumByType(platformAnnouncement.getCategory()));
            tAppletMessage.setId(platformAnnouncement.getId());

            result.setContent(tAppletMessage, PropertyFilterFactory.
                    create(new String[]{"shopId", "openId", "isDel", "updateTime"}));
        }

        TAppletReadMessageMap tAppletReadMessageMap = tAppletMessageService.getShopReadMessageBy(shopMessageDetailReq);
        if(tAppletReadMessageMap == null) {
            tAppletReadMessageMap = new TAppletReadMessageMap();
            tAppletReadMessageMap.setMessageId(Integer.parseInt(shopMessageDetailReq.getMessageId()));
            tAppletReadMessageMap.setShopId(Integer.parseInt(shopMessageDetailReq.getShopId()));
            tAppletReadMessageMap.setOpenId(shopMessageDetailReq.getOpenId());
            tAppletReadMessageMap.setCreateTime(new Date());
            tAppletReadMessageMap.setType(Byte.parseByte(shopMessageDetailReq.getType()));
            tAppletReadMessageMap.setUpdateTime(new Date());

            tAppletMessageService.saveTAppletReadMessage(tAppletReadMessageMap);
        }
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getRemainingGuides(ShopReq.ShopGuidesReq shopGuidesReq) {
        List<ShopGuideVO> shopGuideVOS = tAppletLeadService.getRemainingGuides(shopGuidesReq.getOpenId());

        Map<String, Object> result = new HashMap<>();
        result.put("list", shopGuideVOS);
        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel updateRemainingGuide(ShopReq.ShopGuideUpdateReq shopGuideUpdateReq) {

        TAppletLead tAppletLead = new TAppletLead();
        tAppletLead.setCreateTime(new Date());
        tAppletLead.setUpdateTime(new Date());
        tAppletLead.setStatus(ShopEnum.ShopGuideStatus.HAS_READ.getStatus());
        tAppletLead.setType(shopGuideUpdateReq.getType());
        tAppletLead.setOpenId(shopGuideUpdateReq.getOpenId());

        tAppletLeadService.saveRemainingGuide(tAppletLead);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel favor(ShopReq.BaseReq req) {
        return shopService.favor(req);
    }

    @Override
    public ResponseModel cancelFavor(ShopReq.BaseReq req) {
        return shopService.cancelFavor(req);
    }

    @Override
    public PageInfoResp<ShopVo> getFavoriteShopList(ShopReq.BaseReq req) {
        return shopService.getFavoriteShopList(req);
    }

    @Override
    public ShopVo getShop(ShopReq.BaseReq req) {
        return shopService.getShop(req);
    }

    @Override
    public ResponseModel updateApply(ShopReq.UpdateApplyReq req) {
        return shopService.updateApply(req);
    }

    @Override
    public ResponseModel update(ShopReq.UpdateReq req) {
        return shopService.update(req);
    }

    @Override
    public ResponseModel updateContactPhone(ShopReq.UpdateContactPhoneReq req) {
        return shopService.updateContactPhone(req);
    }

    @Override
    public ResponseModel updateApplyApprove(ShopReq.BaseReq req) {
        return shopService.updateApplyApprove(req);
    }

    @Override
    public ResponseModel updateApplyReject(ShopReq.BaseReq req) {
        return shopService.updateApplyReject(req);
    }

    @Override
    public ResponseModel getShopAuctionList(ShopReq.ShopListReq shopListReq) {
        ShopAuctionSearchDto params = new ShopAuctionSearchDto();
        BeanUtils.copyProperties(shopListReq, params);
        if(StringUtils.isNotEmpty(shopListReq.getStartingPrice())){
            params.setBeginPrice(AppletEnum.QUERY_PRICE.getBeginPrice(shopListReq.getStartingPrice()));
            params.setEndPrice(AppletEnum.QUERY_PRICE.getEndPriceByType(shopListReq.getStartingPrice()));
        }
        if (null != params.getCityId()) {
            if (params.getCityId().intValue() < 0) {
                params.setProvinceId(-params.getCityId());
                params.setCityId(null);
            }
        }
        List<String> pushIds = tAppletAuctionMapService.getHotPushIds(params);

        PageInfo pageInfo = tAppletAuctionMapService.searchShopAuctionList(params);
        List<AppletSearchAuctionActivityVo> list = pageInfo.getList();

        PageInfoResp<AppletSearchAuctionActivityVo> resp  = new PageInfoResp<>();
        resp.setList(list);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());

        Map<String, Object> result = new HashMap<>();
        result.put("pageDetail", resp);
        result.put("pushIds", pushIds);
        result.put("fixedTotal",getShopInfo(shopListReq.getShopId()));
        return ResponseModel.succ(result);
    }

    private Object getShopInfo(String shopId) {



        if (String.valueOf(SystemConstant.DEFAULT_APPLET_SHOP).equals(shopId)) {
            return  activityDao.countPlatformActivityNum();
        }

        TAppletShop shop = shopService.getAppletShopById(Integer.valueOf(shopId));
        if (shop == null) {
            return 0;
        }
        return shop.getProductCount();
    }

    @Override
    public ResponseModel getMyShopMessage(ShopReq.ShopMessageReq shopMessageReq) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shopMessageReq.getShopId());
        params.put("openId", shopMessageReq.getOpenId());
        List<ShopMessageTypeVO> shopMessageTypeVOS = tAppletMessageService.getMyShopMessage(params);

        HashMap<String, Object> result = new HashMap<>();
        result.put("list", shopMessageTypeVOS);

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getShopListByPage(ShopReq.QueryReq req) {
        return shopService.getShopListByPage(req);
    }

    @Override
    public ShopVo getShopDetail(ShopReq.BaseReq req) {
        return shopService.getShopDetail(req);
    }

    @Override
    public PageInfoResp<ShopUpdateApplyRecordVo> getUpdateApplyRecordListByPage(ShopReq.QueryReq req) {
        return shopService.getUpdateApplyRecordListByPage(req);
    }

    @Override
    public ShopUpdateApplyRecordVo getUpdateApplyRecord(ShopReq.BaseReq req) {
        return shopService.getUpdateApplyRecord(req);
    }

    @Override
    public ResponseModel createShop(ShopReq.CreateReq req) {
        log.info("开店支付完成，req={}", JSON.toJSONString(req));
        ResponseModel resp = shopService.createShop(req);
        Integer shopId = (Integer) resp.getContent();
        try {

            if(req.getPartyId() != null){
                AccountBaseDto accountBaseDto = accountService.getAccountBaseByPartyId(req.getPartyId());
                TAcct acct = acctService.findAcctByPartyIdAndType(req.getPartyId(),accountBaseDto.getType());

                acctService.addAcctAmount(acct.getId(), req.getAmount(), AccountEnum.AcctOperateType.RECHARGE.getKey(), null,null);
                if (accountBaseDto != null && accountBaseDto.getParentId() != null) {

                    TAcct parentAcct = acctService.findAcctByPartyIdAndType(accountBaseDto.getParentId(),accountBaseDto.getParentType());

                    acctService.addAcctAmount(parentAcct.getId(), req.getAmount().multiply(accountBaseDto.getShopCommissionPercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP), AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey(), acct.getId() + "",shopId);
                }
            }else {
                TAcct acct = acctService.findAcctByPartyIdAndType(shopId,AccountEnum.AcctType.SHOP.getKey());
                acctService.addAcctAmount(acct.getId(), req.getAmount(), AccountEnum.AcctOperateType.RECHARGE.getKey(), null,null);
                TAppletShop appletShop = shopService.getAppletShopById(shopId);
                if(appletShop.getInviteType().equals(AccountEnum.InviteType.JG.getKey())){
                    TAgency tAgency = agencyService.findByAgencyId(appletShop.getInviteCode());
                    TAcct agencyAcct = acctService.findAcctByPartyIdAndType(tAgency.getId(),AccountEnum.AcctType.AGENCY.getKey());
                    acctService.addAcctAmount(agencyAcct.getId(), req.getAmount().multiply(tAgency.getShopCommissionPercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP), AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey(), acct.getId() + "",shopId);
                }else{
                    TAppletShop parentShop = shopService.getAppletShopById(appletShop.getInviteCode());
                    if(parentShop.getPartyId() == null){
                        TAcct parentShopAcct = acctService.findAcctByPartyIdAndType(parentShop.getId(),AccountEnum.AcctType.SHOP.getKey());
                        acctService.addAcctAmount(parentShopAcct.getId(), req.getAmount().multiply(parentShop.getShopCommissionPercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP), AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey(), acct.getId() + "",shopId);
                    }else{
                        TParty party = partyService.findPartyById(parentShop.getPartyId());
                        TAcct parentPartyAcct = acctService.findAcctByPartyIdAndType(party.getId(),party.getType());
                        acctService.addAcctAmount(parentPartyAcct.getId(), req.getAmount().multiply(parentShop.getShopCommissionPercent()).divide(new BigDecimal(100),2,BigDecimal.ROUND_HALF_UP), AccountEnum.AcctOperateType.SHOP_INVITED_COMMISSION.getKey(), acct.getId() + "",shopId);
                    }
                }
            }
            TAppletShop appletShop = shopService.getAppletShopById(shopId);
            if (AccountEnum.InviteType.JG.getKey().equals(appletShop.getInviteType())) {
                smsHelperService.arrivalReminderNotify(accountService.getAgencyNotifierMobile(appletShop.getInviteCode()));
            } else if (AccountEnum.InviteType.DP.getKey().equals(appletShop.getInviteType())) {
                smsHelperService.arrivalReminderNotify(accountService.getShopNotifierMobile(appletShop.getInviteCode()));
                tAppletMessageService.sendArrivalReminderMessage(appletShop.getInviteCode());
            }

        } catch (Exception e) {
            e.printStackTrace();
            mqSender.sendTryCatchExceptionEmail(req.getPartyId(), e);
        }
        return resp;
    }


    @Override
    public ResponseModel getAuctionDetail(AuctionReq.AuctionInfoReq req) {
        AuctionDetailVo vo = shopService.getAppletAuctionDetail(req);

        if(vo==null){
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }

        ShopEnum.NewOnlineStatus onlineStatus = RespConvertUtil.getOnlineStatus(vo.getStatus(), DateUtil.strToDateLong(vo.getBeginAt()));

        vo.setModeStr(ActivityEnum.ActivityMode.getKeyByValue(vo.getActivityMode()));
        vo.setCompeteUrl(gatewayProperties.getAppletCompeteUrl());
        vo.setStatus(onlineStatus.getKey());
        vo.setStatusDesc(onlineStatus.getValue());
        vo.setPmPhone(StringUtils.isEmpty(vo.getPmPhone())?"400-015-0005":vo.getPmPhone());
        vo.setDepositCount(String.valueOf(depositService.countParticipantNumber(Integer.valueOf(req.getAuctionId()))));
        vo.setBidPrice("无");
        vo.setBidPerson("无");
        vo.setStartingPrice(getStartingPrice(vo));
        vo.setPreemptivePerson(StringUtils.isEmpty(vo.getPreemptivePerson())?"无":"有");
        vo.setReservePrice(StringUtils.isEmpty(vo.getReservePrice())?"无":"有");
        vo.setViewCount(StringUtils.isEmpty(vo.getViewCount())?"0":vo.getViewCount());
        vo.setIncrement(StringUtils.isEmpty(vo.getIncrement())?null:vo.getIncrement());
        vo.setIsDealPerson(false);
        addBidRecord(vo,req);//添加出价记录
        addAssetCategory(vo,req);//添加标的属性
        addRemarkStatus(vo,req);//提醒状态
        addFavor(vo,req);//关注属性
        addCityName(vo,req);//添加省份城市信息
        addDealPerson(vo,req);//登录人是否是成交人
        vo.setHasExist(hasExist(req));//是否已经添加拍品
        vo.setBeginTimestamp(DateUtil.strToDateLong(vo.getBeginAt()).getTime());
        vo.setEndTimestamp(DateUtil.strToDateLong(vo.getEndAt()).getTime());
        if (StringUtils.isNotEmpty(vo.getIncrementAt())) {
            vo.setIncrementTimestamp(DateUtil.strToDateLong(vo.getIncrementAt()).getTime());
        }
        vo.setCurrentTimestamp(System.currentTimeMillis());
        // 过滤字体大小
        if (StringUtils.isNotEmpty(vo.getDetail())) {
            vo.setDetail(vo.getDetail().replaceAll("font-size: \\d*px;", ""));
        }
        return ResponseModel.succ(vo);
    }

    private String getStartingPrice(AuctionDetailVo vo) {
        //当为一口价明标是  起拍价去保留价
        if(ActivityEnum.ActivityMode.PUBLIC.getKey().equals(vo.getActivityMode())||ActivityEnum.ActivityMode.SEALED.getKey().equals(vo.getActivityMode())){
            return vo.getReservePrice();
        }

        return vo.getStartingPrice();
    }

    private Boolean hasExist(AuctionReq.AuctionInfoReq req) {
        if(StringUtils.isEmpty(req.getShopId())){
            return true;
        }

        TAppletAuctionMapCondition params = new TAppletAuctionMapCondition();
        params.setShopId(req.getShopId());
        params.setAuctionId(Integer.valueOf(req.getAuctionId()));
        params.setIsDel("0");
        return tAppletAuctionMapService.getAppletAuctionByCondition(params)!=null;
    }

    @Override
    public ResponseModel getSearchDetails() {
        Map jsonObject = new HashMap();
        jsonObject.put("assetCategory", getAssetCategory());//拍品的类型
        jsonObject.put("activityCities", getCitiesInfo());//获取查询的省份 城市信息
        jsonObject.put("priceList", getPriceInfo());//查询的起拍价格
        jsonObject.put("activityStatus", getActivityStatus());//商品的转态
        jsonObject.put("activityMode", getActivityMode());//委托方分类

        return ResponseModel.succ(jsonObject);
    }

    @Override
    public ResponseModel getHomeRecommend(ShopReq.ShopListReq req) {
        return ResponseModel.succ(shopService.getAppletHallActivityList(req));
    }

    private Object getActivityMode() {
        List<AppletQueryInfoVO> modeListMap = new ArrayList<>();

        modeListMap.add(new AppletQueryInfoVO("1","银行"));
        modeListMap.add(new AppletQueryInfoVO("2","AMC"));
        modeListMap.add(new AppletQueryInfoVO("3","民营资管"));
        modeListMap.add(new AppletQueryInfoVO("4","个人"));
        modeListMap.add(new AppletQueryInfoVO("5","拍卖公司"));
        modeListMap.add(new AppletQueryInfoVO("6","一般公司"));

        return modeListMap;

    }


    private Object getActivityStatus() {
        List<AppletQueryInfoVO> statusListMap = new ArrayList<>();

        statusListMap.add(new AppletQueryInfoVO("UPCOMING","即将开拍"));
        statusListMap.add(new AppletQueryInfoVO("SALE","正在拍卖"));
        statusListMap.add(new AppletQueryInfoVO("SUCCESS","成交"));
        statusListMap.add(new AppletQueryInfoVO("FAILED","结束"));
        return statusListMap;

    }

    private Object getPriceInfo() {

        List<AppletQueryInfoVO> priceList = new ArrayList<>();

        priceList.add(new AppletQueryInfoVO("1","1百万以下"));
        priceList.add(new AppletQueryInfoVO("2","1百万-9百万"));
        priceList.add(new AppletQueryInfoVO("3","1千万-9千万"));
        priceList.add(new AppletQueryInfoVO("4","1亿以上"));

        return priceList;

    }

    private Object getAssetCategory() {
        List<AppletQueryInfoVO> categoryListMap = new ArrayList<>();

        categoryListMap.add(new AppletQueryInfoVO("2","债权"));
        categoryListMap.add(new AppletQueryInfoVO("16","物权"));
        return categoryListMap;

    }

    private Object getCitiesInfo() {


        List<Province> provinceList = provinceService.getAllProvince();

        List<AppletQueryInfoVO> statusListMap = new ArrayList<>();

        for(Province p :provinceList){
            statusListMap.add(new AppletQueryInfoVO(String.valueOf(p.getId()),p.getName())) ;
        }
        return statusListMap;
    }

    private void addDealPerson(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {

        if(req.getPartyId()!=null&&vo.getBuyerId()!=null&&vo.getBuyerId().equals(req.getPartyId())){
            vo.setIsDealPerson(true);
        }
    }

    private void addCityName(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {

        String strCity="";

        List<String> ids = getCityIds(vo.getCityId());
        if(ids==null||ids.size()<1){
            return;
        }
        //获取省份城市信息
        List<ProvinceCityVo> cityVoList = cityService.getProvinceCityList(ids);

        for(ProvinceCityVo city :cityVoList){
            strCity = strCity+city.getProvinceName()+" "+city.getCityName()+"、";
        }

        vo.setCityName(StringUtils.isNotEmpty(strCity)?strCity.substring(0,strCity.length()-1):strCity);
    }

    private List<String> getCityIds(String cityId) {

        try{

            List list = Arrays.asList(cityId.split(","));

            return list;

        }catch (Exception e){
            log.error("转换城市id异常，异常信息为：{}",e);

            return null;

        }



    }

    private void addFavor(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {

        vo.setFavorStatus(false);
        if(req.getAccountId()!=null){
            FavoriteActivity favoriteActivity = favoriteActivityDao.selectShopFavorActivityId(req.getPartyId(), Integer.valueOf(req.getAuctionId()), req.getShopId(), req.getAccountId());
            vo.setFavorStatus(favoriteActivity!=null);

        }
//        FavoriteActivityCondition condition = new FavoriteActivityCondition();
//        condition.setActivityId(Integer.valueOf(req.getAuctionId()));
//        List<FavoriteActivity> favoriteActivities = favoriteActivityDao.selectList(condition);
//        vo.setFavorCount(String.valueOf(CollectionUtils.isEmpty(favoriteActivities) ? 0 : favoriteActivities.size()));
        vo.setFavorCount(serviceActivityUtils.getFocusNum(Integer.valueOf(req.getAuctionId()))+"");

    }

    private void addRemarkStatus(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {
        vo.setRemarkStatus(false);

        if(req.getAccountId()!= null){
            NotifyPartyActivity notifyPartyActivity = notifyPartyActivityDao.selectByPartyIdAndActivityId(req.getPartyId(), Integer.valueOf(req.getAuctionId()), req.getAccountId());
            vo.setRemarkStatus(notifyPartyActivity!=null);
        }

    }

    private void addAssetCategory(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {
        vo.setAssetCategoryName("债权拍卖");
        vo.setAssetCategoryType("1");

        if("16".equals(vo.getAssetCategoryGroupId())){
            vo.setAssetCategoryName("物权拍卖");
            vo.setAssetCategoryType("2");
        }

    }


    private void addBidRecord(AuctionDetailVo vo, AuctionReq.AuctionInfoReq req) {

        //获取出价记录
        BidRecord bidRecord = getBidRecord(req);
        if(bidRecord!=null){
            vo.setBidPrice(bidRecord.getAmount());
            vo.setBidPerson(bidRecord.getBidderName());
        }
    }


    private BidRecord getBidRecord(AuctionReq.AuctionInfoReq req) {

        ActivityReq.ActivityId bidOrderReq = new ActivityReq.ActivityId();
        bidOrderReq.setPartyId(req.getPartyId());
        bidOrderReq.setActivityId(Integer.valueOf(req.getAuctionId()));
        Object bidOrder = auctionOrderFacade.bidOrder(bidOrderReq);
        if(bidOrder==null){
            return null;
        }

        PageInfoResp<BidRecord> object = (PageInfoResp<BidRecord>)bidOrder;

        List<BidRecord> bidRecordList = object.getList();
        if(bidRecordList.size()<1){
            return null;
        }

        return bidRecordList.get(0);
    }


    @Override
    public ResponseModel getShopWebEnrollingList(ShopReq.EnrollingReq req) {
        ShopEnrollingSearchDto params = new ShopEnrollingSearchDto();
        BeanUtils.copyProperties(req, params);

        PageInfo pageInfo = appletEnrollingMapService.getShopWebEnrollingList(params);

        PageInfoResp pageInfoResp = new PageInfoResp(pageInfo);
        return ResponseModel.succ(pageInfoResp);
    }

    @Override
    public ResponseModel getShopEnrollingList(ShopReq.EnrollingReq req) {
        ShopEnrollingSearchDto params = new ShopEnrollingSearchDto();
        BeanUtils.copyProperties(req, params);
        if (null != params.getCityId()) {
            if (params.getCityId().intValue() < 0) {
                params.setProvinceId(-params.getCityId());
                params.setCityId(null);
            }
        }
        PageInfo pageInfo = null;
        String webId = "1";
        List<String> pushIds = appletEnrollingMapService.getHotPushIds(params);

        if(StringUtils.isNotEmpty(req.getBeginPrice())){
            params.setBeginPrice(AppletEnum.QUERY_PRICE.getBeginPrice(req.getBeginPrice()));
            params.setEndPrice(AppletEnum.QUERY_PRICE.getEndPriceByType(req.getBeginPrice()));
        }

        if(req.getShopId() == null||webId.equals(req.getShopId().toString())) {
            pageInfo = appletEnrollingMapService.getShopWebEnrollingList(params);
        }else{
            pageInfo = appletEnrollingMapService.getShopEnrollingList(params);
        }

        PageInfoResp pageInfoResp = new PageInfoResp(pageInfo);

        Map<String, Object> result = new HashMap<>();
        result.put("pageDetail", pageInfoResp);
        result.put("pushIds", pushIds);

        return ResponseModel.succ(result);
    }

    @Override
    @Transactional
    public ResponseModel upOrDownShopEnrolling(ShopReq.UpOrDownEnrollingReq req) {

        List<TAppletEnrollingMap> updateTAppletEnrollingMaps = new ArrayList<TAppletEnrollingMap>();
        List<TAppletEnrollingMap> saveTAppletEnrollingMaps = new ArrayList<TAppletEnrollingMap>();

        getSaveOrUpdateList(updateTAppletEnrollingMaps, saveTAppletEnrollingMaps, req);

        if(saveTAppletEnrollingMaps.size() > 0) {
            appletEnrollingMapService.saveBatchAppletEnrollingList(saveTAppletEnrollingMaps);

        }else if(updateTAppletEnrollingMaps.size() > 0) {
            appletEnrollingMapService.updateBatchAppletEnrollingList(updateTAppletEnrollingMaps);
        }

        //修改小程序
        ShopReq.UpOfStocks upOfStocks = new ShopReq.UpOfStocks();
        upOfStocks.setShopId(req.getShopId());
        updateShopProductCount(upOfStocks.getShopId());
        return ResponseModel.succ();
    }

    @Override
    @Transactional
    public ResponseModel setEnrollingHomePage(ShopReq.HomePageReq homePageReq) {
        appletEnrollingMapService.deleteEnrollingHomePage(homePageReq.getShopId());
        appletEnrollingMapService.setEnrollingHomePage(homePageReq);

        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getEnrollingSearchParams() {
        Map<String, Object> result = new HashMap<>();

        result.put("enrollingTypeArray", getTypeArray());
        result.put("enrollingStatusArray", getStatusArray());
        result.put("enrollingAllPrice", getPriceArray());
        result.put("allCity", getCitiesInfo());

        return ResponseModel.succ(result);
    }

    @Override
    public ResponseModel getSearchAuctionAndEnrollingList(ShopReq.AuctionEnrollingReq shopListReq) {

        ShopEnrollingSearchDto enrollingParams = new ShopEnrollingSearchDto();
        BeanUtils.copyProperties(shopListReq, enrollingParams);

        if(StringUtils.isNotBlank(shopListReq.getQuery())) {
            TAppletSearchRecord tAppletSearchRecord =
                    tAppletSearchRecordService.selectTAppletSearchRecord(shopListReq.getQuery(), shopListReq.getOpenId(), shopListReq.getShopId());
            saveOrUpdateSearchRecord(tAppletSearchRecord, shopListReq.getShopId(),
                    shopListReq.getOpenId(), shopListReq.getQuery());
        }

        PageInfo searchAuctionAndEnrollingList = appletEnrollingMapService.getSearchAuctionAndEnrollingList(enrollingParams);
        PageInfoResp enrollingResp  = new PageInfoResp(searchAuctionAndEnrollingList);

        return ResponseModel.succ(enrollingResp);
    }

    @Override
    public PageInfoResp<ShopVo> getInvitedShopListByPage(ShopReq.QueryReq req) {
        return shopService.getInvitedShopListByPage(req);
    }


    private void saveOrUpdateSearchRecord(TAppletSearchRecord tAppletSearchRecord,
                                          Integer shopId, String openId, String query) {
        if(tAppletSearchRecord == null) {
            tAppletSearchRecord = new TAppletSearchRecord();
            tAppletSearchRecord.setContext(query);
            tAppletSearchRecord.setShopId(shopId);
            tAppletSearchRecord.setOpenId(openId);
            tAppletSearchRecord.setIsDel(false);
            tAppletSearchRecord.setCreateTime(new Date());
            tAppletSearchRecord.setUpdateTime(new Date());

            tAppletSearchRecordService.saveSearchRecord(tAppletSearchRecord);
        }else {
            tAppletSearchRecord.setUpdateTime(new Date());
            tAppletSearchRecord.setIsDel(false);
            tAppletSearchRecordService.updateSearchRecord(tAppletSearchRecord);
        }
    }

    private List<AppletQueryInfoVO> getStatusArray() {
        List<AppletQueryInfoVO> statusListMap = new ArrayList<>();
        statusListMap.add(new AppletQueryInfoVO("ONLINE","正在报名"));
        statusListMap.add(new AppletQueryInfoVO("FINISHED","报名结束"));
        statusListMap.add(new AppletQueryInfoVO("CLOSED","招商结束"));
        return statusListMap;

    }

    private List<AppletQueryInfoVO> getTypeArray( ) {
        List<AppletQueryInfoVO> typeListMap = new ArrayList<>();
        typeListMap.add(new AppletQueryInfoVO("2","债权"));
        typeListMap.add(new AppletQueryInfoVO("3","物权"));
        typeListMap.add(new AppletQueryInfoVO("1","抵押物"));
        return typeListMap;
    }


    private List<AppletQueryInfoVO> getPriceArray() {

        List<AppletQueryInfoVO> priceList = new ArrayList<>();

        priceList.add(new AppletQueryInfoVO("1","1百万以下"));
        priceList.add(new AppletQueryInfoVO("2","1百万-9百万"));
        priceList.add(new AppletQueryInfoVO("3","1千万-9千万"));
        priceList.add(new AppletQueryInfoVO("4","1亿以上"));

        return priceList;
    }

    private void getSaveOrUpdateList(List<TAppletEnrollingMap> updateTAppletEnrollingMaps, List<TAppletEnrollingMap> saveTAppletEnrollingMaps, ShopReq.UpOrDownEnrollingReq req) {
        TAppletEnrollingMap tAppletEnrollingMap = null;
        String[] outOfStocks = req.getOutOfStocks();
        for (int i = 0; i < outOfStocks.length; i++) {
            tAppletEnrollingMap = appletEnrollingMapService.
                    getEnrollingByShopParams(req.getShopId(), outOfStocks[i], null,  null);
            if("0".equals(req.getOutFlag())) {
                if(tAppletEnrollingMap == null) {
                    tAppletEnrollingMap = new TAppletEnrollingMap();
                    tAppletEnrollingMap.setCreateTime(new Date());
                    tAppletEnrollingMap.setEnrollingId(Integer.parseInt(outOfStocks[i]));
                    tAppletEnrollingMap.setShopId(req.getShopId());
                    tAppletEnrollingMap.setUpdateTime(new Date());
                    tAppletEnrollingMap.setIsDel("0");
                    tAppletEnrollingMap.setStatus("0");

                    saveTAppletEnrollingMaps.add(tAppletEnrollingMap);
                }else{
                    tAppletEnrollingMap.setStatus("0");
                    tAppletEnrollingMap.setIsDel("0");
                    tAppletEnrollingMap.setUpdateTime(new Date());
                    updateTAppletEnrollingMaps.add(tAppletEnrollingMap);
                }
            }else {

                tAppletEnrollingMap = appletEnrollingMapService.
                        getEnrollingByShopParams(req.getShopId(), outOfStocks[i], "0", "0");

                if(tAppletEnrollingMap == null) {
                    tAppletEnrollingMap = appletEnrollingMapService.
                            getEnrollingByShopParams(req.getShopId(), outOfStocks[i], "0", "1");
                }

                if("1".equals(req.getDeleteFlag())) {
                    tAppletEnrollingMap.setUpdateTime(new Date());
                    tAppletEnrollingMap.setIsDel("1");

                }else {
                    tAppletEnrollingMap.setUpdateTime(new Date());
                    tAppletEnrollingMap.setStatus("1");
                }

                updateTAppletEnrollingMaps.add(tAppletEnrollingMap);
            }
        }
    }
}
