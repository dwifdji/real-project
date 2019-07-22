
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionItemCondition;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;

/**
 * <p>TAssetFieldFilterOptionItem的基础操作Dao</p>
 * @ClassName: TAssetFieldFilterOptionItemDao
 * @Description: TAssetFieldFilterOptionItem基础操作的Dao
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public interface TAssetFieldFilterOptionItemDao extends BaseDao<TAssetFieldFilterOptionItem, TAssetFieldFilterOptionItemCondition>{

    int deleteTAssetFieldFilterOptionItem(Integer id);
}
