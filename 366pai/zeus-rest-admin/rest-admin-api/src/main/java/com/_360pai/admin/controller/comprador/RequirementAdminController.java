package com._360pai.admin.controller.comprador;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.common.constants.CompradorEnum;
import com._360pai.core.facade.comprador.CompradorRequirementFacade;
import com._360pai.core.facade.comprador.req.CompradorRequirementQueryReq;
import com._360pai.core.facade.comprador.req.CompradorRequirementReq;
import com._360pai.core.facade.comprador.req.RequirementAdminQueryRep;
import com._360pai.core.facade.comprador.resp.CompradorDetailResp;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 大买办置业需求
 *
 * @author : whisky_vip
 * @date : 2018/8/30 10:12
 */
@RestController
public class RequirementAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    CompradorRequirementFacade compradorRequirementFacade;

    /**
     * 描述 大买办置业需求 列表
     *
     * @author : whisky_vip
     * @date : 2018/9/1 9:19
     */
    @RequiresPermissions("fwxqgl_dmbgl:dmbzy_list")
    @PostMapping("/admin/comprador/requirementList")
    public ResponseModel requirementList(@RequestBody RequirementAdminQueryRep req) {
        PageUtils.Page resp = compradorRequirementFacade.requirementListForAdmin(req);
        return ResponseModel.succ(resp);
    }

    /**
     * 描述: 大买办置业需求 查看详情
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/requirementDetail")
    public ResponseModel requirementDetail(@RequestBody CompradorRequirementQueryReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        CompradorDetailResp result = compradorRequirementFacade.requirementDetail(req,true);
        return ResponseModel.succ(result);
    }

    /**
     * 描述 大买办置业需求 审核通过
     *
     * @author : whisky_vip
     * @date : 2018/8/30 10:12
     */
    @PostMapping("/admin/comprador/requirementAuditPass")
    public ResponseModel requirementAuditPass(@RequestBody CompradorRequirementReq.RequirementStatusUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        req.setStatus(CompradorEnum.RequirementStatus.PASS_FOR_PAY.getValue().toString());
        int count = compradorRequirementFacade.requirementAuditPass(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 大买办置业需求 审核拒绝
     *
     * @author : whisky_vip
     * @date : 2018/11/14 15:56
     */
    @PostMapping("/admin/comprador/requirementAuditNoPass")
    public ResponseModel requirementAuditNoPass(@RequestBody CompradorRequirementReq.RequirementStatusUpdate req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        req.setStatus(CompradorEnum.RequirementStatus.NO_PASS.getValue().toString());
        int count = compradorRequirementFacade.requirementAuditNoPass(req);
        return ResponseModel.wrapCount(count);
    }


    /**
     * 描述: 大买办置业需求 手动点击 已完成
     * 更新主表变成已完成
     * 然后更新附表所有数据为已完成
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/requirementFinished")
    public ResponseModel requirementFinished(@RequestBody CompradorRequirementQueryReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        req.setOperatorId(loadCurLoginId());
        int count = compradorRequirementFacade.requirementFinished(req);
        return ResponseModel.wrapCount(count);
    }

}
