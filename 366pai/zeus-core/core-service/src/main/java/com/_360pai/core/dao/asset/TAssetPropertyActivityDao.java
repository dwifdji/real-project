
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.TAssetPropertyActivityCondition;
import com._360pai.core.model.asset.TAssetPropertyActivity;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetPropertyActivity的基础操作Dao</p>
 * @ClassName: TAssetPropertyActivityDao
 * @Description: TAssetPropertyActivity基础操作的Dao
 * @author Generator
 * @date 2018年09月18日 20时18分03秒
 */
public interface TAssetPropertyActivityDao extends BaseDao<TAssetPropertyActivity,TAssetPropertyActivityCondition>{

    List<Map> propertySearch(Integer assetPropertyId);
}
