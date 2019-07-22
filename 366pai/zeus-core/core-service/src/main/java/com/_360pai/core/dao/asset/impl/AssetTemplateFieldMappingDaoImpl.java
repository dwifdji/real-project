
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetTemplateFieldMappingCondition;
import com._360pai.core.dao.asset.mapper.AssetTemplateFieldMappingMapper;
import com._360pai.core.model.asset.AssetTemplateFieldMapping;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetTemplateFieldMappingDao;

@Service
public class AssetTemplateFieldMappingDaoImpl extends AbstractDaoImpl<AssetTemplateFieldMapping, AssetTemplateFieldMappingCondition, BaseMapper<AssetTemplateFieldMapping, AssetTemplateFieldMappingCondition>> implements AssetTemplateFieldMappingDao {

    @Resource
    private AssetTemplateFieldMappingMapper assetTemplateFieldMappingMapper;

    @Override
    protected BaseMapper<AssetTemplateFieldMapping, AssetTemplateFieldMappingCondition> daoSupport() {
        return assetTemplateFieldMappingMapper;
    }

    @Override
    protected AssetTemplateFieldMappingCondition blankCondition() {
        return new AssetTemplateFieldMappingCondition();
    }

    @Override
    public Boolean isInMapping(Integer groupId, Integer fieldId) {
        return assetTemplateFieldMappingMapper.isInMapping(groupId, fieldId);
    }

    @Override
    public int deleteMapping(Integer groupId) {
        return assetTemplateFieldMappingMapper.deleteMapping(groupId);
    }
}
