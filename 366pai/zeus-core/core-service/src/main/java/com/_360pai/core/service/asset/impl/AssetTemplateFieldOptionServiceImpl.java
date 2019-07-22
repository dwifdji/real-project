package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetTemplateFieldOptionCondition;
import com._360pai.core.dao.asset.AssetTemplateFieldOptionDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.core.service.asset.AssetTemplateFieldOptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetTemplateFieldOptionServiceImpl implements AssetTemplateFieldOptionService {

    @Autowired
    private AssetTemplateFieldOptionDao assetTemplateFieldOptionDao;


    @Override
    public int addAssetTemplateFieldOption(AssetTemplateFieldOption params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能为空");
        AssetTemplateFieldOption assetTemplateFieldOptionByName = findAssetTemplateFieldOptionByName(params);
        if (assetTemplateFieldOptionByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return assetTemplateFieldOptionDao.insert(params);
    }

    @Override
    public int editAssetTemplateFieldOption(AssetTemplateFieldOption params) {
        AssetTemplateFieldOption assetTemplateFieldOptionById = findAssetTemplateFieldOptionById(params);
        if (assetTemplateFieldOptionById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的模板字段不存在");
        }
        AssetTemplateFieldOption assetTemplateFieldOptionByName = findAssetTemplateFieldOptionByName(params);
        if (assetTemplateFieldOptionByName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称已存在");
        }
        AssetTemplateFieldOption option = new AssetTemplateFieldOption();
        option.setId(params.getId());
        option.setName(params.getName());
        return assetTemplateFieldOptionDao.updateById(option);
    }

    @Override
    public int editWeightAssetTemplateFieldOption(AssetTemplateFieldOption params) {
        AssetTemplateFieldOption assetTemplateFieldOptionById = findAssetTemplateFieldOptionById(params);
        if (assetTemplateFieldOptionById == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的模板字段不存在");
        }
        AssetTemplateFieldOption option = new AssetTemplateFieldOption();
        option.setId(params.getId());
        option.setSearchWeight(params.getSearchWeight());
        return assetTemplateFieldOptionDao.updateById(option);
    }

    @Override
    @Transactional
    public int deleteAssetTemplateFieldOption(AssetTemplateFieldOption params) {
        if (params.getId() == 0) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "删除模板不存在");
        return assetTemplateFieldOptionDao.deleteAssetTemplateFieldOption(params.getId());
    }

    private AssetTemplateFieldOption findAssetTemplateFieldOptionByName(AssetTemplateFieldOption params) {
        AssetTemplateFieldOptionCondition condition = new AssetTemplateFieldOptionCondition();
        condition.setName(params.getName());
        return assetTemplateFieldOptionDao.selectOneResult(condition);
    }

    private AssetTemplateFieldOption findAssetTemplateFieldOptionById(AssetTemplateFieldOption params) {
        AssetTemplateFieldOptionCondition condition = new AssetTemplateFieldOptionCondition();
        condition.setId(params.getId());
        return assetTemplateFieldOptionDao.selectOneResult(condition);
    }
}