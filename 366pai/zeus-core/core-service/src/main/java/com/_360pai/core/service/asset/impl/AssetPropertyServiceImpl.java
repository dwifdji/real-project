package com._360pai.core.service.asset.impl;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 10:11
 */

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.dao.asset.AssetPropertyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.core.service.asset.AssetPropertyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetPropertyServiceImpl implements AssetPropertyService {

    @Autowired
    private AssetPropertyDao assetPropertyDao;

    @Override
    public AssetProperty getById(Integer propertyId) {
        AssetPropertyCondition condition = new AssetPropertyCondition();
        condition.setId(propertyId);
        return assetPropertyDao.selectFirst(condition);
    }

    @Override
    public PageInfo getPropertiesList(int page, int perPage, AssetPropertyCondition condition) {
        PageHelper.startPage(page, perPage);
        List<AssetProperty> assetProperties = assetPropertyDao.selectList(condition);
        return new PageInfo<>(assetProperties);
    }

    @Override
    public int addAssetProperties(AssetProperty params) {
        if (StringUtils.isBlank(params.getName())) throw new BusinessException(ApiCallResult.FAILURE.getCode(), "属性名称不能没空");
        AssetProperty assetProperty = findAssetPropertyByName(params);
        if (assetProperty != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称重复");
        }
        return assetPropertyDao.insert(params);
    }

    @Override
    public int editAssetProperties(AssetProperty param) {
        AssetProperty assetProperty = findAssetPropertyById(param);
        if (assetProperty == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的属性不存在");
        }
        return assetPropertyDao.updateById(param);
    }

    @Override
    public Object getProperties() {
        return assetPropertyDao.getProperties();
    }

    @Override
    public List<AssetProperty> getAssetPropertyListByType(String type) {
        AssetPropertyCondition assetPropertyCondition = new AssetPropertyCondition();
        assetPropertyCondition.setType(type);
        return assetPropertyDao.selectList(assetPropertyCondition);
    }

    private AssetProperty findAssetPropertyByName(AssetProperty param) {
        AssetPropertyCondition condition = new AssetPropertyCondition();
        condition.setName(param.getName());
        return assetPropertyDao.selectFirst(condition);
    }

    private AssetProperty findAssetPropertyById(AssetProperty param) {
        AssetPropertyCondition condition = new AssetPropertyCondition();
        condition.setId(param.getId());
        return assetPropertyDao.selectFirst(condition);
    }
}