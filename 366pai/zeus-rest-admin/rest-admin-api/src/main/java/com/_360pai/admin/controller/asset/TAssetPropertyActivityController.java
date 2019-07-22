package com._360pai.admin.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.TAssetPropertyActivityFacade;
import com._360pai.core.facade.asset.req.TAssetPropertyActivityReq;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivity
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/27 11:18
 */
@RestController
public class TAssetPropertyActivityController {

    @Reference(version = "1.0.0")
    private TAssetPropertyActivityFacade tAssetPropertyActivityFacade;

    @GetMapping(value = "/admin/assetPropertyActivity/list")
    public ResponseModel getTAssetPropertyActivityList(TAssetPropertyActivityReq req){
        PageInfo pageInfo = tAssetPropertyActivityFacade.propertySearch(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/assetPropertyActivity/add")
    public ResponseModel addTAssetPropertyActivity(@RequestBody TAssetPropertyActivityReq req){
        Assert.notNull(req.getAssetPropertyId(),"拍品类型ID不能为空");
        Assert.notNull(req.getActivityId(),"活动ID不能为空");
        Assert.notNull(req.getActivityType(),"活动类型不能为空");
        int pageInfo = tAssetPropertyActivityFacade.addTAssetPropertyActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/assetPropertyActivity/edit")
    public ResponseModel editTAssetPropertyActivity(@RequestBody TAssetPropertyActivityReq req){
        Assert.notNull(req.getId(),"当前ID不能为空");
        Assert.notNull(req.getAssetPropertyId(),"拍品类型ID不能为空");
        Assert.notNull(req.getActivityId(),"活动ID不能为空");
        Assert.notNull(req.getActivityType(),"活动类型不能为空");
        int pageInfo = tAssetPropertyActivityFacade.editTAssetPropertyActivity(req);
        return ResponseModel.succ(pageInfo);
    }

    @PostMapping(value = "/admin/assetPropertyActivity/delete")
    public ResponseModel deleteTAssetPropertyActivity(@RequestBody TAssetPropertyActivityReq req){
        Assert.notNull(req.getId(),"当前ID不能为空");
        int pageInfo = tAssetPropertyActivityFacade.deleteTAssetPropertyActivity(req);
        return ResponseModel.succ(pageInfo);
    }

}
