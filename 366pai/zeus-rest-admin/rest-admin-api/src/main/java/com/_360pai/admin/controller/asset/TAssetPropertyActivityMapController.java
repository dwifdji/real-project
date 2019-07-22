package com._360pai.admin.controller.asset;

import com._360pai.core.facade.asset.TAssetPropertyActivityMapFacade;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivityMapController  设置活动分类
 * @ProjectName zeus-core
 * @Description:
 * @date 2018/9/5 10:18
 */
@RestController
public class TAssetPropertyActivityMapController {

    @Reference(version = "1.0.0")
    private TAssetPropertyActivityMapFacade tAssetPropertyActivityMapFacade;



}
