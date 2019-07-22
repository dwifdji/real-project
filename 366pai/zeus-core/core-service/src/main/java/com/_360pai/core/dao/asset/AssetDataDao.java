
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetDataCondition;
import com._360pai.core.model.asset.AssetData;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

/**
 * <p>AssetData的基础操作Dao</p>
 * @ClassName: AssetDataDao
 * @Description: AssetData基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetDataDao extends BaseDao<AssetData,AssetDataCondition>{

    AssetData findAssetData(Integer assetId);

    PageInfo<AssetData> find(int page, int perPage);
}
