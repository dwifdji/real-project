package com._360pai.admin.controller.withfudig;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.withfudig.WithfudigRequirementInvestFacade;
import com._360pai.core.facade.withfudig.req.WithfudigRequirementInvestReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 16:15
 */
@RestController
public class WithfudigRequirementInvestAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    WithfudigRequirementInvestFacade withfudigRequirementInvestFacade;


    /**
     * 描述 配资乐 详情  投标列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/requirementInvestList")
    public ResponseModel requirementInvestList(@RequestBody WithfudigRequirementInvestReq.RequirementInvestList req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        PageUtils.Page result = withfudigRequirementInvestFacade.requirementInvestList(req);
        return ResponseModel.succ(result);
    }


    /**
     * 描述 配资乐 推介情况 填写沟通结果
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/requirementInvestRemark")
    public ResponseModel requirementInvestRemark(@RequestBody WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        Assert.notNull(req.getRequirementInvestId(), "requirementInvestId 参数不能为空");
        Assert.notNull(req.getRemark(), "remark 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigRequirementInvestFacade.requirementInvestRemark(req);
        return ResponseModel.wrapCount(count);
    }

    /**
     * 描述 配资乐 推介情况 撮合成功
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/withfudig/requirementInvestMatchSuccess")
    public ResponseModel requirementInvestMatchSuccess(@RequestBody WithfudigRequirementInvestReq.RequirementInvestRemark req) {
        Assert.notNull(req.getRequirementInvestId(), "requirementInvestId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = withfudigRequirementInvestFacade.requirementInvestMatchSuccess(req);
        return ResponseModel.wrapCount(count);
    }

}
