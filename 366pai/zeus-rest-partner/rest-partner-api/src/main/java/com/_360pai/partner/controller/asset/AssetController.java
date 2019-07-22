package com._360pai.partner.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.constant.SystemConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.disposal.DisposeLevelFacade;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:32
 */
@RestController
public class AssetController extends AbstractController {

    @Reference(version = "1.0.0")
    AssetFacade assetFacade;
    @Reference(version = "1.0.0")
    ActivityFacade activityFacade;
    @Reference(version = "1.0.0")
    private DisposeLevelFacade disposeLevelFacade;

    /**
     * 获取标的列表
     */
    @GetMapping(value = "/agency/asset/list")
    public ResponseModel list(AssetReq.QueryReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(assetFacade.getAllAssetByPage(req));
    }

    /**
     * 获取标的详情
     */
    @GetMapping(value = "/agency/asset/detail")
    public ResponseModel detail(AssetReq.AssetIdReq req) {
        Assert.notNull(req.getAssetId(), "assetId 参数不能为空");
        return ResponseModel.succ(assetFacade.getAsset(req.getAssetId()));
    }

    /**
     * 更新标的详情
     */
    @PostMapping(value = "/agency/asset/update")
    public ResponseModel update(@RequestBody AssetReq.UpdateReq req) {
        return ResponseModel.succ(assetFacade.update(req));
    }

    /**
     * 功能描述: 上传拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/agency/addAsset")
    public ResponseModel addAsset(@RequestBody AssetReq.AddReq req) {
        req.setSideType(SideType.AGENCY);
        return (ResponseModel)  assetFacade.addAsset(req, loadCurLoginAgencyId(), AssetEnum.ComeFrom.AGENCY.getKey());
    }


    /**
     * 功能描述: 拍品修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/agency/asset/edit")
    public ResponseModel assetEdit(@RequestBody AssetReq.AddReq req) {
        req.setSideType(SideType.AGENCY);
        ResponseModel pageInfo = assetFacade.assetEdit(req);
        return pageInfo;
    }


    /**
     * 功能描述: 拍品详情（修改专用）
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/agency/my/asset/detail")
    public ResponseModel AssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        req.setSideType(SideType.AGENCY);
        Object pageInfo = assetFacade.assetDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 拍品详情（展示用）
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/agency/my/asset/see")
    public ResponseModel seeAssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        req.setSideType(SideType.AGENCY);
        Object pageInfo = assetFacade.MyDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 保存到草稿箱
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/agency/drafts")
    public ResponseModel draftsAsset(@RequestBody AssetReq.AddReq req) {
        Integer agencyId = loadCurLoginAgencyId();
        assetFacade.draftsAsset(req, SystemConstant.AGENCY_DRAFTS_PREFIX_KEY + agencyId);
        return ResponseModel.succ();
    }

    /**
     * 功能描述: 保存到草稿箱
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/agency/search/drafts")
    public ResponseModel searchDraftsAsset(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getType(), "类型不能为空");
        Integer agencyId = loadCurLoginAgencyId();
        Object drafts = assetFacade.findDrafts(req, SystemConstant.AGENCY_DRAFTS_PREFIX_KEY + agencyId);
        return ResponseModel.succ(drafts);
    }

    /**
     * 判断活动所在城市是否存在一级合伙人
     */
    @GetMapping("/agency/survey/firstLevelProvider")
    public ResponseModel firstLevelProvider(Integer activityId) {
        Integer partner = disposeLevelFacade.getFirstLevelPartnerByActivityId(activityId);
        Map<String, Object> resp = new HashMap<>(1);
        resp.put("isExist", null != partner ? "10":"00");
        return ResponseModel.succ(resp);
    }

    /**
     * 准备签署尽调授权协议接口
     */
    @PostMapping(value = "/agency/self/asset/preSignTuneAuthProtocol")
    public ResponseModel preSignTuneAuthProtocol(@Valid @RequestBody AssetAuthorizationReq.PreSignTuneAuthProtocol req) {
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(assetFacade.preSignTuneAuthProtocol(req));
    }

    /**
     * 签署尽调授权协议
     */
    @PostMapping(value = "/agency/self/asset/signTuneAuthProtocol")
    public ResponseModel signTuneAuthProtocol(@Valid @RequestBody AssetAuthorizationReq.SignTuneAuthProtocol req) {
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(assetFacade.signTuneAuthProtocol(req));
    }

    /**
     * 功能描述: 上传处置服务拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/agency/addDisposalAsset")
    public ResponseModel addDisposalAsset(@RequestBody AssetReq.AddReq req) {
        req.setSideType(SideType.AGENCY);
        Object i = assetFacade.addDisposalAsset(req, loadCurLoginAgencyId());
        System.out.println("req = " + JSON.toJSONString(req));
        return (ResponseModel) i;
    }

    /**
     * 功能描述: 商品详情页拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/agency/self/asset/detail")
    public ResponseModel MyDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setSideType(SideType.AGENCY);
        Object pageInfo = assetFacade.MyDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 拍品修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/agency/my/asset/disposalAssetEdit")
    public ResponseModel disposalAssetEdit(@RequestBody AssetReq.AddDisposalReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getAgencyId());
        req.setSideType(SideType.AGENCY);
        return assetFacade.disposalAssetEdit(req);
    }


    /**
     * 获取租赁权拍卖信息数据
     * @return
     */
    @GetMapping(value = "/agency/self/asset/getLeaseAssetById")
    public ResponseModel getLeaseAssetById(AssetReq.LeaseDataReq req) {
        if(req.getAssetId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        req.setWebFlag(AssetEnum.WebFlag.AGENCY.getKey());
        return assetFacade.getLeaseAssetById(req);
    }
}
