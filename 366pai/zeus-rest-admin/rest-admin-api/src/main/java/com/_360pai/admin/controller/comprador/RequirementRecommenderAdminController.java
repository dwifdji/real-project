package com._360pai.admin.controller.comprador;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.CompradorRequirementRecommenderFacade;
import com._360pai.core.facade.comprador.req.RequiementRecommenderRemarkReq;
import com._360pai.core.facade.comprador.req.RequirementRecommenderQueryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/4 10:35
 */
@RestController
public class RequirementRecommenderAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    CompradorRequirementRecommenderFacade compradorRequirementRecommenderFacade;

    /**
     * 描述 资产大买办 详情  推介情况 列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/requirementRecommenderList")
    public ResponseModel requirementRecommenderList(@RequestBody RequirementRecommenderQueryReq req) {
        Assert.notNull(req.getRequirementId(), "requirementId 不正确");
        PageUtils.Page result = compradorRequirementRecommenderFacade.requirementRecommenderList(req);
        return ResponseModel.succ(result);
    }


    /**
     * 描述 推介情况 填写沟通结果
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/requirementRecommenderRemark")
    public ResponseModel requirementRecommenderRemark(@RequestBody RequiementRecommenderRemarkReq req) {
        Assert.notNull(req.getRequirementRecommenderId(), "requirementRecommenderId 不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = compradorRequirementRecommenderFacade.requirementRecommenderRemark(req);
        if (count > 0) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    /**
     * 描述 推介情况 撮合成功
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/requirementRecommenderMatchSuccess")
    public ResponseModel requirementRecommenderMatchSuccess(@RequestBody RequiementRecommenderRemarkReq req) {
        Assert.notNull(req.getRequirementRecommenderId(), "requirementRecommenderId 不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = compradorRequirementRecommenderFacade.requirementRecommenderMatchSuccess(req);
        if (count > 0) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

}
