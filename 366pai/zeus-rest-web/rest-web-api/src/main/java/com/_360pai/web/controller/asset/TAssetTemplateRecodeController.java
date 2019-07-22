package com._360pai.web.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.core.facade.asset.TAssetTemplateRecodeFacade;
import com._360pai.core.facade.asset.req.TAssetTemplateRecodeReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zxiao
 * @Title: TAssetTemplateRecodeController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 15:27
 */
@RestController
public class TAssetTemplateRecodeController {

    @Reference(version = "1.0.0")
    private TAssetTemplateRecodeFacade tAssetTemplateRecodeFacade;

    @RequestMapping(value = "/confined/temp/addRecode")
    public ResponseModel addRecode(TAssetTemplateRecodeReq req) {

        String version = tAssetTemplateRecodeFacade.addRecode(req);

        return ResponseModel.succ(version);
    }
}
