package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionCondition;
import com._360pai.core.dao.asset.TAssetFieldFilterOptionDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.TAssetFieldFilterOption;
import com._360pai.core.service.asset.TAssetFieldFilterOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TAssetFieldFilterOptionServiceImpl implements TAssetFieldFilterOptionService {

    @Autowired
    private TAssetFieldFilterOptionDao tAssetFieldFilterOptionDao;


    @Override
    public int addAssetTemplateFieldOption(TAssetFieldFilterOption params) {
        TAssetFieldFilterOption tAssetFieldFilterOptionByName = findTAssetFieldFilterOptionByName(params);
        if (tAssetFieldFilterOptionByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return tAssetFieldFilterOptionDao.insert(params);
    }

    @Override
    public int editAssetTemplateFieldOption(TAssetFieldFilterOption params) {
        TAssetFieldFilterOption tAssetFieldFilterOptionById = findTAssetFieldFilterOptionById(params);
        if (tAssetFieldFilterOptionById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "二级筛选器不存在");
        }
        return tAssetFieldFilterOptionDao.updateById(params);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAssetTemplateFieldOption(TAssetFieldFilterOption params) {
        TAssetFieldFilterOption tAssetFieldFilterOptionById = findTAssetFieldFilterOptionById(params);
        if (tAssetFieldFilterOptionById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(),"删除的二级筛选器不存在");
        }
        return tAssetFieldFilterOptionDao.deleteAssetTemplateFieldOption(params.getId());
    }

    private TAssetFieldFilterOption findTAssetFieldFilterOptionById(TAssetFieldFilterOption params) {
        TAssetFieldFilterOptionCondition optionCondition = new TAssetFieldFilterOptionCondition();
        optionCondition.setId(params.getId());
        return tAssetFieldFilterOptionDao.selectOneResult(optionCondition);
    }

    private TAssetFieldFilterOption findTAssetFieldFilterOptionByName(TAssetFieldFilterOption params) {
        TAssetFieldFilterOptionCondition optionCondition = new TAssetFieldFilterOptionCondition();
        optionCondition.setName(params.getName());
        optionCondition.setFilterId(params.getFilterId());
        return tAssetFieldFilterOptionDao.selectOneResult(optionCondition);
    }
}