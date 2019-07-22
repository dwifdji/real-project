
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.AssetEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetCondition;
import com._360pai.core.dao.asset.mapper.AssetMapper;
import com._360pai.core.model.asset.Asset;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetDao;

import java.util.List;
import java.util.Map;

@Service
public class AssetDaoImpl extends AbstractDaoImpl<Asset, AssetCondition, BaseMapper<Asset, AssetCondition>> implements AssetDao {

    @Resource
    private AssetMapper assetMapper;

    @Override
    protected BaseMapper<Asset, AssetCondition> daoSupport() {
        return assetMapper;
    }

    @Override
    protected AssetCondition blankCondition() {
        return new AssetCondition();
    }

    @Override
    public PageInfo getAssetList(int page, int perPage, Map<String, Object> params, String orderBy) {
        PageHelper.startPage(page, perPage);
        if (StringUtils.isNotBlank(orderBy)) {
            PageHelper.orderBy(orderBy);
        }
        List<Asset> list = assetMapper.getAssetList(params);
        return new PageInfo<>(list);
    }

    @Override
    public List<Map> getAgencyCode(Integer agencyId) {
        return assetMapper.getAgencyCode(agencyId);
    }

    @Override
    public int updateStatus(Integer assetId, AssetEnum.Status status) {
        Asset asset = new Asset();
        asset.setId(assetId);
        asset.setStatus(status);
        return assetMapper.updateById(asset);
    }

    @Override
    public List<Map> myAsset(Map<String, Object> params) {
        return assetMapper.myAsset(params);
    }

    @Override
    public String getCategoryName(Integer assetId) {
        return assetMapper.getCategoryName(assetId);
    }

    @Override
    public PageInfo getListByPage(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<Asset> list = assetMapper.selectAll();
        return new PageInfo<>(list);
    }
}
