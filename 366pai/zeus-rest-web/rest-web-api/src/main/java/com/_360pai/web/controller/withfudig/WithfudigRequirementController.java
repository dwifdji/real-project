package com._360pai.web.controller.withfudig;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.common.constants.WithfudigEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.asset.TAssetCateGoryFacade;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.facade.withfudig.WithfudigRequirementFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述 配资乐 配置需求单
 *
 * @author : whisky_vip
 * @date : 2018/8/28 10:34
 */
@Slf4j
@RestController
public class WithfudigRequirementController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigRequirementFacade withfudigRequirementFacade;
    @Reference(version = "1.0.0")
    ServiceFollowFacade        serviceFollowFacade;
    @Reference(version = "1.0.0")
    TAssetCateGoryFacade       tAssetCateGoryFacade;
    @Reference(version = "1.0.0")
    ActivityFacade             activityFacade;
    @Reference(version = "1.0.0")
    ServiceConfigFacade        serviceConfigFacade;

    /**
     * 描述  配资乐 发布需求价格
     *
     * @author : whisky_vip
     * @date : 2018/10/10 16:57
     */
    @PostMapping("/open/withfudig/withfudigPrice")
    public ResponseModel getWithfudigPrice() {
        String                  price  = serviceConfigFacade.selectByConfigType(ServiceConfigEnum.WITHFUDIG_REQUIREMENT_PRICE);
        Map<String, BigDecimal> result = Maps.newHashMap();
        result.put("withfudigPrice", new BigDecimal(price));
        return ResponseModel.succ(result);
    }

    /**
     * 描述 配资乐 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/10 9:57
     */
    @PostMapping("/open/withfudig/requirementList")
    public ResponseModel requirementListForPlatform(@RequestBody WithfudigRequirementReq.RequirementListForPlatform req) {
        PageUtils.Page resp = withfudigRequirementFacade.requirementListForPlatform(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 配资乐已关注 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/10 9:57
     */
    @PostMapping("/confined/withfudig/requirementFollowList")
    public ResponseModel requirementFollowListForPlatform(@RequestBody WithfudigRequirementReq.RequirementListForPlatform req) {
//        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());
        PageUtils.Page resp = withfudigRequirementFacade.requirementFollowList(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 我的配资乐 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/10 9:57
     */
    @PostMapping("/confined/withfudig/myRequirementList")
    public ResponseModel myRequirementList(@RequestBody WithfudigRequirementReq.MyRequirementList req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageUtils.Page resp = withfudigRequirementFacade.myRequirementList(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述 有标的物的 配资乐需求数据 提交
     *
     * @author : whisky_vip
     * @date : 2018/9/10 9:23
     */
    @PostMapping("/confined/withfudig/requirementAddWithAssertId")
    public ResponseModel requirementAddWithAssertId(@RequestBody WithfudigRequirementReq.RequirementAdd req) {
        isAuth();
        Assert.notNull(req.getActivityId(), "assertId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setIsPlatform(true);
        Object     activity             = activityFacade.getById(req.getActivityId());
        JSONObject activityObject       = JSONObject.parseObject(JSONObject.toJSONString(activity));
        Integer    assetCategoryGroupId = activityObject.getInteger("assetCategoryGroupId");
        String     categoryName         = tAssetCateGoryFacade.getCategoryOptionNameByCategoryId(assetCategoryGroupId);
        req.setAssetType(convertAssetType(categoryName).toString());
        req.setAssetId(activityObject.getInteger("assetId"));
        checkParamLegal(req);
        int id = withfudigRequirementFacade.requirementAdd(req);

        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("requirementId", id);
        return ResponseModel.succ(objectObjectHashMap);
    }

    /**
     * 描述 没有标的物的 配资乐需求数据 提交
     *
     * @author : whisky_vip
     * @date : 2018/9/10 18:49
     */
    @PostMapping("/confined/withfudig/requirementAddWithoutAssertId")
    public ResponseModel requirementAddWithoutAssertId(@RequestBody WithfudigRequirementReq.RequirementAdd req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setIsPlatform(false);
        checkParamLegal(req);
        int id = withfudigRequirementFacade.requirementAdd(req);

        HashMap<Object, Object> objectObjectHashMap = Maps.newHashMap();
        objectObjectHashMap.put("requirementId", id);
        return ResponseModel.succ(objectObjectHashMap);
    }

    /**
     * 描述 配资乐需求数据 更新
     *
     * @author : whisky_vip
     * @date : 2018/9/13 19:47
     */
    @PostMapping("/confined/withfudig/requirementUpdate")
    public ResponseModel requirementUpdate(@RequestBody WithfudigRequirementReq.RequirementUpdate req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setIsPlatform(false);
        checkParamLegal(req);
        int count = withfudigRequirementFacade.requirementUpdate(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 没有标的物的 配资乐需求关联标的物  提交
     *
     * @author : whisky_vip
     * @date : 2018/9/10 18:49
     */
    @PostMapping("/confined/withfudig/requirementRelateAssertId")
    public ResponseModel requirementRelateAssertId(@RequestBody WithfudigRequirementReq.RequirementRelateAssertId req) {
        isAuth();
        Assert.notNull(req.getRequirementId(), "requirementId 不能为空");
        Assert.notNull(req.getAssetId(), "assetId 不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setAccountId(accountBaseInfo.getAccountId());
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        int count = withfudigRequirementFacade.requirementRelateAssertId(req);
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述: 查看详情数据
     *
     * @author : whisky_vip
     * @date : 2018/9/10 9:23
     */
    @PostMapping("/confined/withfudig/requirementDetail")
    public ResponseModel requirementDetail(@RequestBody WithfudigRequirementReq.RequirementDetailReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        WithfudigRequirementDetailResp result = withfudigRequirementFacade.requirementDetail(req, false);
        return ResponseModel.succ(result);
    }


    /**
     * 描述 关注配资乐
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:38
     */
    @PostMapping(value = "/confined/withfudig/serviceFollowAdd")
    public ResponseModel withfudigServiceFollowAdd(@RequestBody ServiceFollowReq.Add req) {
//        isAuth();
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey());
        int count = serviceFollowFacade.serviceFollowAdd(req);
        withfudigRequirementFacade.updateFollowCount(req.getRequirementId());
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述 取消关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:39
     */
    @PostMapping(value = "/confined/withfudig/serviceFollowRemove")
    public ResponseModel withfudigServiceFollowRemove(@RequestBody ServiceFollowReq.Remove req) {
//        isAuth();
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        int count = serviceFollowFacade.serviceFollowRemove(req);
        withfudigRequirementFacade.updateFollowCount(req.getRequirementId());
        return ResponseModel.succ(count);
    }

    /**
     * 描述 配资乐  批量取消关注
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:39
     */
    @PostMapping(value = "/confined/withfudig/serviceFollowRemoveList")
    public ResponseModel withfudigServiceFollowRemoveList(@RequestBody ServiceFollowReq.Remove req) {
//        isAuth();
        if (null == req.getRequirementIds() || req.getRequirementIds().length == 0) {
            return ResponseModel.succ();
        }
        req.setPartyId(loadCurLoginAccountInfo().getPartyPrimaryId());
        req.setAccountId(loadCurLoginAccountInfo().getAccountId());
        req.setRelativeType(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey());
        int count = serviceFollowFacade.serviceFollowRemoveList(req);
        return ResponseModel.succ(count);
    }

    private static final String PROPERTY     = "物权";
    private static final String DEBT         = "单户债权";
    private static final String DEBT_PACKAGE = "债权";

    private Integer convertAssetType(String name) {
        if (name == null) {
            return null;
        }
        if (name.contains(PROPERTY)) {
            return WithfudigEnum.AssetType.PROPERTY.getValue();
        } else if (name.contains(DEBT)) {
            return WithfudigEnum.AssetType.DEBT.getValue();
        } else if (name.contains(DEBT_PACKAGE)) {
            return WithfudigEnum.AssetType.DEBTPACKAGE.getValue();
        }
        return null;
    }

    private void checkParamLegal(WithfudigRequirementReq.RequirementAdd req) {
        if (null != req.getRequirementInterestPercentStart() && null != req.getRequirementInterestPercentEnd()) {
            if (req.getRequirementInterestPercentEnd().compareTo(req.getRequirementInterestPercentStart()) < 0) {
                throw new BusinessException("请正确填写融资利息");
            }
        }
    }
    private void checkParamLegal(WithfudigRequirementReq.RequirementUpdate req) {
        if (null != req.getRequirementInterestPercentStart() && null != req.getRequirementInterestPercentEnd()) {
            if (req.getRequirementInterestPercentEnd().compareTo(req.getRequirementInterestPercentStart()) < 0) {
                throw new BusinessException("请正确填写融资利息");
            }
        }
    }


}
