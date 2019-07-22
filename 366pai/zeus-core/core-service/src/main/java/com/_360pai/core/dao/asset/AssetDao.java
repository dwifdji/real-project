
package com._360pai.core.dao.asset;

import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.asset.AssetCondition;
import com._360pai.core.model.asset.Asset;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>Asset的基础操作Dao</p>
 * @ClassName: AssetDao
 * @Description: Asset基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface AssetDao extends BaseDao<Asset,AssetCondition>{
    PageInfo getAssetList(int page, int perPage, Map<String, Object> params, String orderBy);

    List<Map> getAgencyCode(Integer agencyId);

    int updateStatus(Integer assetId, AssetEnum.Status status);

    List<Map> myAsset(Map<String, Object> params);

    String getCategoryName(Integer assetId);

    PageInfo getListByPage(int page, int perPage);
}
