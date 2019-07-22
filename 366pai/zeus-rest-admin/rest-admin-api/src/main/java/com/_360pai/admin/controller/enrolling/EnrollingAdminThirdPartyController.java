package com._360pai.admin.controller.enrolling;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.utils.EnrollingExcelUtils;
import com._360pai.arch.common.ResponseModel;
import com._360pai.core.common.constants.EnrollingEnum;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.enrolling.EnrollingFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.core.facade.enrolling.req.EnrollingReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述 第三方预招商
 *
 * @author : whisky_vip
 * @date : 2018/12/5 14:13
 */
@RestController
public class EnrollingAdminThirdPartyController extends AbstractController {

    @Reference(version = "1.0.0")
    EnrollingFacade    enrollingFacade;
    @Reference(version = "1.0.0")
    EnrollingWebFacade enrollingWebFacade;
    @Reference(version = "1.0.0")
    StaffFacade        staffFacade;
    @Reference(version = "1.0.0")
    AssetFacade        assetFacade;

    /**
     * 获取 第三方债权预招商 列表
     */
//    @RequiresPermissions("yzsgl_zqwqgl:list")
    @GetMapping(value = "/admin/enrolling_activity/third_party/creditor_enrolling_activities")
    public ResponseModel creditorEnrollingActivities(EnrollingReq.activitiesListReq req) {
        req.setType("2");
        req.setThirdPartyStatus(1);
        return enrollingFacade.getEnrollingActivityList(req);
    }

    /**
     * 获取 第三方物权预招商 列表
     */
//    @RequiresPermissions("yzsgl_zqwqgl:right_enrolling_activity_list")
    @GetMapping(value = "/admin/enrolling_activity/third_party/right_enrolling_activities")
    public ResponseModel rightEnrollingActivities(EnrollingReq.activitiesListReq req) {
        req.setType("3");
        req.setThirdPartyStatus(1);
        return enrollingFacade.getEnrollingActivityList(req);
    }


    /**
     * 第三方 预招商活动 提交
     */
    @PostMapping(value = "/admin/enrolling_activity/third_party/save_activity")
    public ResponseModel saveActivity(@RequestBody EnrollingReq.saveActivityThirdPartyReq req) {

        JSONObject jsonObject = JSON.parseObject(req.getFields());
        //获取当前的模板id
        Integer categoryId = jsonObject.getInteger("templateId");
        //获取模板对应的类型
        String enrollingType = EnrollingEnum.TEMPLATE_TYPE.getDesc(String.valueOf(categoryId));
        req.setPartyId(loadCurLoginId());
        req.setEnrollingType(enrollingType);
        req.setTemplateId(categoryId);
        req.setOperatorId(String.valueOf(loadCurLoginId()));
        return enrollingWebFacade.saveActivityFromThirdParty(req);
    }

    /**
     * 第三方预招商活动数据 修改
     */
    @PostMapping("/admin/enrolling_activity/third_party/update_activity")
    public ResponseModel updateActivity(@RequestBody EnrollingReq.saveActivityThirdPartyReq req) {
        Integer loadCurLoginId = loadCurLoginId();

        if (loadCurLoginId == null) {
            return ResponseModel.fail("请先登录！");
        }
        req.setUpdateType("1");
        staffFacade.saveEnrollingActivityOperationRecord(loadCurLoginId(), "修改数据预招商活动", Integer.parseInt(req.getActivityId()));
        req.setOperatorId(String.valueOf(loadCurLoginId));
        return enrollingWebFacade.updateActivityThirdParty(req);
    }

    /**
     * 功能描述: 保存到草稿箱
     * THIRDENROLLINGZQ
     * THIRDENROLLINGWQ
     */
    @PostMapping(value = "/admin/enrolling_activity/third_party/saveDrafts")
    public ResponseModel draftsAsset(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getType(), "类型不能为空");
        assetFacade.draftsAsset(req, loadCurLoginId() + "");
        return ResponseModel.succ();
    }

    /**
     * 功能描述: 保存到草稿箱
     * THIRDENROLLINGZQ
     * THIRDENROLLINGWQ
     */
    @PostMapping(value = "/admin/enrolling_activity/third_party/searchDrafts")
    public ResponseModel searchDraftsAsset(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getType(), "类型不能为空");
        Object drafts = assetFacade.findDrafts(req, loadCurLoginId() + "");
        return ResponseModel.succ(drafts);
    }

    /**
     * 第三方预招商活动 渠道信息
     */
    @GetMapping(value = "/admin/enrolling_activity/third_party/info")
    public ResponseModel thirdPartyInfo(EnrollingReq.activityIdTypeReq req) {
        Assert.notNull(req.getActivityId(), "activityId不能为空");
       return enrollingWebFacade.getThirdExtraInfo(req.getActivityId());
    }

    /**
     * 第三方预招商活动数据 下载
     */
    @GetMapping(value = "/admin/enrolling_activity/third_party/download")
    public void download(EnrollingReq.activitiesListReq req, HttpServletRequest request, HttpServletResponse response) {
        req.setThirdPartyStatus(1);



        req.setPage(1);
        req.setPerPage(5000);

        List<Map<String, Object>> totalMaps = new ArrayList<>();
        PageInfo pageInfo = enrollingFacade.getExportEnrollingActivitiesInfo(req);
        totalMaps.addAll(pageInfo.getList());

        if(pageInfo.getTotal() > req.getPerPage()){
            for (int i = 2; i < pageInfo.getTotal()/ req.getPerPage() + 2; i++) {
                req.setPage(i);
                PageInfo page = enrollingFacade.getExportEnrollingActivitiesInfo(req);
                totalMaps.addAll(page.getList());
            }
        }

        EnrollingExcelUtils.buildExcel(req.getType(),req.getThirdPartyStatus(), totalMaps, request, response);
    }

}
