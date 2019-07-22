package com._360pai.core.provider.asset;

import com._360pai.arch.common.utils.PageUtils;
import com._360pai.core.condition.asset.TAssetPropertyActivityMapCondition;
import com._360pai.core.facade.asset.TAssetPropertyActivityMapFacade;
import com._360pai.core.facade.asset.req.TAssetPropertyActivityMapReq;
import com._360pai.core.model.asset.TAssetPropertyActivityMap;
import com._360pai.core.service.asset.TAssetPropertyActivityMapService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivityMapProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/5 10:20
 */
@Component
@Service(version = "1.0.0")
public class TAssetPropertyActivityMapProvider implements TAssetPropertyActivityMapFacade {
    @Autowired
    private TAssetPropertyActivityMapService tAssetPropertyActivityMapService;

    @Override
    public int insertTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req) {
        return tAssetPropertyActivityMapService.insertTAssetPropertyActivityMap(copyTAssetPropertyActivityMap(req));
    }

    @Override
    public int updateTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req) {
        return tAssetPropertyActivityMapService.updateTAssetPropertyActivityMap(copyTAssetPropertyActivityMap(req));
    }

    @Override
    public PageInfo selectTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req) {
        TAssetPropertyActivityMapCondition condition = new TAssetPropertyActivityMapCondition();
        condition.setAssetPropertyId(req.getAssetPropertyId());
        return tAssetPropertyActivityMapService.selectTAssetPropertyActivityMap(req.getPage(),req.getPerPage());
    }

    private TAssetPropertyActivityMap copyTAssetPropertyActivityMap(TAssetPropertyActivityMapReq req) {
        TAssetPropertyActivityMap activityMap = new TAssetPropertyActivityMap();
        BeanUtils.copyProperties(req, activityMap);
        return activityMap;
    }


}
