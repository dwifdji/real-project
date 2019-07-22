package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetFieldGroupCondition;
import com._360pai.core.dao.asset.AssetFieldGroupDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetFieldGroup;
import com._360pai.core.service.asset.AssetFieldGroupService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFieldGroupServiceImpl implements AssetFieldGroupService {

    @Autowired
    private AssetFieldGroupDao assetFieldGroupDao;


    @Override
    public List<AssetFieldGroup> selectAllAssetFileGroupList() {
        return assetFieldGroupDao.selectAll();
    }

    @Override
    public int insertAssetFieldGroup(AssetFieldGroup params) {
        AssetFieldGroup assetFieldGroup = findAssetFieldGroupByName(params);
        if (null != assetFieldGroup) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "已存在该分组字段");
        }
        return assetFieldGroupDao.insert(params);
    }

    @Override
    public int updateAssetFieldGroup(AssetFieldGroup params) {
        AssetFieldGroup assetFieldGroupById = findAssetFieldGroupById(params);
        if (null == assetFieldGroupById) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "数据不存在");
        }
        if (StringUtils.isBlank(params.getName())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "分组名称不能为空");
        }
        AssetFieldGroup assetFieldGroup = new AssetFieldGroup();
        assetFieldGroup.setName(params.getName());
        return assetFieldGroupDao.updateById(params);
    }

    @Override
    public List<AssetFieldGroup> selectFileGroupByCondition(AssetFieldGroupCondition params) {
        return assetFieldGroupDao.selectList(params);
    }

    public AssetFieldGroup findAssetFieldGroupByName(AssetFieldGroup params) {
        AssetFieldGroupCondition condition = new AssetFieldGroupCondition();
        condition.setName(params.getName());
        return assetFieldGroupDao.selectFirst(condition);
    }

    public AssetFieldGroup findAssetFieldGroupById(AssetFieldGroup params) {
        AssetFieldGroupCondition condition = new AssetFieldGroupCondition();
        condition.setId(params.getId());
        return assetFieldGroupDao.selectFirst(condition);
    }
}