
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionCondition;
import com._360pai.core.model.asset.TAssetFieldFilterOption;

/**
 * <p>TAssetFieldFilterOption的基础操作Dao</p>
 * @ClassName: TAssetFieldFilterOptionDao
 * @Description: TAssetFieldFilterOption基础操作的Dao
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public interface TAssetFieldFilterOptionDao extends BaseDao<TAssetFieldFilterOption, TAssetFieldFilterOptionCondition>{

    int deleteAssetTemplateFieldOption(Integer id);
}
