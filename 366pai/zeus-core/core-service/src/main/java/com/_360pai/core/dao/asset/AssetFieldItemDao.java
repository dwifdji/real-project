
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetFieldItemCondition;
import com._360pai.core.model.asset.AssetFieldItem;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetFieldItem的基础操作Dao</p>
 * @ClassName: AssetFieldItemDao
 * @Description: AssetFieldItem基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetFieldItemDao extends BaseDao<AssetFieldItem,AssetFieldItemCondition>{

    int deleteItem(Integer id);
}
