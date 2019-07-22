
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.AssetCondition;
import com._360pai.core.model.asset.Asset;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>Asset数据层的基础操作</p>
 * @ClassName: AssetMapper
 * @Description: Asset数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface AssetMapper extends BaseMapper<Asset, AssetCondition>{
    List<Asset> getAssetList(Map<String, Object> params);

    List<Map> getAgencyCode(@Param("agencyId") Integer agencyId);

    List<Map> myAsset(Map<String, Object> params);

    String getCategoryName(@Param("assetId") Integer assetId);
}
