
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetDataCondition;
import com._360pai.core.dao.asset.mapper.AssetDataMapper;
import com._360pai.core.model.asset.AssetData;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetDataDao;

import java.util.List;

@Service
public class AssetDataDaoImpl extends AbstractDaoImpl<AssetData, AssetDataCondition, BaseMapper<AssetData, AssetDataCondition>> implements AssetDataDao {

    @Resource
    private AssetDataMapper assetDataMapper;

    @Override
    protected BaseMapper<AssetData, AssetDataCondition> daoSupport() {
        return assetDataMapper;
    }

    @Override
    protected AssetDataCondition blankCondition() {
        return new AssetDataCondition();
    }

    @Override
    public AssetData findAssetData(Integer assetId) {
        AssetDataCondition condition = new AssetDataCondition();
        condition.setAssetId(assetId);
        List<AssetData> assetData = assetDataMapper.selectByCondition(condition);
        if (!assetData.isEmpty()) {
            if (assetData.size() > 2) {
                throw new RuntimeException("sql查询结果大于1");
            }
            return assetData.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<AssetData> find(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<AssetData> list = assetDataMapper.selectAll();
        return new PageInfo<>(list);
    }
}
