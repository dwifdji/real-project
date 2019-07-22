package com._360pai.web.controller.assistant;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.AnnouncementUtil;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.arch.common.utils.JsonUtil;
import com._360pai.core.common.constants.ActivityEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.StaffFacade;
import com._360pai.core.facade.account.req.StaffReq;
import com._360pai.core.facade.account.resp.AgencyResp;
import com._360pai.core.facade.account.resp.StaffResp;
import com._360pai.core.facade.activity.InstructionsContentFacade;
import com._360pai.core.facade.activity.req.InstructionsContentReq;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.resp.AssetResp;
import com._360pai.core.facade.disposal.DisposalAdminFacade;
import com._360pai.core.facade.enrolling.EnrollingWebFacade;
import com._360pai.web.controller.AbstractController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxiao
 * @Title: InstructionsContentController  详情页公告
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/21 17:00
 */
@Slf4j
@RestController
public class InstructionsContentController extends AbstractController {

    @Reference(version = "1.0.0")
    private InstructionsContentFacade instructionsContentFacade;
    @Reference(version = "1.0.0")
    private AccountFacade accountFacade;
    @Reference(version = "1.0.0")
    private DisposalAdminFacade disposalAdminFacade;
    @Reference(version = "1.0.0")
    private EnrollingWebFacade enrollingWebFacade;
    @Reference(version = "1.0.0")
    private AssetFacade assetFacade;
    @Reference(version = "1.0.0")
    private StaffFacade staffFacade;

    /**
     * 功能描述: 活动详情页特别告知
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @GetMapping(value = "/open/instructions_contents/special_detail")
    public ResponseModel specialDetail(InstructionsContentReq req) {
        ResponseModel model = new ResponseModel().setTimestamp(System.currentTimeMillis()).warpSuccess();
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        String html = instructionsContentFacade.specialDetail(req);
        Map<String, Object> map = new HashMap<>(16);
        if (StringUtils.isNotEmpty(html)) {
            map.put("html", html.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
        } else {
            map.put("html", html);
        }
        model.setContent(map);
        return model;
    }

    /**
     * 功能描述: 拍卖活动详情页拍卖公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @GetMapping(value = "/open/instructions_contents/announcement", produces = "application/json;charset=UTF-8")
    public ResponseModel announcement(InstructionsContentReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Object activity = instructionsContentFacade.getActivityById(req);
        log.info("拍卖公告查询活动结果为：{}", activity);
        if (activity == null) {
            return ResponseModel.fail("查询失败");
        }

        JSONObject json;
        //当为租赁权拍卖时
        JSONObject activityJson = JsonUtil.toJSONObject(activity);

        if("-1".equals(activityJson.getString("categoryId"))){
            req.setAssetId(activityJson.getString("assetId"));
            Object leaseInfo = instructionsContentFacade.getLeaseActivityInfo(req);
            json = JsonUtil.toJSONObject(leaseInfo);
        //非租赁权拍卖
        }else{
             json = getAnnouncementJson(activity, req.getAgencyCode());
        }
        String pageInfo = instructionsContentFacade.announcement(req);
        String formatString = AnnouncementUtil.parse("{{", "}}", pageInfo, json);
        return ResponseModel.succ(formatString.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    private JSONObject getAnnouncementJson(Object activity, String agencyCode) {

        JSONObject json = JsonUtil.toJSONObject(activity);
        String mode = json.getString("mode");

        Date previewAt = json.getDate("previewAt");
        if (null != previewAt) {

            String parse = DateUtil.formatDate2Str(previewAt, DateUtil.NORM_DATE_PATTERN);
            json.put("previewAt", parse);
        }

        if (mode == null) {
            log.info("拍卖公告查询失败mode={}", json);
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }

        Integer agencyId = json.getInteger("agencyId");
        if (agencyId == null) {
            log.info("拍卖公告查询失败agencyId={}", json);

            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");

         }
        AgencyResp agency = accountFacade.getAgencyById(agencyId);
        log.info("拍卖公告查询机构结果为：{}", JSON.toJSONString(agency));
        if (agency == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
         }
        json.put("agencyName", agency.getName());

        Integer assetId = json.getInteger("assetId");
        if (assetId == null) {
            log.info("拍卖公告查询失败assetId={}", json);
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }
        AssetResp asset = assetFacade.getAsset(assetId);
        json.put("handoverDays", asset.getAsset().getHandoverDays());
        json.put("payDays", asset.getAsset().getPayDays());
        json.put("contactName", asset.getAsset().getContactPerson().getName());
        json.put("contactPhone", asset.getAsset().getContactPerson().getMobile());

        Integer staffId = json.getInteger("staffId");
        if (staffId != null && staffId > 0) {
            StaffReq.BaseReq param = new StaffReq.BaseReq();
            param.setStaffId(staffId);
            StaffResp.BasicResp staffBasic = staffFacade.getStaffBasic(param);
            json.put("staffPhone", staffBasic.getMobile());
            json.put("staffName", staffBasic.getName());
        }

        json.put("assetModeStr", ActivityEnum.ActivityMode.getKeyByValue(mode));
        String unionAgencyName = "360PAI.COM全网联拍共享拍卖平台";
        if (StringUtils.isNotBlank(agencyCode)) {
            AgencyResp agencyResp = accountFacade.getAgencyByCode(agencyCode);
            if (agencyId.equals(agencyResp.getId())) {
                 unionAgencyName = agencyResp.getName();
                 json.put("agencyName", "");
            } else {
                unionAgencyName = agencyResp.getName();
            }
        }
        json.put("unionAgencyName", unionAgencyName);
        return json;


    }

    /**
     * 功能描述: 拍卖活动详情页竞买须知
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/23 13:19
     */
    @GetMapping(value = "/open/instructions_contents/buyer_must_know", produces = "application/json;charset=UTF-8")
    public ResponseModel buyerMustKnow(InstructionsContentReq req) {
        Assert.notNull(req.getActivityId(), "活动ID不能为空");
        Object activity = instructionsContentFacade.getActivityById(req);
        if (activity == null) {
            return ResponseModel.fail("查询失败");
        }
        JSONObject json;
        //当为租赁权拍卖时
        JSONObject activityJson = JsonUtil.toJSONObject(activity);
        if("-1".equals(activityJson.getString("categoryId"))){
            req.setAssetId(activityJson.getString("assetId"));
            Object leaseInfo = instructionsContentFacade.getLeaseBuyerMustKnow(req);
            json = JsonUtil.toJSONObject(leaseInfo);
            json = getLeaseData(json, activityJson.getString("agencyId"), req.getAgencyCode());
            //非租赁权拍卖
        }else{
            json = getMustKnowJson(activity, req.getAgencyCode());
        }
        String pageInfo = instructionsContentFacade.buyerMustKnow(req);
        String formatString = AnnouncementUtil.parse("{{", "}}", pageInfo, json);
        String replace = formatString.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    private JSONObject getLeaseData(JSONObject json, String agencyId, String agencyCode) {
        if(StringUtils.isNotBlank(agencyId)) {
            AgencyResp agency = accountFacade.getAgencyById(Integer.parseInt(agencyId));
            json.put("agencyName", agency.getName());
        }

        String unionAgencyName = getUnionAgencyName(agencyCode);
        json.put("unionAgencyName", unionAgencyName);

        return json;
    }

    private String getUnionAgencyName(String agencyCode){
        String unionAgencyName = "360PAI.COM全网联拍共享拍卖平台";
        if (StringUtils.isNotBlank(agencyCode)) {
            AgencyResp agencyResp = accountFacade.getAgencyByCode(agencyCode);
                unionAgencyName = agencyResp.getName();
        }

        return unionAgencyName;
    }


    private JSONObject getMustKnowJson(Object activity, String agencyCode) {

        JSONObject json = JsonUtil.toJSONObject(activity);
        Date previewAt = json.getDate("previewAt");
        if (null != previewAt) {
            String parse = DateUtil.formatDate2Str(previewAt, DateUtil.NORM_DATE_PATTERN);
            json.put("previewAt", parse);
        }
        Integer agencyId = json.getInteger("agencyId");
        if (agencyId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");

        }

        AgencyResp agency = accountFacade.getAgencyById(agencyId);
        log.info("竞买须知查询机构结果为：{}", JSON.toJSONString(agency));
        if (agency == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }
        json.put("agencyName", agency.getName());

        Integer assetId = json.getInteger("assetId");
        if (assetId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }
        AssetResp asset = assetFacade.getAsset(assetId);
        log.info("竞买须知查询标的结果为：{}", JSON.toJSONString(asset));
        if (asset == null) {
            log.info("竞买须知查询标的失败");
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查询失败");
        }
        json.put("handoverDays", asset.getAsset().getHandoverDays());
        json.put("payDays", asset.getAsset().getPayDays());

        String unionAgencyName = "360PAI.COM全网联拍共享拍卖平台";
        if (StringUtils.isNotBlank(agencyCode)) {
            AgencyResp agencyResp = accountFacade.getAgencyByCode(agencyCode);
            if (agencyId.equals(agencyResp.getId())) {
                unionAgencyName = agencyResp.getName();
                json.put("agencyName", "");
            } else {
                unionAgencyName = agencyResp.getName();
            }
        }
        json.put("unionAgencyName", unionAgencyName);
        return json;

    }

    /**
     * 功能描述: 处置招标公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/29 11:09
     */
    @GetMapping(value = "/open/disposal/instructions_contents/announcement", produces = "application/json;charset=UTF-8")
    public ResponseModel disposalAnnouncement(InstructionsContentReq req) {
        Assert.notNull(req.getDisposalId(), "处置服务ID不能为空");
        Map<String, Object> disposal = disposalAdminFacade.findDisposalById(req.getDisposalId());
        JSONObject json = JsonUtil.toJSONObject(disposal);
        String pageInfo = instructionsContentFacade.disposalAnnouncement(req);
        String formatString = AnnouncementUtil.parse("{{", "}}", pageInfo, json);
        String replace = formatString.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    /**
     * 功能描述: 抵押物预招商须知
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/29 11:09
     */
    @GetMapping(value = "/open/enrolling/instructions_contents/buyer_must_know", produces = "application/json;charset=UTF-8")
    public ResponseModel enrollingBuyerMustKnow(InstructionsContentReq req) {
        Assert.notNull(req.getActivityId(), "预招商ID不能为空");

        String pageInfo = instructionsContentFacade.enrollingBuyerMustKnow(req);
        String replace = pageInfo.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    /**
     * 功能描述: 抵押物预招商公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/29 11:09
     */
    @GetMapping(value = "/open/enrolling/instructions_contents/announcement", produces = "application/json;charset=UTF-8")
    public ResponseModel enrollingAnnouncement(InstructionsContentReq req) {

        Assert.notNull(req.getActivityId(), "预招商ID不能为空");

        String pageInfo = instructionsContentFacade.enrollingAnnouncement(req);
        String replace = pageInfo.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    /**
     * 功能描述: 物权招商公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/29 11:09
     */
    @GetMapping(value = "/open/real/instructions_contents/announcement", produces = "application/json;charset=UTF-8")
    public ResponseModel realAnnouncement(InstructionsContentReq req) {

        Assert.notNull(req.getActivityId(), "预招商ID不能为空");

        String pageInfo = instructionsContentFacade.realAnnouncement(req);
        String replace = pageInfo.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

    /**
     * 功能描述: 债权招商公告
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/29 11:09
     */
    @GetMapping(value = "/open/obligatory/instructions_contents/announcement", produces = "application/json;charset=UTF-8")
    public ResponseModel obligatoryAnnouncement(InstructionsContentReq req) {
        Assert.notNull(req.getActivityId(), "预招商ID不能为空");

        String pageInfo = instructionsContentFacade.obligatoryAnnouncement(req);
        String replace = pageInfo.replace("\t", "");
        return ResponseModel.succ(replace.replaceAll("font-size: \\d*px;", "font-size: 16px;"));
    }

}
