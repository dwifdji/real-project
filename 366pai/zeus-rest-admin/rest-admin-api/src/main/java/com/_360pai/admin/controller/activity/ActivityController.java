package com._360pai.admin.controller.activity;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.DownloadUtil;
import com._360pai.arch.common.utils.ExcelUtil;
import com._360pai.core.common.SystemDict;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.common.constants.ActivityServiceProviderEnum;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.facade.activity.req.ActivityReq;
import com._360pai.core.facade.activity.resp.ActivityResp;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
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
    StaffFacade staffFacade;

    /**
     * 获取活动列表
     */
    @RequiresPermissions("pmgl_pmhd:list")
    @GetMapping(value = "/admin/activity/list")
    public ResponseModel list(ActivityReq.QueryReq activityReq) {
        return ResponseModel.succ(activityFacade.getAllByPage(activityReq));
    }

    /**
     * 获取拍卖活动详情
     */
    @GetMapping(value = "/admin/activity/detail")
    public ResponseModel detail(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getActivity(req));
    }

    /**
     * 更新拍卖活动详情
     */
    @PostMapping(value = "/admin/activity/update")
    public ResponseModel update(@Valid @RequestBody ActivityReq.UpdateReq req) {
        Assert.notNull(req.getId(), "id 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        activityFacade.updateActivity(req);
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "更新拍卖师、佣金比例", req.getId());
        return ResponseModel.succ();
    }

    /**
     * 活动审核通过接口
     *
     */
    @RequiresPermissions("pmgl_pmhd:activity_review")
    @PostMapping(value = "/admin/activity/approve")
    public ResponseModel approve(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        ActivityResp resp = activityFacade.platformApprove(req);
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "活动审核通过", req.getActivityId());
        return ResponseModel.succ(resp);
    }

    /**
     * 活动审核拒绝接口
     *
     */
    @RequiresPermissions("pmgl_pmhd:activity_review")
    @PostMapping(value = "/admin/activity/reject")
    public ResponseModel reject(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getReason(), "reason 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        ActivityResp resp = activityFacade.platformReject(req);
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "活动审核拒绝", req.getActivityId());
        return ResponseModel.succ(resp);
    }

    /**
     * 出价记录列表
     */
    @GetMapping(value = "/admin/activity/bidRecords")
    public ResponseModel bidRecords(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getAllBidRecordsByPage(req));
    }

    /**
     * 参拍人列表
     */
    @GetMapping(value = "/admin/activity/participatingParties")
    public ResponseModel participatingParties(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getParticipatingPartiesByPage(req));
    }

    /**
     * 关注人列表
     */
    @GetMapping(value = "/admin/activity/favoredPartyAccounts")
    public ResponseModel favoredPartyAccounts(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getFavoredPartyAccountsByPage(req));
    }

    /**
     * 提醒人列表
     */
    @GetMapping(value = "/admin/activity/notifiedPartyAccounts")
    public ResponseModel notifiedPartyAccounts(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getNotifiedPartyAccountsByPage(req));
    }

    /**
     * 分享人列表
     */
    @GetMapping(value = "/admin/activity/sharePartyAccounts")
    public ResponseModel sharePartyAccounts(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getSharePartyAccountsByPage(req));
    }

    /**
     * 强制撤回活动
     */
    @RequiresPermissions("pmgl_pmhd:force_withdraw_activity")
    @PostMapping(value = "/admin/activity/forceWithdraw")
    public ResponseModel forceWithdraw(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        activityFacade.forceWithdraw(req);
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "强制撤回活动", req.getActivityId());
        return ResponseModel.succ();
    }


    /**
     * 活动审核通过接口
     *
     */
    @PostMapping(value = "/admin/activity/bind/staff")
    public ResponseModel bindStaff(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getStaffId(), "staffId 参数不能为空");
        ActivityResp resp = activityFacade.bindStaff(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 活动审核拒绝接口
     *
     */
    @PostMapping(value = "/admin/activity/unbind/staff")
    public ResponseModel unbindStaff(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        ActivityResp resp = activityFacade.unbindStaff(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 获取送拍协议
     */
    @GetMapping(value = "/admin/delegation/agreement")
    public ResponseModel delegationAgreement(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.getDelegationAgreement(req));
    }

    /**
     * 活动可见性接口
     *
     */
    @PostMapping(value = "/admin/activity/modify/visibility")
    public ResponseModel modifyVisibility(@RequestBody ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getVisibilityLevel(), "visibilityLevel 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "修改活动可见性", req.getActivityId());
        return ResponseModel.succ(activityFacade.modifyVisibility(req));
    }

    /**
     * 活动联拍数据
     */
    @GetMapping(value = "/admin/activity/union/data")
    public ResponseModel unionData(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        return ResponseModel.succ(activityFacade.unionData(req));
    }

    /**
     * 查看拍卖活动保留价接口
     */
    @RequiresPermissions("pmgl_pmhd:check_reserve_price")
    @GetMapping(value = "/admin/activity/check/reserve/price")
    public ResponseModel checkReservePrice(ActivityReq.ActivityId req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Map<String, Object> data = new HashMap<>();
        data.put("reservePrice", activityFacade.checkReservePrice(req.getActivityId()));
        return ResponseModel.succ(data);
    }

    /**
     * 修改拍卖活动保留价接口
     */
    @RequiresPermissions("pmgl_pmhd:modify_reserve_price")
    @PostMapping(value = "/admin/activity/modify/reserve/price")
    public ResponseModel modifyReservePrice(@RequestBody ActivityReq.ModifyReservePriceReq req) {
        Assert.notNull(req.getActivityId(), "activityId 参数不能为空");
        Assert.notNull(req.getReservePrice(), "reservePrice 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        activityFacade.modifyReservePrice(req);
        staffFacade.saveAuctionActivityOperationRecord(loadCurLoginId(), "修改保留价", req.getActivityId());
        return ResponseModel.succ();
    }

    /**
     * 活动数据下载
     */
    @GetMapping(value = "/admin/activity/download")
    public ResponseModel download(ActivityReq.ActivityId req, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "拍卖活动信息汇总");
        params.put("sheetName", "拍卖活动统计数据");
        List<Map<String, Object>> list = new ArrayList<>();
        req.setPage(1);
        req.setPerPage(100);
        PageInfoResp pageInfoResp;
        while (true) {
            pageInfoResp = activityFacade.exportActivityList(req);
            list.addAll(pageInfoResp.getList());
            if (!pageInfoResp.isHasNextPage()) {
                break;
            }
            req.setPage(req.getPage() + 1);
        }
        params.put("list", list);
        String[] keys = new String[]{
                "活动编号", "提交审核时间", "上拍时间", "委托方名称", "活动名称", "拍品类型", "所在省份", "所在城市", "拍卖方式", "起拍价", "拍卖开始时间", "拍卖结束时间", "债权利息", "债权本金", "关注人数", "报名人数", "提醒人数", "浏览次数", "活动状态", "送拍机构"
        };
        params.put("keys", keys);
        String[] columnNames = keys;
        params.put("columnNames", columnNames);
        DownloadUtil.downloadExcel(request, response, params);
        return ResponseModel.succ();
    }

    /**
     * 活动服务商报名记录接口（分页）
     */
    @GetMapping(value = "/admin/activity/service/provider/enroll/record")
    public ResponseModel getActivityServiceProviderEnrollRecord(ActivityReq.ActivityServiceProviderReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        Assert.notNull(req.getProviderType(), "providerType不能为空");
        return activityFacade.getActivityServiceProviderEnrollRecord(req);
    }

    /**
     * 活动服务商信息接口
     */
    @GetMapping(value = "/admin/activity/service/provider")
    public ResponseModel getBackgroundActivityServiceProvider(ActivityReq.ActivityServiceProviderReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        return activityFacade.getBackgroundActivityServiceProvider(req);
    }

    /**
     * 设置活动服务商接口
     */
    @PostMapping(value = "/admin/activity/service/provider/setup")
    public ResponseModel activityServiceProviderSetup(@RequestBody ActivityReq.ActivityServiceProviderReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        return activityFacade.activityServiceProviderSetup(req);
    }

    /**
     * 取消活动服务商接口
     */
    @PostMapping(value = "/admin/activity/service/provider/cancel")
    public ResponseModel cancelActivityServiceProvider(@RequestBody ActivityReq.ActivityServiceProviderReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Assert.notNull(req.getActivityType(), "activityType不能为空");
        return activityFacade.cancelActivityServiceProvider(req);
    }

    /**
     * 更新活动服务商报名记录接口
     */
    @PostMapping(value = "/admin/activity/service/provider/update")
    public ResponseModel updateActivityServiceProvider(@RequestBody ActivityReq.ActivityServiceProviderReq req) {
        Assert.notNull(req.getId(), "id不能为空");
        Assert.notNull(req.getOrderNum(), "orderNum不能为空");
        return activityFacade.updateActivityServiceProvider(req);
    }

    /**
     * 推广位列表接口（分页）
     */
    @RequiresPermissions("yygl_tgwgl:tgwgl_list")
    @GetMapping(value = "/admin/activity/poster/list")
    public ResponseModel getBackendActivityPosterList(ActivityReq.QueryReq req) {
        return ResponseModel.succ(activityFacade.getBackendActivityPosterList(req));
    }

    /**
     * 推广位详情接口
     */
    @GetMapping(value = "/admin/activity/poster/detail")
    public ResponseModel getActivityPoster(ActivityReq.QueryReq req) {
        return ResponseModel.succ(activityFacade.getActivityPoster(req));
    }

    /**
     * 推广位新增接口
     */
    @PostMapping(value = "/admin/activity/poster/add")
    public ResponseModel addActivityPoster(@RequestBody ActivityReq.ActivityPosterAddReq req) {
        return ResponseModel.succ(activityFacade.addActivityPoster(req));
    }

    /**
     * 推广位修改接口
     */
    @PostMapping(value = "/admin/activity/poster/update")
    public ResponseModel updateActivityPoster(@RequestBody ActivityReq.ActivityPosterUpdateReq req) {
        return ResponseModel.succ(activityFacade.updateActivityPoster(req));
    }

    /**
     * 推广位删除接口
     */
    @PostMapping(value = "/admin/activity/poster/delete")
    public ResponseModel deleteActivityPoster(@RequestBody ActivityReq.QueryReq req) {
        return ResponseModel.succ(activityFacade.deleteActivityPoster(req));
    }


    /**
     * 导航信息
     */
    @GetMapping(value = "/admin/open/nav")
    public ResponseModel nav() {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("asset_category_list", activityFacade.getActivityPosterAssetCategoryList());
        jsonObject.put("activity_status_list", getActivityStatusList());
        jsonObject.put("bus_type_list", getBusTypeList());


        return ResponseModel.succ(jsonObject);
    }

    private JSONArray getActivityStatusList() {
        JSONArray activityStatusList = (JSONArray) JSONArray.parse(SystemDict.AUCTIONSTATUS);
        for (Object item : activityStatusList) {
            JSONObject js = (JSONObject) item;
            js.put("id", ActivityServiceProviderEnum.ActivityType.Auction.getKey() + "_" + js.getString("name"));
        }
        JSONObject item1 = new JSONObject();
        item1.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + EnrollingEnum.STATUS.ONLINE.getType());
        item1.put("name", EnrollingEnum.STATUS.ONLINE.getTypeName());
        activityStatusList.add(item1);
        JSONObject item2 = new JSONObject();
        item2.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + EnrollingEnum.STATUS.FINISHED.getType());
        item2.put("name", EnrollingEnum.STATUS.FINISHED.getTypeName());
        activityStatusList.add(item2);
        JSONObject item3 = new JSONObject();
        item3.put("id", ActivityServiceProviderEnum.ActivityType.Enrolling.getKey() + "_" + EnrollingEnum.STATUS.CLOSED.getType());
        item3.put("name", EnrollingEnum.STATUS.CLOSED.getTypeName());
        activityStatusList.add(item3);
        return activityStatusList;
    }

    private JSONArray getBusTypeList() {
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(addBusType("土地"));
        jsonArray.add(addBusType("在建工程"));
        jsonArray.add(addBusType("厂房"));
        jsonArray.add(addBusType("商业"));
        jsonArray.add(addBusType("办公"));
        jsonArray.add(addBusType("住宅"));
        jsonArray.add(addBusType("股权"));
        jsonArray.add(addBusType("设备"));
        jsonArray.add(addBusType("机动车"));
        jsonArray.add(addBusType("矿权"));
        jsonArray.add(addBusType("商铺"));
        jsonArray.add(addBusType("市场"));
        jsonArray.add(addBusType("场地"));
        jsonArray.add(addBusType("农用地"));
        jsonArray.add(addBusType("无抵押"));
        jsonArray.add(addBusType("其他"));
        return jsonArray;
    }

    private JSONObject addBusType(String name) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", name);
        jsonObject.put("name", name);
        return jsonObject;
    }
}
