package com._360pai.core.service.applet.impl;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.NumberToCN;
import com._360pai.arch.common.utils.ToolUtil;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.aspact.BusinessEmailService;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.common.constants.PartyEnum;
import com._360pai.core.condition.applet.TAppletShopCondition;
import com._360pai.core.dao.account.*;
import com._360pai.core.dao.activity.AuctionActivityDao;
import com._360pai.core.dao.applet.*;
import com._360pai.core.dao.asset.AssetDao;
import com._360pai.core.dao.assistant.StaffDao;
import com._360pai.core.dao.enrolling.EnrollingActivityDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.req.AccountReq;
import com._360pai.core.facade.account.vo.CompanyVo;
import com._360pai.core.facade.account.vo.UserVo;
import com._360pai.core.facade.activity.vo.AuctionActivityVo;
import com._360pai.core.facade.applet.req.AuctionReq;
import com._360pai.core.facade.applet.vo.AuctionDetailVo;
import com._360pai.core.facade.applet.vo.ShopUpdateApplyRecordVo;
import com._360pai.core.facade.applet.vo.ShopVo;
import com._360pai.core.facade.shop.req.ShopReq;
import com._360pai.core.facade.shop.vo.AppletHallActivityVO;
import com._360pai.core.model.account.*;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.applet.TAppletFavoriteShop;
import com._360pai.core.model.applet.TAppletHallActivity;
import com._360pai.core.model.applet.TAppletShop;
import com._360pai.core.model.applet.TAppletShopUpdateApplyRecord;
import com._360pai.core.model.assistant.Staff;
import com._360pai.core.service.account.AcctService;
import com._360pai.core.service.account.CompanyService;
import com._360pai.core.service.account.UserService;
import com._360pai.core.service.applet.AppletShopService;
import com._360pai.core.service.applet.TAppletMessageService;
import com._360pai.core.service.assistant.CommonService;
import com._360pai.core.service.assistant.SmsHelperService;
import com._360pai.core.utils.ReqConvertUtil;
import com._360pai.core.utils.RespConvertUtil;
import com._360pai.gateway.controller.req.wx.WXACodeUnLimitReq;
import com._360pai.gateway.facade.WxFacade;
import com._360pai.gateway.resp.wxpay.WXACodeUnLimitResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xdrodger
 * @Title: AppletShopServiceImpl
 * @ProjectName zeus
 * @Description:
 * @date 2018/11/26 17:05
 */
@Slf4j
@Service
public class AppletShopServiceImpl implements AppletShopService {

    @Autowired
    private TAppletShopDao shopDao;
    @Autowired
    private TAppletFavoriteShopDao favoriteShopDao;
    @Autowired
    private TAppletAuctionMapDao appletAuctionMapDao;
    @Reference(version = "1.0.0")
    private WxFacade wxFacade;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private TAppletShopUpdateApplyRecordDao updateApplyRecordDao;
    @Autowired
    private StaffDao staffDao;
    @Autowired
    private TCompanyDao companyDao;
    @Autowired
    private TUserDao userDao;
    @Autowired
    private TAccountDao accountDao;
    @Autowired
    private TAccountExtBindDao accountExtBindDao;
    @Autowired
    private TPartyDao partyDao;
    @Autowired
    private CommonService commonService;
    @Autowired
    private TAppletMessageService appletMessageService;
    @Autowired
    private AssetDao assetDao;
    @Autowired
    private UserService userService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private AuctionActivityDao activityDao;
    @Autowired
    private TAcctDao tAcctDao;
    @Autowired
    private AcctService acctService;
    @Autowired
    private AuctionActivityDao auctionActivityDao;
    @Autowired
    private TAppletHallActivityDao hallActivityDao;
    @Autowired
    private SmsHelperService smsHelperService;
    @Autowired
    private BusinessEmailService businessEmailService;
    @Autowired
    private TAppletEnrollingMapDao enrollingMapDao;
    @Autowired
    private TAppletAuctionMapDao auctionMapDao;
    @Autowired
    private EnrollingActivityDao enrollingActivityDao;

    @Transactional
    @Override
    public ResponseModel favor(ShopReq.BaseReq req) {
        TAppletShop shop = shopDao.selectById(req.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        TAppletFavoriteShop favoriteShop = favoriteShopDao.getByShopIdPartyId(req.getShopId(), req.getOpenId());
        if (favoriteShop == null) {
            favoriteShop = new TAppletFavoriteShop();
            favoriteShop.setShopId(req.getShopId());
            favoriteShop.setOpenId(req.getOpenId());
            int result = favoriteShopDao.insert(favoriteShop);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = shopDao.addFavoriteCount(shop.getId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        } else if (favoriteShop.getIsDelete()) {
            favoriteShop.setIsDelete(false);
            int result = favoriteShopDao.updateById(favoriteShop);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = shopDao.addFavoriteCount(shop.getId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return ResponseModel.succ();
    }

    @Transactional
    @Override
    public ResponseModel cancelFavor(ShopReq.BaseReq req) {
        TAppletFavoriteShop favoriteShop = favoriteShopDao.getByShopIdPartyId(req.getShopId(), req.getOpenId());
        if (favoriteShop != null && !favoriteShop.getIsDelete()) {
            favoriteShop.setIsDelete(true);
            int result = favoriteShopDao.updateById(favoriteShop);
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
            result = shopDao.subFavoriteCount(favoriteShop.getShopId());
            if (result == 0) {
                throw new BusinessException(ApiCallResult.FAILURE);
            }
        }
        return ResponseModel.succ();
    }

    @Override
    public PageInfoResp<ShopVo> getFavoriteShopList(ShopReq.BaseReq req) {
        PageInfoResp<ShopVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("openId", req.getOpenId());
        PageInfo pageInfo = favoriteShopDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        List<ShopVo> itemsList = new ArrayList<>();
        List<TAppletShop> list = pageInfo.getList();
        for (TAppletShop shop : list) {
            ShopVo vo = RespConvertUtil.convertToShopVo(shop);
            List<AuctionActivityVo> activityList = getTop3ActivityList(shop);
            vo.setActivityList(activityList);
            itemsList.add(vo);
        }
        resp.setList(itemsList);
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        return resp;
    }

    private List<AuctionActivityVo> getTop3ActivityList(TAppletShop shop) {
        List<AuctionActivityVo> activityList = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        PageInfo pageInfo;
        if (SystemConstant.DEFAULT_APPLET_SHOP.equals(shop.getId())) {
            pageInfo = auctionActivityDao.getPlatformActivityListByPage(1, 3, params, "");
        } else {
            params.put("shopId", shop.getId());
            pageInfo = appletAuctionMapDao.getActivityListByPage(1, 3, params, "");
        }
        List<AuctionActivity> auctionActivities = pageInfo.getList();
        for (AuctionActivity activity : auctionActivities) {
            activityList.add(RespConvertUtil.convertToAuctionActivityVo(activity));
        }
        return activityList;
    }


    @Override
    public ShopVo getShop(ShopReq.BaseReq req) {
        TAppletShop shop = shopDao.selectById(req.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (StringUtils.isEmpty(shop.getAppletQrCode())) {
            String appletQrCode = getAppletQrCode(req.getShopId() + "");
            if (StringUtils.isNotEmpty(appletQrCode)) {
                shop.setAppletQrCode(appletQrCode);
                shopDao.updateById(shop);
            }
        }
        if (StringUtils.isEmpty(shop.getInviteQrCode())) {
            String inviteQrCode = getInviteQrCode(req.getShopId() + "");
            if (StringUtils.isNotEmpty(inviteQrCode)) {
                shop.setInviteQrCode(inviteQrCode);
                shopDao.updateById(shop);
            }
        }
        ShopVo vo = RespConvertUtil.convertToShopVo(shop);
        vo.setInviteCode(AccountEnum.InviteType.DP.getKey() + shop.getId());
        vo.setMobile(ToolUtil.maskMobile(shop.getMobile()));


        if (req.getAccountId() != null) {
            TAccount account = accountDao.selectById(req.getAccountId());
            if (account.getShopId() != null && account.getShopId().equals(shop.getId())) {
                vo.setIsMyShop(true);
            }
        }
        if (!vo.getIsMyShop()) {
            vo.setIsFavorShop(favoriteShopDao.isFavorShop(req.getOpenId(), shop.getId()));
        }
        if (SystemConstant.DEFAULT_APPLET_SHOP.equals(shop.getId())) {
            vo.setAuctionProductCount(activityDao.countPlatformActivityNum());
            vo.setEnrollingProductCount(enrollingActivityDao.countPlatformActivityNum());
            vo.setProductCount(vo.getAuctionProductCount() + vo.getEnrollingProductCount());
            vo.setPageTitleName(shop.getName());
        } else {
            vo.setAuctionProductCount(auctionMapDao.countProduct(shop.getId()));
            vo.setEnrollingProductCount(enrollingMapDao.countProduct(shop.getId()));
            vo.setProductCount(vo.getAuctionProductCount() + vo.getEnrollingProductCount());
            if (vo.getIsMyShop()) {
                vo.setPageTitleName("我的资产小店");
            } else {
                vo.setPageTitleName("朋友的小店");
            }
        }
        return vo;
    }

    private String getAppletQrCode(String shopId) {
        WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
        if (systemProperties.getPutAppletHomePage()) {
            wxaCodeUnLimitReq.setPage(systemProperties.getShopAppletHomePage());
        }
        wxaCodeUnLimitReq.setScene(shopId);
        log.info("获取店铺小程序二维码，入参={}", JSON.toJSONString(wxaCodeUnLimitReq));
        WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
        if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
            log.error("获取店铺小程序二维码失败，入参={}，出参={}", JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
            return "";
        }
        return wxaCodeUnLimitResp.getImgUrl();
    }

    private String getInviteQrCode(String shopId) {
        WXACodeUnLimitReq wxaCodeUnLimitReq = new WXACodeUnLimitReq();
        if (systemProperties.getPutAppletHomePage()) {
            wxaCodeUnLimitReq.setPage(systemProperties.getShopInviteHomePage());
        }
        wxaCodeUnLimitReq.setScene(AccountEnum.InviteType.DP.getKey() + shopId);
        log.info("获取店铺小程序邀请二维码，入参={}", JSON.toJSONString(wxaCodeUnLimitReq));
        WXACodeUnLimitResp wxaCodeUnLimitResp = wxFacade.getWXACodeUnLimit(wxaCodeUnLimitReq);
        if (wxaCodeUnLimitResp == null || !wxaCodeUnLimitResp.getCode().equals("000")) {
            log.error("获取店铺小程序邀请二维码失败，入参={}，出参={}", JSON.toJSONString(wxaCodeUnLimitReq), JSON.toJSONString(wxaCodeUnLimitResp));
            return "";
        }
        return wxaCodeUnLimitResp.getImgUrl();
    }

    @Override
    public ResponseModel updateApply(ShopReq.UpdateApplyReq req) {
        TAppletShop shop = shopDao.selectById(req.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (updateApplyRecordDao.hasPendingApply(req.getShopId())) {
            throw new BusinessException("有审核中的申请，请耐心等待");
        }
        if (StringUtils.isEmpty(req.getName()) && StringUtils.isEmpty(req.getLogoUrl())) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        if (shop.getName().equals(req.getName()) && shop.getLogoUrl().equals(req.getLogoUrl())) {
            throw new BusinessException("没有变更无需修改");
        }
        TAppletShopUpdateApplyRecord applyRecord = ReqConvertUtil.convertToTAppletShopUpdateApplyRecord(req);
        applyRecord.setPartyId(shop.getPartyId());
        applyRecord.setOldName(shop.getName());
        applyRecord.setOldLogoUrl(shop.getLogoUrl());
        int result = updateApplyRecordDao.insert(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount account = accountDao.getByShopId(applyRecord.getShopId());
        if (account != null) {
            TAccountExtBind extBind = accountExtBindDao.findAppletByAccountId(account.getId());
            if (extBind != null) {
                businessEmailService.sendShopUpdateApply(StringEscapeUtils.unescapeJava(extBind.getNickName()));
            }
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel update(ShopReq.UpdateReq req) {
        TAppletShop shop = shopDao.selectById(req.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        shop = ReqConvertUtil.convertToTAppletShop(req);
        int result = shopDao.updateById(shop);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updateContactPhone(ShopReq.UpdateContactPhoneReq req) {
        TAppletShop shop = shopDao.selectById(req.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        shop.setContactPhone(req.getContactPhone());
        int result = shopDao.updateById(shop);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        return ResponseModel.succ();
    }

    @Transactional
    @Override
    public ResponseModel updateApplyApprove(ShopReq.BaseReq req) {
        TAppletShopUpdateApplyRecord applyRecord = updateApplyRecordDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setStatus(AccountEnum.ApplyStatus.APPROVED.getKey());
        int result = updateApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAppletShop shop = shopDao.selectById(applyRecord.getShopId());
        if (shop == null) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        shop.setName(applyRecord.getName());
        shop.setLogoUrl(applyRecord.getLogoUrl());
        result = shopDao.updateById(shop);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount account = accountDao.getByShopId(applyRecord.getShopId());
        if (account != null) {
            appletMessageService.sendShopUpdateApplyApproveMessage(account.getId());
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel updateApplyReject(ShopReq.BaseReq req) {
        TAppletShopUpdateApplyRecord applyRecord = updateApplyRecordDao.selectById(req.getApplyId());
        if (applyRecord == null) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        if (!AccountEnum.ApplyStatus.PENDING.getKey().equals(applyRecord.getStatus())) {
            throw new BusinessException(ApiCallResult.PARAMETER_INVALID);
        }
        //if (StringUtils.isEmpty(req.getReason())) {
        //    throw new BusinessException("原因不能为空");
        //}
        applyRecord.setOperatorId(req.getOperatorId());
        applyRecord.setStatus(AccountEnum.ApplyStatus.REJECT.getKey());
        applyRecord.setReason(req.getReason());
        int result = updateApplyRecordDao.updateById(applyRecord);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        TAccount account = accountDao.getByShopId(applyRecord.getShopId());
        if (account != null) {
            smsHelperService.shopUpdateApplyReject(account.getMobile());
        }
        return ResponseModel.succ();
    }

    @Override
    public ResponseModel getShopListByPage(ShopReq.QueryReq req) {
        Map<String, Object> params = new HashMap<>();
        params.put("partyType", req.getPartyType());
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getDefaultAgencyName())) {
            params.put("defaultAgencyName", req.getDefaultAgencyName());
        }
        if (StringUtils.isNotBlank(req.getInviteCode())) {
            params.put("inviteCode", req.getInviteCode());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        PageInfo pageInfo = shopDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        List<TAppletShop> list = pageInfo.getList();
        List<ShopVo> itemList = new ArrayList<>();
        for (TAppletShop shop : list) {
            PartyEnum.Type type = null;
            if (PartyEnum.Type.user.name().equals(req.getPartyType())) {
                type = PartyEnum.Type.user;
            }
            if (PartyEnum.Type.company.name().equals(req.getPartyType())) {
                type = PartyEnum.Type.company;
            }
            ShopVo vo = processTAppletShop(type, shop);
            itemList.add(vo);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("total", pageInfo.getTotal());
        data.put("hasNextPage", pageInfo.isHasNextPage());
        data.put("list", itemList);
        data.putAll(shopDao.getSummaryInfo(params));
        return ResponseModel.succ(data);
    }

    @Override
    public ShopVo getShopDetail(ShopReq.BaseReq req) {
        TAppletShop shop = shopDao.selectById(req.getId());
        PartyEnum.Type type = PartyEnum.Type.user;
        if (PartyEnum.Type.company.name().equals(req.getPartyType())) {
            type = PartyEnum.Type.company;
        }
        ShopVo vo = processTAppletShop(type, shop);
        return vo;
    }

    private ShopVo processTAppletShop(PartyEnum.Type type, TAppletShop shop) {
        ShopVo vo = RespConvertUtil.convertToShopVo(shop);
        vo.setInviteCount(shopDao.countShopInviteNum(shop.getId()));
        if (type != null && shop.getPartyId() != null) {
            AccountReq.BaseReq req = new AccountReq.BaseReq();
            req.setPartyId(shop.getPartyId());
            if (PartyEnum.Type.company.equals(type)) {
                CompanyVo company = companyService.getCompanyById(req);
                vo.setCompany(company);
                vo.setType(PartyEnum.Type.company.name());
            }
            if (PartyEnum.Type.user.equals(type)) {
                UserVo user = userService.getUserById(req);
                vo.setUser(user);
                vo.setType(PartyEnum.Type.user.name());
            }
        }
        if (shop.getPartyId() != null) {
            vo.setIsAccountAuth(true);
        }
        if (shop.getServiceCharge().compareTo(BigDecimal.ZERO) > 0) {
            vo.setIsPayServiceCharge(true);
        }
        TAccount account = accountDao.getByShopId(shop.getId());
        if (account != null) {
            vo.setRegisterTime(account.getCreateTime());
        }
        return vo;
    }

    @Override
    public PageInfoResp<ShopUpdateApplyRecordVo> getUpdateApplyRecordListByPage(ShopReq.QueryReq req) {
        PageInfoResp<ShopUpdateApplyRecordVo> resp = new PageInfoResp<>();
        Map<String, Object> params = new HashMap<>();
        params.put("partyType", req.getPartyType());
        if (StringUtils.isNotBlank(req.getQ())) {
            params.put("q", req.getQ());
        }
        if (StringUtils.isNotBlank(req.getStatus())) {
            params.put("status", req.getStatus());
        }
        if (StringUtils.isNotBlank(req.getInviteCode())) {
            params.put("inviteCode", req.getInviteCode());
        }
        if (StringUtils.isNotBlank(req.getCreatedAtFrom()) && StringUtils.isNotBlank(req.getCreatedAtTo())) {
            params.put("createdAtFrom", req.getCreatedAtFrom());
            params.put("createdAtTo", req.getCreatedAtTo());
        }
        PageInfo pageInfo = updateApplyRecordDao.getListByPage(req.getPage(), req.getPerPage(), params, "");
        List<TAppletShopUpdateApplyRecord> list = pageInfo.getList();
        List<ShopUpdateApplyRecordVo> itemList = new ArrayList<>();
        for (TAppletShopUpdateApplyRecord applyRecord : list) {
            PartyEnum.Type type = null;
            if (PartyEnum.Type.user.name().equals(req.getPartyType())) {
                type = PartyEnum.Type.user;
            }
            if (PartyEnum.Type.company.name().equals(req.getPartyType())) {
                type = PartyEnum.Type.company;
            }
            ShopUpdateApplyRecordVo vo = processTAppletShopUpdateApplyRecord(type, applyRecord);
            itemList.add(vo);
        }
        resp.setTotal(pageInfo.getTotal());
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setList(itemList);
        return resp;
    }

    @Override
    public ShopUpdateApplyRecordVo getUpdateApplyRecord(ShopReq.BaseReq req) {
        TAppletShopUpdateApplyRecord applyRecord = updateApplyRecordDao.selectById(req.getId());
        PartyEnum.Type type = null;
        if (PartyEnum.Type.user.name().equals(req.getPartyType())) {
            type = PartyEnum.Type.user;
        }
        if (PartyEnum.Type.company.name().equals(req.getPartyType())) {
            type = PartyEnum.Type.company;
        }
        ShopUpdateApplyRecordVo vo = processTAppletShopUpdateApplyRecord(type, applyRecord);
        return vo;
    }

    @Transactional
    @Override
    public ResponseModel createShop(ShopReq.CreateReq req) {
        log.info("创建店铺，req={}", JSON.toJSONString(req));
        if (StringUtils.isEmpty(req.getOpenId())) {
            throw new BusinessException(ApiCallResult.EMPTY);
        }
        TAccountExtBind accountExtBind = accountExtBindDao.findAppletByOpenId(req.getOpenId());
        if (accountExtBind == null) {
            throw new BusinessException("创建店铺，openId未绑定");
        }
        if (accountExtBind.getAccountId() == null) {
            throw new BusinessException("创建店铺，accountId未绑定");
        }
        TAccount account = accountDao.selectById(accountExtBind.getAccountId());
        if (account == null) {
            throw new BusinessException("创建店铺，accountId未绑定不存在");
        }
        if (account.getShopId() != null) {
            throw new BusinessException("创建店铺，accountId已绑定店铺");
        }
        TAppletShop shop = new TAppletShop();
        shop.setPartyId(req.getPartyId());
        shop.setName(accountExtBind.getNickName() + "的小店");
        shop.setLogoUrl(accountExtBind.getHeadImgUrl());
        try {
            shop.setLogoUrl(commonService.saveExternalImgUrl(accountExtBind.getHeadImgUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        shop.setInviteCode(accountExtBind.getInviteCode());
        shop.setInviteType(accountExtBind.getInviteType());
        shop.setServiceCharge(req.getAmount());
        shop.setMobile(account.getMobile());
        shop.setContactPhone(account.getMobile());
        int result = shopDao.insert(shop);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        account.setShopId(shop.getId());
        result = accountDao.updateById(account);
        if (result == 0) {
            throw new BusinessException(ApiCallResult.FAILURE);
        }
        Integer partyId = shop.getId();
        String type = AccountEnum.AcctType.SHOP.getKey();
        if (req.getPartyId() != null) {
            TParty party = partyDao.selectById(req.getPartyId());
            if (party == null) {
                throw new BusinessException("创建店铺，partyId不存在");
            }
            partyId = party.getId();
            type = party.getType();
        } else {
            TUser user = userDao.getByAccountId(account.getId());
            if (user != null) {
                partyId = user.getId();
                type = AccountEnum.AcctType.USER.getKey();
            } else {
                TCompany company = companyDao.getLatestByAccountId(account.getId());
                if (company != null) {
                    partyId = company.getId();
                    type = AccountEnum.AcctType.COMPANY.getKey();
                }
            }
        }
        acctService.saveAcctIfNeed(partyId, type);
        appletMessageService.sendOpenShopSuccessMessage(accountExtBind.getAccountId());
        appletMessageService.sendSubordinateOpenShopSuccessMessage(accountExtBind.getAccountId());
        return ResponseModel.succ(shop.getId());
    }



    private ShopUpdateApplyRecordVo processTAppletShopUpdateApplyRecord(PartyEnum.Type type, TAppletShopUpdateApplyRecord applyRecord) {
        ShopUpdateApplyRecordVo vo = RespConvertUtil.convertToShopUpdateApplyRecordVo(applyRecord);
        if (applyRecord.getOperatorId() != null) {
            Staff staff = staffDao.selectById(applyRecord.getOperatorId());
            if (staff != null) {
                vo.setOperator(staff.getName());
            }
        }
        TAppletShop shop = shopDao.selectById(applyRecord.getShopId());
        if (type != null && shop.getPartyId() != null) {
            AccountReq.BaseReq req = new AccountReq.BaseReq();
            req.setPartyId(shop.getPartyId());
            if (PartyEnum.Type.company.equals(type)) {
                CompanyVo company = companyService.getCompanyById(req);
                vo.setCompany(company);
                vo.setType(PartyEnum.Type.company.name());
            }
            if (PartyEnum.Type.user.equals(type)) {
                UserVo user = userService.getUserById(req);
                vo.setUser(user);
                vo.setType(PartyEnum.Type.user.name());
            }
        }
        vo.setInviteCode(shop.getInviteType() + shop.getInviteCode());
        return vo;
    }


    @Override
    public AuctionDetailVo getAppletAuctionDetail(AuctionReq.AuctionInfoReq req) {
        Map<String, Object> params = new HashMap<>();
        params.put("activityId",req.getAuctionId());

        return appletAuctionMapDao.getAppletAuctionDetail(params);
    }


    @Override
    public void updateShopById(TAppletShop req) {
        shopDao.updateById(req);
    }

    @Override
    public TAppletShop getAppletShopInfo(TAppletShopCondition req) {
        return shopDao.selectFirst(req);
    }

    @Override
    public TAppletShop getAppletShopById(Integer id) {
        return shopDao.selectById(id);
    }

    @Override
    public PageInfoResp<AppletHallActivityVO> getAppletHallActivityList(ShopReq.ShopListReq req) {
        PageInfoResp<AppletHallActivityVO> resp = new PageInfoResp<>();
        List<AppletHallActivityVO> resultList = new ArrayList<>();
        PageInfo<AppletHallActivityVO> pageInfo = hallActivityDao.getFrontList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (AppletHallActivityVO item : pageInfo.getList()) {
            if (item.getPreviewAt() != null) {
                item.setPreviewAtTimestamp(item.getPreviewAt().getTime());
            }
            if (item.getBeginAt() != null) {
                item.setBeginAtTimestamp(item.getBeginAt().getTime());
            }
            if (item.getEndAt() != null) {
                item.setEndAtTimestamp(item.getEndAt().getTime());
            }
            if (item.getIncrementAt() != null) {
                item.setIncrementAtTimestamp(item.getIncrementAt().getTime());
            }
            item.setCurrentTimestamp(System.currentTimeMillis());
            item.setStatusDesc(getActivityStatusDesc(item));
            item.setStatus(getActivityStatus(item));
            item.setAmountDesc(getActivityAmountDesc(item));
            item.setCategoryName(getActivityCategoryName(item));
            if ("0".equals(item.getActivityType())) {
                item.setModeDesc(ActivityEnum.ActivityMode.getKeyByValue(item.getMode()));
            }
            resultList.add(item);
        }
        resp.setList(resultList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    @Override
    public PageInfoResp<ShopVo> getInvitedShopListByPage(ShopReq.QueryReq req) {
        PageInfoResp<ShopVo> resp = new PageInfoResp<>();
        List<ShopVo> itemList = new ArrayList<>();
        PageInfo<TAppletShop> pageInfo = shopDao.getInvitedList(req.getPage(), req.getPerPage(), (Map<String, Object>) JSON.toJSON(req), "");
        for (TAppletShop item : pageInfo.getList()) {
            ShopVo vo = RespConvertUtil.convertToShopVo(item);
            vo.setInviteCount(shopDao.countShopInviteNum(item.getId()));
            if (item.getPartyId() != null) {
                TParty party = partyDao.selectById(item.getPartyId());
                if (party != null) {
                   vo.setType(party.getType());
                }
            } else {
                vo.setType(SystemConstant.ACCOUNT_COMMON_TYPE);
            }
            TAccount account = accountDao.getByShopId(item.getId());
            if (account != null) {
                vo.setRegisterTime(account.getCreateTime());
            }
            itemList.add(vo);
        }
        resp.setList(itemList);
        resp.setHasNextPage(pageInfo.isHasNextPage());
        resp.setTotal(pageInfo.getTotal());
        return resp;
    }

    private String getActivityStatus(AppletHallActivityVO activity) {
        if ("0".equals(activity.getActivityType())) {
            if (activity.getStatus().equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), activity.getBeginAt()) < 0) {
                return ActivityEnum.OnlineStatus.UPCOMING.getKey();
            } else if (activity.getStatus().equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), activity.getBeginAt()) > 0) {
                return ActivityEnum.OnlineStatus.SALE.getKey();
            } else if (activity.getStatus().equals(ActivityEnum.Status.SUCCESS.getKey())) {
                return ActivityEnum.OnlineStatus.SUCCESS.getKey();
            } else if (activity.getStatus().equals(ActivityEnum.Status.FAILED.getKey())) {
                return ActivityEnum.OnlineStatus.FAILED.getKey();
            }
        } else {
            if (EnrollingEnum.STATUS.ONLINE.getType().equals(activity.getStatus())) {
                return ActivityEnum.OnlineStatus.SALE.getKey();
            } else if (EnrollingEnum.STATUS.FINISHED.getType().equals(activity.getStatus())) {
                return ActivityEnum.OnlineStatus.FAILED.getKey();
            } else if (EnrollingEnum.STATUS.CLOSED.getType().equals(activity.getStatus())) {
                return ActivityEnum.OnlineStatus.FAILED.getKey();
            }
        }

        return "";
    }

    private String getActivityStatusDesc(AppletHallActivityVO activity) {
        if ("0".equals(activity.getActivityType())) {
            if (activity.getStatus().equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), activity.getBeginAt()) < 0) {
                return ActivityEnum.OnlineStatus.UPCOMING.getValue();
            } else if (activity.getStatus().equals(ActivityEnum.Status.ONLINE.getKey()) && DateUtil.getMarginMin(new Date(), activity.getBeginAt()) > 0) {
                return ActivityEnum.OnlineStatus.SALE.getValue();
            } else if (activity.getStatus().equals(ActivityEnum.Status.SUCCESS.getKey())) {
                return "成交";
            } else if (activity.getStatus().equals(ActivityEnum.Status.FAILED.getKey())) {
                return "结束";
            }
        } else {
            return EnrollingEnum.STATUS.getDesc(activity.getStatus());
        }

        return "";
    }

    private String getActivityAmountDesc(AppletHallActivityVO activity) {
        if ("0".equals(activity.getActivityType())) {
            return "起拍价";
        } else {
            if ("1".equals(activity.getActivityType())) {
                return "市场参考价";
            } else if ("2".equals(activity.getActivityType())) {
                return "债权本金";
            }  else if ("3".equals(activity.getActivityType())) {
                return "市场参考价";
            }
        }
        return "";
    }

    private String getActivityCategoryName(AppletHallActivityVO activity) {
        if ("0".equals(activity.getActivityType())) {
            return activity.getCategoryName();
        } else {
            if ("1".equals(activity.getActivityType())) {
                return "抵押物预招商";
            } else if ("2".equals(activity.getActivityType())) {
                return "债权招商";
            }  else if ("3".equals(activity.getActivityType())) {
                return "物权招商";
            }
        }
        return "";
    }
}
