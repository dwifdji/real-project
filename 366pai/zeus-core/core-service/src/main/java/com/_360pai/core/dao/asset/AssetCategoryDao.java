
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetCategoryCondition;
import com._360pai.core.model.asset.AssetCategory;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetCategory的基础操作Dao</p>
 * @ClassName: AssetCategoryDao
 * @Description: AssetCategory基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分13秒
 */
public interface AssetCategoryDao extends BaseDao<AssetCategory,AssetCategoryCondition>{

    Integer selectDefault(Integer id);
}
