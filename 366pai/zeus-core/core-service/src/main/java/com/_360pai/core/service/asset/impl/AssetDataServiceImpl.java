package com._360pai.core.service.asset.impl;

import com._360pai.core.condition.asset.AssetDataCondition;
import com._360pai.core.dao.asset.AssetDataDao;
import com._360pai.core.model.asset.AssetData;
import com._360pai.core.service.asset.AssetDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 描述:
 *
 * @author : whisky_vip
 * @date : 2018/8/17 15:17
 */
@Service
public class AssetDataServiceImpl implements AssetDataService {

    @Autowired
    private AssetDataDao assetDataDao;

    @Override
    public AssetData getByAssetId(Integer assetId) {
        AssetDataCondition condition = new AssetDataCondition();
        condition.setAssetId(assetId);
        return assetDataDao.selectFirst(condition);
    }
}