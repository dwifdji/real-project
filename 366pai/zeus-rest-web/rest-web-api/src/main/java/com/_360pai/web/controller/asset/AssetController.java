package com._360pai.web.controller.asset;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.arch.core.filter.PropertyFilterFactory;
import com._360pai.core.common.constants.AgencyEnum;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.TAssetPropertyActivityFacade;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.AgencyReq;
import com._360pai.core.facade.account.vo.AgencyVo;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.AssetPropertiesFacade;
import com._360pai.core.facade.asset.req.AssetAuthorizationReq;
import com._360pai.core.facade.asset.req.AssetPropertyReq;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.asset.req.TAssetPropertyActivityReq;
import com._360pai.web.controller.AbstractController;
import com._360pai.web.controller.account.resp.AccountBaseInfo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class AssetController extends AbstractController {
    @Reference(version = "1.0.0")
    protected AssetFacade assetFacade;
    @Reference(version = "1.0.0")
    protected AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    protected AssetPropertiesFacade assetPropertiesFacade;
    @Reference(version = "1.0.0")
    protected TAssetPropertyActivityFacade tAssetPropertyActivityFacade;

    /**
     * 功能描述: 上传拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/confined/addAsset")
    public ResponseModel addAsset(@RequestBody AssetReq.AddReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        return (ResponseModel) assetFacade.addAsset(req, accountBaseInfo.getPartyPrimaryId(), AssetEnum.ComeFrom.PLATFORM.getKey());
    }

    /**
     * 功能描述: 保存到草稿箱
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/confined/drafts")
    public ResponseModel draftsAsset(@RequestBody AssetReq.AddReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        assetFacade.draftsAsset(req, accountBaseInfo.getPartyPrimaryId() + "");
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
    @PostMapping(value = "/confined/search/drafts")
    public ResponseModel searchDraftsAsset(@RequestBody AssetReq.AddReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        Assert.notNull(req.getType(), "类型不能为空");
        Assert.notNull(accountBaseInfo.getPartyPrimaryId(), "请先去认证");
        Object drafts = assetFacade.findDrafts(req, accountBaseInfo.getPartyPrimaryId() + "");
        return ResponseModel.succ(drafts);
    }

    /**
     * 功能描述: 我的拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @GetMapping(value = "/confined/myAsset")
    public ResponseModel myAsset(AssetReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        PageInfo pageInfo = assetFacade.myAsset(req);
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
    @PostMapping(value = "/confined/my/asset/edit")
    public ResponseModel AssetEdit(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        req.setSideType(SideType.PLATFORM);
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return assetFacade.assetEdit(req);
    }

    /**
     * 功能描述: 拍品修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/confined/my/asset/disposalAssetEdit")
    public ResponseModel disposalAssetEdit(@RequestBody AssetReq.AddDisposalReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setSideType(SideType.PLATFORM);
        return assetFacade.disposalAssetEdit(req);
    }

    /**
     * 功能描述: 拍品修改
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/confined/my/asset/withfudigAssetEdit")
    public ResponseModel withfudigAssetEdit(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        isAuth();
        return assetFacade.withfudigAssetEdit(req);
    }

    /**
     * 功能描述: 拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/confined/my/asset/detail")
    public ResponseModel AssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        Integer partyPrimaryId = accountBaseInfo.getPartyPrimaryId();
        req.setPartyId(partyPrimaryId);
        req.setSideType(SideType.PLATFORM);
        Object pageInfo = assetFacade.assetDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/confined/my/asset/see")
    public ResponseModel seeAssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setSideType(SideType.PLATFORM);
        Object pageInfo = assetFacade.seeAssetDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 商品详情页拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/open/asset/detail")
    public ResponseModel detail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setSideType(SideType.PLATFORM);
        Map<String, Object> pageInfo = assetFacade.productDetail(req);
        if(pageInfo==null){
            return ResponseModel.succ();
        }
        if (accountBaseInfo.getAccountId() != null) {
            if (accountBaseInfo.getPartyPrimaryId() == null) {
                //未认证
                pageInfo.put("seeStatus", "isNotAuth");
            } else {
                //登录  同时判断是否认证
                boolean accountAuth = accountBaseInfo.isAccountAuth();
                if (accountAuth) {
                    //认证通过
                    pageInfo.put("seeStatus", "authorization");
                } else {
                    //未认证
                    pageInfo.put("seeStatus", "isNotAuth");
                }
            }
        } else {
            //未登录
            pageInfo.put("seeStatus", "NotLogged");
        }
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 商品详情页拍品详情
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/21 13:02
     */
    @PostMapping(value = "/confined/self/asset/detail")
    public ResponseModel MyDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "拍品编号不存在");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        req.setSideType(SideType.PLATFORM);
        Object pageInfo = assetFacade.MyDetail(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述:  标的上传活动搜索机构
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @GetMapping(value = "/open/agency/search")
    public ResponseModel search(AgencyReq.QueryReq req) {
        req.setPayBind(1);
        req.setOpenFadada("1");
        req.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
        PageInfoResp<AgencyVo> pageInfo = accountFacade.getAgencyListByPage(req);
        return ResponseModel.succ(pageInfo);
    }

    @GetMapping(value = "/open/assetProperty/list")
    public ResponseModel list(AssetPropertyReq req) {
        PageInfo pageInfo = assetPropertiesFacade.getPropertiesList(req);
        return ResponseModel.succ(pageInfo);
    }

    /**
     * 功能描述: 热门拍卖搜索
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:16
     */
    @PostMapping(value = "/open/assetProperty/search")
    public ResponseModel propertySearch(@RequestBody TAssetPropertyActivityReq req) {
        req.setPage(1);
        req.setPerPage(10);
        PageInfo pageInfo = tAssetPropertyActivityFacade.propertySearch(req);
        return ResponseModel.succ(pageInfo, PropertyFilterFactory.create(null));
    }

    /**
     * 我发布的拍品
     */
    @GetMapping(value = "/confined/my/asset/list")
    public ResponseModel myAssetList(AssetReq.QueryReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.isAccountAuth()) {
            return ResponseModel.fail("请先完成认证再进行此操作！");
        }
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(assetFacade.getAllAssetByPage(req));
    }

    /**
     * 撤回标的
     */
    @PostMapping(value = "/confined/withdraw/asset")
    public ResponseModel withdrawAsset(@RequestBody AssetReq.AssetIdReq req) {
        Assert.notNull(req.getAssetId(), "assetId 不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(assetFacade.withdrawAsset(req));
    }

    /**
     * 功能描述: 上传处置服务拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/confined/addDisposalAsset")
    public ResponseModel addDisposalAsset(@RequestBody AssetReq.AddReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        Object i = assetFacade.addDisposalAsset(req, accountBaseInfo.getPartyPrimaryId());
        System.out.println("req = " + JSON.toJSONString(req));
        return (ResponseModel) i;
    }

    /**
     * 功能描述: 上传配资乐拍品
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/20 16:17
     */
    @PostMapping(value = "/confined/addWithfudigAsset")
    public ResponseModel addWithfudigAsset(@RequestBody AssetReq.AddReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();
        Object i = assetFacade.addWithfudigAsset(req, accountBaseInfo.getPartyPrimaryId());
        System.out.println("req = " + JSON.toJSONString(req));
        return (ResponseModel) i;
    }

    @PostMapping(value = "/confined/self/asset/uploadSelfReport")
    public ResponseModel uploadSelfReport(@RequestBody AssetReq.UploadReportReq req) {
        isAuth();
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        assetFacade.uploadSelfReport(req);
        return ResponseModel.succ();
    }

    /**
     * 准备签署尽调授权协议接口
     */
    @PostMapping(value = "/confined/self/asset/preSignTuneAuthProtocol")
    public ResponseModel preSignTuneAuthProtocol(@Valid @RequestBody AssetAuthorizationReq.PreSignTuneAuthProtocol req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(assetFacade.preSignTuneAuthProtocol(req));
    }

    /**
     * 签署尽调授权协议
     */
    @PostMapping(value = "/confined/self/asset/signTuneAuthProtocol")
    public ResponseModel signTuneAuthProtocol(@Valid @RequestBody AssetAuthorizationReq.SignTuneAuthProtocol req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return ResponseModel.succ(assetFacade.signTuneAuthProtocol(req));
    }


    /**
     * 新增租赁权拍卖信息数据
     * @return
     */
    @PostMapping(value = "/confined/self/asset/saveOrUpdateLeaseAsset")
    public ResponseModel saveOrUpdateLeaseAsset(@RequestBody AssetReq.LeaseDataReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return assetFacade.saveOrUpdateLeaseAsset(req);
    }


    /**
     * 获取租赁权拍卖信息数据
     * @return
     */
    @GetMapping(value = "/open/self/asset/getLeaseAssetById")
    public ResponseModel getLeaseAssetById(AssetReq.LeaseDataReq req) {
        if(req.getAssetId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }
        req.setWebFlag(AssetEnum.WebFlag.WEB.getKey());
        return assetFacade.getLeaseAssetById(req);
    }

    /**
     * 保存或者修改租赁权的草稿
     * @return
     */
    @PostMapping(value = "/confined/self/asset/saveOrUpdateLeaseDraft")
    public ResponseModel saveOrUpdateLeaseDraft(@RequestBody AssetReq.LeaseDataReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return assetFacade.saveOrUpdateLeaseDraft(req);
    }


    /**
     * 查詢租赁权上派拍的草稿
     * @return
     */
    @PostMapping(value = "/confined/self/asset/getLeaseDraft")
    public ResponseModel getLeaseDraft(@RequestBody AssetReq.LeaseDataReq req) {
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        isAuth();

        req.setPartyId(accountBaseInfo.getPartyPrimaryId());
        return assetFacade.getLeaseDraft(req);
    }

}
