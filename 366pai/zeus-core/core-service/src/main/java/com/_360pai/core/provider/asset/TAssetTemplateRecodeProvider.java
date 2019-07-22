package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.TAssetTemplateRecodeFacade;
import com._360pai.core.facade.asset.req.TAssetTemplateRecodeReq;
import com._360pai.core.model.asset.TAssetTemplateRecode;
import com._360pai.core.service.asset.TAssetTemplateRecodeService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: TAssetTemplateRecodeProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 15:28
 */
@Component
@Service(version = "1.0.0")
public class TAssetTemplateRecodeProvider implements TAssetTemplateRecodeFacade {

    @Resource
    private TAssetTemplateRecodeService tAssetTemplateRecodeService;

    @Override
    public String addRecode(TAssetTemplateRecodeReq req) {
        TAssetTemplateRecode recode =new TAssetTemplateRecode();
        BeanUtils.copyProperties(recode,recode);
        return tAssetTemplateRecodeService.addRecode(recode);
    }
}
