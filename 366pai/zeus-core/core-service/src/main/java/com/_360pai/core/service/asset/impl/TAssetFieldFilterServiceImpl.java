package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetFieldFilterCondition;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterDao;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.TAssetFieldFilter;
import com._360pai.core.model.asset.TAssetFieldFilterOption;
import com._360pai.core.service.asset.TAssetFieldFilterService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAssetFieldFilterServiceImpl implements TAssetFieldFilterService {

    @Autowired
    private TAssetFieldFilterDao tAssetFieldFilterDao;
    @Autowired
    private TAssetFieldFilterOptionDao assetFieldFilterOptionDao;


    @Override
    public PageInfo findAssetTemplateFieldList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<TAssetFieldFilter> tAssetFieldFilters = tAssetFieldFilterDao.selectAll();
        return new PageInfo<>(tAssetFieldFilters);
    }

    @Override
    public Object detailAssetTemplateField(TAssetFieldFilter req) {
        TAssetFieldFilter tAssetFieldFilterById = findTAssetFieldFilterById(req);
        if (tAssetFieldFilterById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "筛选器不存在");
        }

        TAssetFieldFilterOptionCondition condition = new TAssetFieldFilterOptionCondition();
        condition.setFilterId(req.getId());
        List<TAssetFieldFilterOption> tAssetFieldFilterOptions = assetFieldFilterOptionDao.selectList(condition);
        tAssetFieldFilterById.setOptions(tAssetFieldFilterOptions);
        return tAssetFieldFilterById;
    }

    @Override
    public int addAssetTemplateField(TAssetFieldFilter params) {
        TAssetFieldFilter tAssetFieldFilterByName = findTAssetFieldFilterByName(params);
        if (tAssetFieldFilterByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return tAssetFieldFilterDao.insert(params);
    }

    @Override
    public int editAssetTemplateField(TAssetFieldFilter params) {
        TAssetFieldFilter tAssetFieldFilterById = findTAssetFieldFilterById(params);
        if (tAssetFieldFilterById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "筛选器不存在");
        }
        return tAssetFieldFilterDao.updateById(params);
    }

    private TAssetFieldFilter findTAssetFieldFilterById(TAssetFieldFilter req) {
        TAssetFieldFilterCondition condition = new TAssetFieldFilterCondition();
        condition.setId(req.getId());
        return tAssetFieldFilterDao.selectOneResult(condition);
    }

    private TAssetFieldFilter findTAssetFieldFilterByName(TAssetFieldFilter req) {
        TAssetFieldFilterCondition condition = new TAssetFieldFilterCondition();
        condition.setName(req.getName());
        return tAssetFieldFilterDao.selectOneResult(condition);
    }
}