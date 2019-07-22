package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetTemplateFieldCondition;
import com._360pai.core.condition.asset.AssetTemplateFieldOptionCondition;
import com._360pai.core.dao.asset.AssetTemplateFieldDao;
import com._360pai.core.dao.asset.AssetTemplateFieldOptionDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetTemplateField;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.core.service.asset.AssetTemplateFieldService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetTemplateFieldServiceImpl implements AssetTemplateFieldService {

    @Autowired
    private AssetTemplateFieldDao assetTemplateFieldDao;
    @Autowired
    private AssetTemplateFieldOptionDao assetTemplateFieldOptionDao;


    @Override
    public PageInfo findAssetTemplateFieldList(int page, int prePaeg) {
        PageHelper.startPage(page, prePaeg);
        List<AssetTemplateField> assetTemplateFields = assetTemplateFieldDao.selectAll();
        return new PageInfo<>(assetTemplateFields);
    }

    @Override
    public int addAssetTemplateField(AssetTemplateField param) {
        if (StringUtils.isBlank(param.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "筛选器名称不能为空");
        AssetTemplateField assetTemplateField = findAssetTemplateFieldByName(param);
        if (assetTemplateField != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称不能重复");
        }
        return assetTemplateFieldDao.insert(param);
    }

    @Override
    public int editAssetTemplateField(AssetTemplateField param) {
        AssetTemplateField assetTemplateField = findAssetTemplateFieldById(param);
        if (assetTemplateField == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的筛选器不存在");
        }
        return assetTemplateFieldDao.updateById(param);
    }

    @Override
    public int deleteAssetTemplateField(AssetTemplateField param) {
        return 0;
    }

    @Override
    public Object detailAssetTemplateField(AssetTemplateField param) {
        AssetTemplateField assetTemplateField = findAssetTemplateFieldById(param);
        if (assetTemplateField == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "查看的筛选器不存在");
        }
        AssetTemplateFieldOptionCondition condition = new AssetTemplateFieldOptionCondition();
        condition.setFieldId(assetTemplateField.getId());
        List<AssetTemplateFieldOption> assetTemplateFieldOptions = assetTemplateFieldOptionDao.selectList(condition);
        assetTemplateField.setOptions(assetTemplateFieldOptions);
        return assetTemplateField;
    }

    public AssetTemplateField findAssetTemplateFieldByName(AssetTemplateField param) {
        AssetTemplateFieldCondition condition = new AssetTemplateFieldCondition();
        condition.setName(param.getName());
        return assetTemplateFieldDao.selectFirst(condition);
    }

    public AssetTemplateField findAssetTemplateFieldById(AssetTemplateField param) {
        AssetTemplateFieldCondition condition = new AssetTemplateFieldCondition();
        condition.setId(param.getId());
        return assetTemplateFieldDao.selectFirst(condition);
    }
}