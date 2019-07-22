package com._360pai.web.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.facade.disposal.DisposalRequirementFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.core.facade.order.resp.ServiceOrderResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-09-17 10:11
 */
@RestController
public class DisposalRequirementController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisposalRequirementController.class);
    @Reference(version = "1.0.0")
    ServiceFollowFacade       serviceFollowFacade;
    @Reference(version = "1.0.0")
    DisposalRequirementFacade disposalRequirementFacade;
    @Reference(version = "1.0.0")
    AssetFacade               assetFacade;
    @Reference(version = "1.0.0")
    ServiceConfigFacade       serviceConfigFacade;

    /**
     * 描述  处置服务价格
     *
     * @author : whisky_vip
     * @date : 2018/10/10 16:57
     */
    @PostMapping("/open/disposal/getDisposalPrice")
    public ResponseModel getDisposalPrice() {
        String price = serviceConfigFacade.selectByConfigType(ServiceConfigEnum.DISPOSAL_REQUIREMENT_PRICE);
        Map<String,BigDecimal> result = Maps.newHashMap();
        result.put("disposalPrice",new BigDecimal(price));
        return ResponseModel.succ(result);
    }

    /**
     * 描述 处置服务专区 首页 热门标的 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:23
     */
    @PostMapping("/open/disposal/getHotRequirementList")
    public ResponseModel getHotRequirementList(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        PageInfoResp pageInfoResp = disposalRequirementFacade.findHotBidList(req);
        return ResponseModel.succ(pageInfoResp);
    }



    /**
     * 描述 首页 招商列表
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/open/disposal/getDisposalRequirementList")
    public ResponseModel getDisposalRequirementList(
            @RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        PageInfoResp pageInfoResp = disposalRequirementFacade.findDisposalRequirementListPage(req);
        return ResponseModel.succ(pageInfoResp);
    }


    /**
     * 描述 同类推荐 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:27
     */
    @PostMapping("/open/disposal/getSimilarRecommendList")
    public ResponseModel getSimilarRecommendList(@RequestBody DisposalBiddingReq.GetBiddingInfoReq req) {
        PageInfoResp similarList = disposalRequirementFacade.findSimilarBidListPage(req);
        return ResponseModel.succ(similarList);
    }


    /**
     * 描述 个人中心查看已发布的处置需求
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:36
     */
    @PostMapping("/confined/disposal/getPublishRequirementList")
    public ResponseModel getPublishRequirementList(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Assert.notNull(accountBaseInfo.getPartyPrimaryId(), "身份未认证");
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setSideType(SideType.PLATFORM);
        PageInfoResp pageInfoResp = disposalRequirementFacade.findPublishedListPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 描述 上传处置需求单
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:37
     */
    @PostMapping("/confined/disposal/addRequirement")
    public ResponseModel addDisposalRequirement(@RequestBody DisposalRequirementReq.AddRequirementInfo req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Assert.notNull(accountBaseInfo.getPartyPrimaryId(), "身份未认证");
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        checkAddParam(req);
        Object resp = disposalRequirementFacade.addDisposalRequirement(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 查看报名情况
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:36
     */
    @PostMapping("/confined/disposal/getBiddingSituation")
    public ResponseModel getBiddingSituation(@RequestBody DisposalRequirementReq.GetBiddingList req) {
        Assert.notNull(req.getDisposalId(), "disposalId 不能为空");
        req.setSideType(SideType.PLATFORM);
        PageInfoResp pageInfoResp
                = disposalRequirementFacade.findBiddingSituation(req);
        return ResponseModel.succ(pageInfoResp);
    }


    /**
     * 描述 服务处置 关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:38
     */
    @PostMapping(value = "/confined/disposal/serviceFollowAdd")
    public ResponseModel disposalServiceFollowAdd(@RequestBody ServiceFollowReq.Add req) {
//        isAuth();
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        req.setRequirementId(req.getDisposalId().toString());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.DIPOSAL.getKey());
        int count = serviceFollowFacade.serviceFollowAdd(req);
        if (count > 0) {
            disposalRequirementFacade.updateFollowCount(req.getRequirementId());
        }
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述 服务处置  取消关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:39F
     */
    @PostMapping(value = "/confined/disposal/serviceFollowRemove")
    public ResponseModel disposalServiceFollowRemove(@RequestBody ServiceFollowReq.Remove req) {
//        isAuth();
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        req.setRequirementId(req.getDisposalId().toString());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.DIPOSAL.getKey());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        int count = serviceFollowFacade.serviceFollowRemove(req);
        disposalRequirementFacade.updateFollowCount(req.getRequirementId());
        return ResponseModel.succ(count);
    }

    @PostMapping(value = "/open/disposal/getRequirementDetail")
    public ResponseModel getRequirementDetail(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Map<String, Object> detail =
                disposalRequirementFacade.findDisposalRequirementDetail(req.getDisposalId(), accountBaseInfo.getPartyPrimaryId(),accountBaseInfo.getAccountId());
        disposalRequirementFacade.updateViewCount(req.getDisposalId());
        return ResponseModel.succ(detail);
    }

    /**
     * 描述 服务处置  批量取消关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:39
     */
    @PostMapping(value = "/confined/disposal/serviceFollowRemoveList")
    public ResponseModel disposalServiceFollowRemoveList(@RequestBody ServiceFollowReq.Remove req) {
//        isAuth();
        if (null == req.getDisposalIds() || req.getDisposalIds().length == 0) {
            return ResponseModel.succ();
        }
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.DIPOSAL.getKey());
        int count = serviceFollowFacade.serviceFollowRemoveList(req);
        return ResponseModel.succ(count);
    }

    /**
     * 描述 我的关注 处置服务
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/confined/disposal/getDisposalFollowList")
    public ResponseModel getDisposalFollowList(
            @RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
//        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());
        PageInfoResp pageInfoResp = disposalRequirementFacade.findDisposalFollowListPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 处置中心获取处置详情
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/confined/disposal/getRequirementCenterDetail")
    public ResponseModel getRequirementCenterDetail(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        isAuth();
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Map<String, Object> detail =
                disposalRequirementFacade.findDisposalRequirementCenterDetail(req.getDisposalId(),
                        accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(detail);
    }

    /**
     * 处置中心获取可修改的处置详情
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/confined/disposal/getRequirementCenterDetailEdit")
    public ResponseModel getRequirementCenterDetailEdit(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        isAuth();
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Map<String, Object> detail =
                disposalRequirementFacade.findDisposalRequirementCenterDetailEdit(req.getDisposalId(),
                        accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(detail);
    }

    @PostMapping(value = "/confined/disposal/getRequirementAssetDetail")
    public ResponseModel getRequirementAssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "assetId 不能为空");
        Object object = assetFacade.assetDetail(req);
        return ResponseModel.succ(object);
    }

    @GetMapping("/confined/disposal/getProviderSupportType")
    public ResponseModel getProviderSupportType() {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        String[] type = disposalRequirementFacade.getDisposeProviderType(accountBaseInfo.getPartyPrimaryId());
        Map<String, Object> map = new HashMap<>();
        map.put("supportType", type);
        return ResponseModel.succ(map);
    }

    private void checkAddParam(DisposalRequirementReq.AddRequirementInfo req) {

        AssetResp asset = assetFacade.getAsset(req.getAssetId());
        // 本人上传时以下字段不能为空
        if ( AssetEnum.BusType.DISPOSAL.getKey().equals(asset.getAsset().getBusType().toString())) {
            if (StringUtils.isBlank(req.getDisposalName())) {
                throw new BusinessException(ApiCallResult.EMPTY.getCode(),"需求名称不能为空");
            }
            if (req.getProviderAreas() == null || req.getProviderAreas().length == 0) {
                throw new BusinessException(ApiCallResult.EMPTY,"处置所在地不能为空");
            }
            if (req.getExtra() == null || req.getExtra().length == 0) {
                throw new BusinessException(ApiCallResult.EMPTY,"预展图片不能为空");
            }
            // 前端数组最后一个为空需要处理
            String[] tmp = new String[req.getExtra().length - 1];
            System.arraycopy(req.getExtra(), 0, tmp, 0, tmp.length);
            req.setExtra(tmp);
        }
        if (null == req.getDisposalTypes() || req.getDisposalTypes().length <= 0) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "选择需要的服务不能为空");
        }
        if (null == req.getPeriod() || req.getPeriod() < 0) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "期望处置周期不能为空");
        }
        if (req.getPeriod().compareTo(new Double(999.99)) > 0) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "请正确填写期望处置周期");
        }
        if (StringUtils.isBlank(req.getCost())) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "可接受服务费用不能为空");
        }
        if (null == req.getAssetId() || req.getAssetId().intValue() < 0) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "标的不能为空");
        }
        if (null == req.getDeadline()) {
            throw new BusinessException(ApiCallResult.EMPTY.getCode(), "截至日期不能为空");
        }
        req.setDeadline(DateUtil.getEndDate(req.getDeadline()));

        for (String tmp : req.getDisposalTypes()) {
            if (!DisposalEnum.RequirementType.JINDIAO.getKey().equals(tmp)
                    && !DisposalEnum.RequirementType.PINGGU.getKey().equals(tmp)) {
                if (StringUtils.isBlank(req.getCaseDescription())) {
                    throw new BusinessException(ApiCallResult.EMPTY.getCode(), "案件描述不能为空");
                }
                if (StringUtils.isBlank(req.getRequireDescription())) {
                    throw new BusinessException(ApiCallResult.EMPTY.getCode(), "需求描述不能为空");
                }
            }
        }
    }


}
