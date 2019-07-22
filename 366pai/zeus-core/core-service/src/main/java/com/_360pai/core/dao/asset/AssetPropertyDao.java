
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.model.asset.AssetProperty;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetProperty的基础操作Dao</p>
 * @ClassName: AssetPropertyDao
 * @Description: AssetProperty基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetPropertyDao extends BaseDao<AssetProperty,AssetPropertyCondition>{

    Object getProperties();
}
