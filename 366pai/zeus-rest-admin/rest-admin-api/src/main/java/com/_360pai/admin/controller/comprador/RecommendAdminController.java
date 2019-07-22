package com._360pai.admin.controller.comprador;

import com._360pai.admin.controller.AbstractController;
import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.facade.comprador.CompradorRecommendFacade;
import com._360pai.core.facade.comprador.req.RecommendListReq;
import com._360pai.core.facade.comprador.req.RecommendReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述 资产大买办 我有资产推荐
 *
 * @author : whisky_vip
 * @date : 2018/9/4 11:23
 */
@RestController
public class RecommendAdminController extends AbstractController {

    @Reference(version = "1.0.0")
    CompradorRecommendFacade compradorRecommendFacade;

    /**
     * 描述 大买办推介需求 列表
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @RequiresPermissions("fwxqgl_dmbgl:dmbtj_list")
    @PostMapping("/admin/comprador/recommendList")
    public ResponseModel recommendList(@RequestBody RecommendListReq req) {
        PageUtils.Page result = compradorRecommendFacade.recommendListForAdmin(req);
        return ResponseModel.succ(result);
    }


    /**
     * 描述 撮合成功
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/recommendMatchSuccess")
    public ResponseModel recommendMatchSuccess(@RequestBody RecommendReq req) {
        Assert.notNull(req.getRecommendId(), "recommendId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = compradorRecommendFacade.recommendMatchSuccess(req);
        if (count > 0) {
            return ResponseModel.succ();
        }
        return ResponseModel.fail();
    }

    /**
     * 描述 手工完成
     *
     * @author : whisky_vip
     * @date : 2018/8/28 10:38
     */
    @PostMapping("/admin/comprador/recommendFinished")
    public ResponseModel recommendFinished(@RequestBody RecommendReq req) {
        Assert.notNull(req.getRecommendId(), "recommendId 参数不能为空");
        req.setOperatorId(loadCurLoginId());
        int count = compradorRecommendFacade.recommendFinished(req);
        return ResponseModel.wrapCount(count);
    }

}
