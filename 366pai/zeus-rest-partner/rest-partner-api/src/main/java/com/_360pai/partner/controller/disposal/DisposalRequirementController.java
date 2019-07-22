package com._360pai.partner.controller.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.disposal.DisposalRequirementFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
     * 描述 上传处置需求单
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:37
     */
    @PostMapping("/agency/disposal/addRequirement")
    public ResponseModel addDisposalRequirement(@RequestBody DisposalRequirementReq.AddRequirementInfo req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setSideType(SideType.AGENCY);
        checkAddParam(req);
        Object resp = disposalRequirementFacade.addDisposalRequirement(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 个人中心查看已发布的处置需求
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:36
     */
    @PostMapping("/agency/disposal/getPublishRequirementList")
    public ResponseModel getPublishRequirementList(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setSideType(SideType.AGENCY);
        PageInfoResp pageInfoResp = disposalRequirementFacade.findPublishedListPage(req);
        return ResponseModel.succ(pageInfoResp);
    }

    /**
     * 处置中心获取处置详情
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:26
     */
    @PostMapping("/agency/disposal/getRequirementCenterDetail")
    public ResponseModel getRequirementCenterDetail(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        try {
            Map<String, Object> detail =
                    disposalRequirementFacade.findDisposalRequirementCenterDetail(req.getDisposalId(),
                            accountBaseInfo.getAgencyId());
            return ResponseModel.succ(detail);
        } catch (Exception e) {
            LOGGER.error("处置中心获取需求详情异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    /**
     * 描述 查看报名情况
     *
     * @author : whisky_vip
     * @date : 2018/9/20 13:36
     */
    @PostMapping("/agency/disposal/getBiddingSituation")
    public ResponseModel getBiddingSituation(@RequestBody DisposalRequirementReq.GetBiddingList req) {
        Assert.notNull(req.getDisposalId(), "disposalId 不能为空");
        try {
            req.setSideType(SideType.AGENCY);
            PageInfoResp pageInfoResp
                    = disposalRequirementFacade.findBiddingSituation(req);
            return ResponseModel.succ(pageInfoResp);
        } catch (Exception e) {
            LOGGER.error("获取投标信息异常，异常信息为：", e);
            return ResponseModel.fail();
        }
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
