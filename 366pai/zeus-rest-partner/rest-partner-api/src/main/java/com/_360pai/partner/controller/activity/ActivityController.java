package com._360pai.partner.controller.activity;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.AuctionFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.req.AuctionReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com._360pai.core.facade.agency.AgencyFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.partner.controller.AbstractController;
import com._360pai.partner.controller.account.resp.AccountBaseInfo;
import com._360pai.partner.utils.ExcelUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/16 10:31
 */
@RestController
public class ActivityController extends AbstractController {

    @Reference(version = "1.0.0")
    ActivityFacade activityFacade;
    @Reference(version = "1.0.0")
    private AuctionFacade auctionFacade;

    @Reference(version = "1.0.0")
    private AgencyFacade agencyFacade;

    @Reference(version = "1.0.0")
    AssetFacade assetFacade;

    /**
     * 获取活动列表
     */
    @GetMapping(value = "/agency/activity/list")
    public ResponseModel list(ActivityReq.QueryReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.getAllByPage(req));
    }

    /**
     * 上拍-我的拍卖会列表
     */
    @GetMapping(value = "/agency/my/activity/list")
    public ResponseModel myActivityList(ActivityReq.QueryReq req) {
        req.setPartyId(loadCurLoginAgencyId());
        req.setComeFrom(AssetEnum.ComeFrom.AGENCY.getKey());
        return ResponseModel.succ(activityFacade.getAllByPage(req));
    }


    /**
     * 获取拍卖活动详情
     */
    @GetMapping(value = "/agency/activity/detail")
    public ResponseModel detail(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数参数不能为空");
        return ResponseModel.succ(activityFacade.getActivity(req));
    }

    /**
     * 更新拍卖活动详情
     */
    @PostMapping(value = "/agency/activity/update")
    public ResponseModel update(@RequestBody ActivityReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        return ResponseModel.succ(activityFacade.updateActivity(req));
    }


    /**
     * 出价记录列表
     */
    @GetMapping(value = "/agency/activity/bidRecords")
    public ResponseModel bidRecords(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.getAllBidRecordsByPage(req));
    }


    /**
     * 活动审核通过接口
     *
     */
    @PostMapping(value = "/agency/activity/approve")
    public ResponseModel approve(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        ActivityResp resp = activityFacade.agencyApprove(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 活动审核拒绝接口
     *
     */
    @PostMapping(value = "/agency/activity/reject")
    public ResponseModel reject(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getReason(), "reason 参数不能为空");
        ActivityResp resp = activityFacade.agencyReject(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 平台可联拍活动列表
     */
    @GetMapping(value = "/agency/activity/availablePlatformActivityList")
    public ResponseModel availablePlatformActivityList(ActivityReq.QueryReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.getAvailablePlatformActivityListByPage(req));
    }

    /**
     * 我的联拍活动列表
     */
    @GetMapping(value = "/agency/activity/myAgencyPortalActivityList")
    public ResponseModel myAgencyPortalActivityList(ActivityReq.QueryReq req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.getAgencyPortalActivityListByPage(req));
    }

    /**
     * 平台可联拍活动发布联拍
     *
     */
    @PostMapping(value = "/agency/activity/publish/union")
    public ResponseModel publishUnion(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setAgencyId(loadCurLoginAgencyId());
        ActivityResp resp = activityFacade.publishUnion(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 平台可联拍活动取消联拍
     *
     */
    @PostMapping(value = "/agency/activity/cancel/union")
    public ResponseModel cancelUnion(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setAgencyId(loadCurLoginAgencyId());
        ActivityResp resp = activityFacade.cancelUnion(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 联拍活动报名记录
     */
    @GetMapping(value = "/agency/activity/participatingParties")
    public ResponseModel participatingParties(ActivityReq.ActivityId req) {
        req.setAgencyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.getAgencyParticipatingPartiesByPage(req));
    }

    /**
     * 获取送拍协议链接
     */
    @GetMapping(value = "/agency/delegation/signature/url")
    public ResponseModel delegationSignatureUrl(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        req.setPartyId(accountBaseInfo.getAgencyId());
        return ResponseModel.succ(activityFacade.delegationSignatureUrl(req));
    }

    /**
     * 获取补充协议链接
     */
    @GetMapping(value = "/agency/additional/signature/url")
    public ResponseModel additionalSignatureUrl(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        req.setPartyId(accountBaseInfo.getAgencyId());
        return ResponseModel.succ(activityFacade.additionalSignatureUrl(req));
    }

    /**
     * 获取成交协议链接
     */
    @GetMapping(value = "/agency/deal/signature/url")
    public ResponseModel dealSignatureUrl(AuctionReq req) {
        if (req == null || req.getOrderId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        AccountBaseInfo accountBaseInfo = loadCurLoginAccountInfo();
        if (!accountBaseInfo.getIsAgencyAdmin()) {
            return ResponseModel.fail(ApiCallResult.NOT_AGENCY_ADMIN_ERRPR);
        }
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(auctionFacade.agencySignContract(req));
    }

    /**
     * 查看拍卖活动保留价接口
     */
    @GetMapping(value = "/agency/activity/check/reserve/price")
    public ResponseModel checkReservePrice(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        if (!accountInfo.getCanCheckReservePrice()) {
            return ResponseModel.fail(ApiCallResult.PERMISSION_DENIED);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("reservePrice", activityFacade.checkReservePrice(req.getActivityId()));
        return ResponseModel.succ(data);
    }

    /**
     * 活动联拍数据
     */
    @GetMapping(value = "/agency/activity/union/data")
    public ResponseModel unionData(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.unionData(req));
    }

    /**
     * 撤回拍卖活动
     */
    @PostMapping(value = "/agency/activity/withdraw")
    public ResponseModel withdrawActivity(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setPartyId(loadCurLoginAgencyId());
        return ResponseModel.succ(activityFacade.withdrawActivity(req));
    }


    /**
     * 获取机构连拍列表
     */
    @GetMapping(value = "/agency/activity/getJointList")
    public ResponseModel getJointList(ActivityReq.JointListReq req) {
        Assert.notNull(req.getType(), "type 参数不能为空");

        req.setAgencyId(loadCurLoginAccountInfo().getAgencyId());


        return ResponseModel.succ(activityFacade.getJointList(req));
    }





    /**
     * 连拍机构参与连拍
     */
    @PostMapping(value = "/agency/activity/saveJoint")
    public ResponseModel saveJoint(@RequestBody ActivityReq.JointReq req) {
        Assert.notNull(req.getIds(), "ids 参数不能为空");
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();
        req.setAgencyId(accountInfo.getAgencyId());
        req.setType("1");
        return ResponseModel.succ(activityFacade.saveOrCancelJoint(req));
    }



    /**
     * 连拍机构取消连拍
     */
    @PostMapping(value = "/agency/activity/cancelJoint")
    public ResponseModel cancelJoint(@RequestBody  ActivityReq.JointReq req) {
        Assert.notNull(req.getIds(), "ids 参数不能为空");
        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();

        req.setType("2");
        req.setAgencyId(accountInfo.getAgencyId());
        req.setIsDel(1);
        return ResponseModel.succ(activityFacade.saveOrCancelJoint(req));
    }



    /**
     * 获取机构连拍状态
     */
    @GetMapping(value = "/agency/activity/getJointStatus")
    public ResponseModel getJointStatus() {

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();


        Map getAgencyInfo = agencyFacade.getAgencyById(accountInfo.getAgencyId());

         return ResponseModel.succ(getAgencyInfo);
    }


    /**
     * 设置机构连拍转态
     */
    @PostMapping(value = "/agency/activity/setJointStatus")
    public ResponseModel setJointStatus(@RequestBody ActivityReq.setJointStatusReq req) {

        Assert.notNull(req.getJointStatus(), "jointStatus 参数不能为空");

        AccountBaseInfo accountInfo = loadCurLoginAccountInfo();

        Map<String, Object> map = new HashMap<>();
        map.put("id",accountInfo.getAgencyId());
        map.put("isJoint",req.getJointStatus());
        agencyFacade.updateAgency(map);
        return ResponseModel.succ();
    }



    /**
     * 连拍商品展示
     *
     */
    @PostMapping(value = "/agency/joint/asset/info")
    public ResponseModel seeAssetDetail(@RequestBody AssetReq.AddReq req) {
        org.apache.shiro.util.Assert.notNull(req.getAssetId(), "拍品编号不存在");
        req.setSideType(SideType.AGENCY_JOINT);
        Object pageInfo = assetFacade.MyDetail(req);
        return ResponseModel.succ(pageInfo);
    }




    /**
     * 导出连拍数据
     */
    @GetMapping(value = "/agency/joint/download")
    public void download(ActivityReq.JointListReq req, HttpServletRequest request, HttpServletResponse response) {
        req.setAgencyId(loadCurLoginAccountInfo().getAgencyId());


        List<Map<String, Object>>  list = activityFacade.getDownloadJointList(req);

        String[] columnNames = new String[]{"拍品名称", "拍品类型", "拍卖方式", "所在地", "起拍价", "预展结束时间", "拍卖时间", "拍品状态",
                "连拍状态" };

        String[] keys = new String[]{"assetName", "categoryType", "auctionType", "cityName", "startingPrice", "previewAt", "beginAt", "statusDesc",
                "jointStatusDesc" };

        String fileName = "我的连拍拍品";
        if("2".equals(req.getType())){
            fileName = "平台拍品库";
        }

         ExcelUtils.buildExcel(fileName,columnNames,keys,list,request,response);
    }



}
