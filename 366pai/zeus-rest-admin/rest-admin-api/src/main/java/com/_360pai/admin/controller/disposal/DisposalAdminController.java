package com._360pai.admin.controller.disposal;

import com._360pai.admin.controller.AbstractController;
import com._360pai.admin.controller.account.resp.AccountBaseInfo;
import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.facade.account.AccountFacade;
import com._360pai.core.facade.account.req.DisposeProviderApplyReq;
import com._360pai.core.facade.asset.AssetFacade;
import com._360pai.core.facade.asset.req.AssetReq;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.facade.disposal.DisposalAdminFacade;
import com._360pai.core.facade.disposal.req.DisposalBiddingReq;
import com._360pai.core.facade.disposal.req.DisposalRequirementReq;
import com.alibaba.dubbo.config.annotation.Reference;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.function.Predicate;

/**
 * @author xiaolei
 * @create 2018-09-17 10:11
 */
@Slf4j
@RestController
public class DisposalAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    DisposalAdminFacade disposalAdminFacade;
    @Reference(version = "1.0.0")
    ServiceFollowFacade serviceFollowFacade;
    @Reference(version = "1.0.0")
    AssetFacade         assetFacade;
    @Reference(version = "1.0.0")
    AccountFacade       accountFacade;

    @RequiresPermissions("fwxqgl_czfwgl:czfwxq_list")
    @PostMapping("/admin/disposal/getPublishRequirementList")
    public ResponseModel getDisposalApplyList(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {

        try {
            PageInfoResp pageInfoResp = disposalAdminFacade.findDisposalByAdmin(req);
            return ResponseModel.succ(pageInfoResp);
        } catch (Exception e) {
            log.error("admin 获取处置需求列表异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping("/admin/disposal/updateVerifyStatusPass")
    public ResponseModel updateVerifyStatusPass(@RequestBody DisposalRequirementReq.AdminOperateInfo req) {
        Assert.notNull(req.getDisposalId(), "disposalId 不能为空");
//        Assert.notNull(req.getDisposalStatus(), "disposalStatus 不能为空");
        try {
            Integer id = loadCurLoginId();
            req.setOperatorVerifyId(id);
            req.setDisposalStatus(DisposalEnum.RequirementStatus.PASS_FOR_PAY.getValue());
            boolean tag = disposalAdminFacade.updateDisposalVerifyStatus(req);
            if (tag) {
                return ResponseModel.succ();
            }
            return ResponseModel.fail();
        } catch (Exception e) {
            log.error("admin 处置需求审核异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping("/admin/disposal/updateVerifyStatusNoPass")
    public ResponseModel updateVerifyStatusNoPass(@RequestBody DisposalRequirementReq.AdminOperateInfo req) {
        Assert.notNull(req.getDisposalId(), "disposalId 不能为空");
//        Assert.notNull(req.getDisposalStatus(), "disposalStatus 不能为空");
        try {
            Integer id = loadCurLoginId();
            req.setOperatorVerifyId(id);
            req.setDisposalStatus(DisposalEnum.RequirementStatus.NO_PASS.getValue());
            boolean tag = disposalAdminFacade.updateDisposalVerifyStatus(req);
            if (tag) {
                return ResponseModel.succ();
            }
            return ResponseModel.fail();
        } catch (Exception e) {
            log.error("admin 处置需求审核异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping("/admin/disposal/updateDisposalSuccess")
    public ResponseModel disposalVerifyPhase2(@RequestBody DisposalBiddingReq.AddBiddingReq req) {
        disposalAdminFacade.updateDisposalSuccess(req.getCommunicateContent(),
                req.getBiddingId(), req.getOperatorId());
        return ResponseModel.succ();
    }

    @PostMapping("/admin/disposal/addBidNotice")
    public ResponseModel addBidNotice(@RequestBody DisposalRequirementReq.AdminOperateInfo req) {

        try {
            Integer operatorId = loadCurLoginId();
            boolean tag        = disposalAdminFacade.addBiddingNotice(req.getBiddingNotice(), operatorId, req.getDisposalId());
            if (tag) {
                return ResponseModel.succ();
            }
            return ResponseModel.fail();
        } catch (Exception e) {
            log.error("admin 提交招标须知异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping("/admin/disposal/manuallyCompleted")
    public ResponseModel manuallyCompleted(@RequestBody DisposalRequirementReq.AdminOperateInfo req) {

        try {
            Integer operatorId = loadCurLoginId();
            boolean tag        = disposalAdminFacade.manuallyCompleted(operatorId, req.getDisposalId());
            if (tag) {
                return ResponseModel.succ();
            }
            return ResponseModel.fail();
        } catch (Exception e) {
            log.error("admin 提交招标须知异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping(value = "/admin/disposal/serviceFollowList")
    public ResponseModel serviceFollowList(@RequestBody ServiceFollowReq.List req) {
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setRelativeType(ServiceFollowEnum.RelativeType.DIPOSAL.getKey());
        PageUtils.Page page = serviceFollowFacade.serviceFollowList(req);
        return ResponseModel.succ(page);
    }

    @PostMapping(value = "/admin/disposal/getRequirementDetail")
    public ResponseModel getRequirementDetail(@RequestBody DisposalRequirementReq.GetPublishInfoReq req) {
        Assert.notNull(req.getDisposalId(), "disposalId 参数不对");
        try {
            Map<String, Object> disposalById = disposalAdminFacade.findDisposalById(req.getDisposalId());
            return ResponseModel.succ(disposalById);
        } catch (Exception e) {
            log.error("获取需求单详情异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping(value = "/admin/disposal/getBiddingDetailList")
    public ResponseModel getBiddingDetailList(@RequestBody DisposalRequirementReq.GetBiddingList req) {
        Assert.notNull(req.getDisposalId(), "disposalId 不能为空");
        try {
            PageInfoResp biddingInfoList = disposalAdminFacade.findBiddingInfoList(req);
            return ResponseModel.succ(biddingInfoList);
        } catch (Exception e) {
            log.error("获取需求单投标情况异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    @PostMapping(value = "/admin/disposal/getRequirementAssetDetail")
    public ResponseModel getRequirementAssetDetail(@RequestBody AssetReq.AddReq req) {
        Assert.notNull(req.getAssetId(), "assetId 不能为空");
        try {
            Object object = assetFacade.assetDetail(req);
            return ResponseModel.succ(object);
        } catch (Exception e) {
            log.error("获取标的详情异常，异常信息为：", e);
            return ResponseModel.fail();
        }
    }

    /**
     * 申请处置服务商
     */
    @PostMapping(value = "/admin/disposal/provider/adminCreate")
    public ResponseModel disposeProviderAdminCreate(@RequestBody DisposeProviderApplyReq.CreateReq req) {
        if (req.getAccountId() == null) {
            return ResponseModel.fail(ApiCallResult.EMPTY);
        }
        req.setOperatorId(loadCurLoginId());
        return ResponseModel.succ(disposalAdminFacade.disposeProviderAdminCreate(req));
    }

    /**
     * 校验是否认证处置服务商
     * @return
     */
    @GetMapping(value = "/admin/disposal/provider/checkAccount")
    public ResponseModel checkAccountDispose(String mobile) {
        Map<String, Object> stringObjectMap = disposalAdminFacade.checkAccountDispose(mobile);
        if (stringObjectMap.get("code") != null) {
            return new ResponseModel((String) stringObjectMap.get("code"),(String) stringObjectMap.get("desc"), stringObjectMap.get("content"));
        }
        return ResponseModel.succ(stringObjectMap);
    }

}
