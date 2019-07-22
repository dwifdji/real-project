package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetPropertyActivityMapCondition;
import com._360pai.core.dao.asset.TAssetPropertyActivityMapDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.activity.ActivityFacade;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.core.model.asset.TAssetPropertyActivityMap;
import com._360pai.core.service.asset.AssetPropertyService;
import com._360pai.core.service.asset.TAssetPropertyActivityMapService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zxiao
 * @Title: TAssetPropertyActivityMapServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/5 10:16
 */
@Service
public class TAssetPropertyActivityMapServiceImpl implements TAssetPropertyActivityMapService {
    @Autowired
    private TAssetPropertyActivityMapDao tAssetPropertyActivityMapDao;
    @Autowired
    private ActivityFacade activityFacade;
    @Autowired
    private AssetPropertyService assetPropertyService;

    @Override
    public int insertTAssetPropertyActivityMap(TAssetPropertyActivityMap params) {
        TAssetPropertyActivityMap byNameAndActivityId = findByNameAndActivityId(params);
        if (byNameAndActivityId != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该活动已存在该类型下，不能添加");
        }
        return tAssetPropertyActivityMapDao.insert(params);
    }

    @Override
    public int updateTAssetPropertyActivityMap(TAssetPropertyActivityMap params) {
        TAssetPropertyActivityMap activityMap = findById(params);
        if (activityMap == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的活动不存在");
        }
        Object activityFacadeById = activityFacade.getById(params.getActivityId());
        if (activityFacadeById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的活动不存在");
        }
        AssetProperty assetProperty = assetPropertyService.getById(params.getAssetPropertyId());
        if (assetProperty == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的类型不存在");
        }

        if (!activityMap.getActivityId().equals(params.getActivityId())
                && !activityMap.getAssetPropertyId().equals(params.getAssetPropertyId())) {
            TAssetPropertyActivityMap byNameAndActivityId = findByNameAndActivityId(params);
            if (byNameAndActivityId != null) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该活动已存在该类型下，不能添加");
            }
        }
        return tAssetPropertyActivityMapDao.updateById(params);
    }

    @Override
    public PageInfo selectTAssetPropertyActivityMap(int page, int perPage) {
        return null;
    }

    private TAssetPropertyActivityMap findByNameAndActivityId(TAssetPropertyActivityMap params) {
        TAssetPropertyActivityMapCondition condition = new TAssetPropertyActivityMapCondition();
        condition.setActivityId(params.getActivityId());
        condition.setAssetPropertyId(params.getAssetPropertyId());
        return tAssetPropertyActivityMapDao.selectOneResult(condition);
    }

    private TAssetPropertyActivityMap findById(TAssetPropertyActivityMap params) {
        TAssetPropertyActivityMapCondition condition = new TAssetPropertyActivityMapCondition();
        condition.setId(params.getId());
        return tAssetPropertyActivityMapDao.selectOneResult(condition);
    }
}
