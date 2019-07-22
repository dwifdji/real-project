package com._360pai.partner.controller.asset;

import com._360pai.arch.common.ResponseModel;
import com._360pai.arch.common.enums.SideType;
import com._360pai.core.facade.asset.TAssetTemplateCategoryFacade;
import com._360pai.core.facade.asset.req.TAssetTemplateCategoryReq;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author zxiao
 * @Title: TAssetTemplateCategoryController
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/1 14:02
 */
@RestController
public class TAssetTemplateCategoryController {

    @Reference(version = "1.0.0")
    private TAssetTemplateCategoryFacade tAssetTemplateCategoryFacade;


    /**
     * 功能描述: 首页获取模板
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/9/26 16:55
     */
    @PostMapping(value = "/agency/getTemplate")
    public ResponseModel getTemplateCopy(@RequestBody TAssetTemplateCategoryReq req) {
        req.setSideType(SideType.AGENCY);
        Object object = tAssetTemplateCategoryFacade.getTemplateCopy(req);
        return ResponseModel.succ(object);
    }

    @PostMapping(value = "/agency/getTemplateParty")
    public ResponseModel getTemplateParty(@RequestBody TAssetTemplateCategoryReq req) {
        Object object = tAssetTemplateCategoryFacade.getTemplateParty(2, 20);
        Map<String, Object> jsonObject = new TreeMap<>();
        jsonObject.put("fields", object);
        return ResponseModel.succ(jsonObject);
    }

}
