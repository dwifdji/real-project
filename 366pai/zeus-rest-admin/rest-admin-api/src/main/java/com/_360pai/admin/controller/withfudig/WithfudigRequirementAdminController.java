package com._360pai.admin.controller.withfudig;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.ServiceFollowEnum;
import com._360pai.core.common.constants.WithfudigEnum;
import com._360pai.core.facade.assistant.ServiceFollowFacade;
import com._360pai.core.facade.assistant.req.ServiceFollowReq;
import com._360pai.core.facade.withfudig.WithfudigRequirementFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementReq;
import com._360pai.core.facade.withfudig.resp.WithfudigRequirementDetailResp;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 配资乐 需求单
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:14
 */
@RestController
public class WithfudigRequirementAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigRequirementFacade withfudigRequirementFacade;
    @Reference(version = "1.0.0")
    ServiceFollowFacade        serviceFollowFacade;

    /**
     * 描述 配资乐需求 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/7 15:22
     */
    @RequiresPermissions("fwxqgl_pzlgl:pzlrzxq_list")
    @PostMapping("/admin/withfudig/requirementList")
    public ResponseModel requirementList(@RequestBody WithfudigRequirementReq.RequirementListForAdmin req) {
        PageUtils.Page resp = withfudigRequirementFacade.requirementListForAdmin(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述: 配资乐需求 查看详情
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/requirementDetail")
    public ResponseModel requirementDetail(@RequestBody WithfudigRequirementReq.RequirementDetailReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        WithfudigRequirementDetailResp result = withfudigRequirementFacade.requirementDetail(req, true);
        return ResponseModel.succ(result);
    }


    /**
     * 描述 特别告知 修改
     *
     * @author : whisky_vip
     * @date : 2018/9/19 10:05
     */
    @PostMapping("/admin/withfudig/specialNoticeUpdate")
    public ResponseModel specialNoticeUpdate(@RequestBody WithfudigRequirementReq.SpecialNoticeUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigRequirementFacade.specialNoticeUpdate(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 配资乐需求 审核通过
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:12
     */
    @PostMapping("/admin/withfudig/requirementAuditPass")
    public ResponseModel requirementAuditPass(@RequestBody WithfudigRequirementReq.RequirementStatusUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        req.setStatus(WithfudigEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString());
        int count = withfudigRequirementFacade.updateRequirementStatus(req);
        return ResponseModel.wrapCount(count);
    }
    /**
     * 描述 配资乐需求 审核拒绝
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:12
     */
    @PostMapping("/admin/withfudig/requirementAuditNoPass")
    public ResponseModel requirementAuditNoPass(@RequestBody WithfudigRequirementReq.RequirementStatusUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        req.setStatus(WithfudigEnum.RequirementStatus.NO_PASS.getValue().toString());
        int count = withfudigRequirementFacade.updateRequirementStatus(req);
        return ResponseModel.wrapCount(count);
    }
    /**
     * 描述 配资乐需求 二次审核通过-配资中
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:12
     */
    @PostMapping("/admin/withfudig/requirementSecondAuditPass")
    public ResponseModel requirementSecondAuditPass(@RequestBody WithfudigRequirementReq.RequirementStatusUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        req.setStatus(WithfudigEnum.RequirementStatus.WITHFUDIG_ING.getValue().toString());
        int count = withfudigRequirementFacade.updateRequirementStatus(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述: 配资乐需求 手动点击 已完成
     * 更新主表变成已完成
     * 然后更新附表所有数据为已完成
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/requirementFinished")
    public ResponseModel requirementFinished(@RequestBody WithfudigRequirementReq.RequirementId req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigRequirementFacade.requirementFinished(req);
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述 关注人列表
     *
     * @author : whisky_vip
     * @date : 2018/9/17 14:10
     */
    @PostMapping(value = "/admin/withfudig/serviceFollowList")
    public ResponseModel withfudigServiceFollowList(@RequestBody ServiceFollowReq.List req) {
        Assert.notNull(req.getRequirementId(), "requirementId 参数不对");
        req.setRelativeType(ServiceFollowEnum.RelativeType.WITHFUDIG.getKey());
        PageUtils.Page page = serviceFollowFacade.serviceFollowList(req);
        return ResponseModel.succ(page);
    }


}
