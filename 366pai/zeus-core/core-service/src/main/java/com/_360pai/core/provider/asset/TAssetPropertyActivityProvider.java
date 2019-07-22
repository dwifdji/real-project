package com._360pai.core.provider.asset;

import com._360pai.core.facade.TAssetPropertyActivityFacade;
import com._360pai.core.facade.asset.req.TAssetPropertyActivityReq;
import com._360pai.core.model.asset.TAssetPropertyActivity;
import com._360pai.core.service.asset.TAssetPropertyActivityService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivityProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/18 20:28
 */
@Component
@Service(version = "1.0.0")
public class TAssetPropertyActivityProvider implements TAssetPropertyActivityFacade {
    @Autowired
    private TAssetPropertyActivityService tAssetPropertyActivityService;

    @Override
    public PageInfo propertySearch(TAssetPropertyActivityReq req) {
        return tAssetPropertyActivityService.propertySearch(req.getPage(), req.getPerPage(), req.getAssetPropertyId());
    }

    @Override
    public int addTAssetPropertyActivity(TAssetPropertyActivityReq req) {
        return tAssetPropertyActivityService.addTAssetPropertyActivity(copy(req));
    }

    @Override
    public int editTAssetPropertyActivity(TAssetPropertyActivityReq req) {
        return tAssetPropertyActivityService.editTAssetPropertyActivity(copy(req));
    }

    @Override
    public int deleteTAssetPropertyActivity(TAssetPropertyActivityReq req) {
        return tAssetPropertyActivityService.deleteTAssetPropertyActivity(copy(req));
    }

    private TAssetPropertyActivity copy(TAssetPropertyActivityReq req) {
        TAssetPropertyActivity assetPropertyActivity = new TAssetPropertyActivity();
        BeanUtils.copyProperties(req, assetPropertyActivity);
        return assetPropertyActivity;
    }
}
