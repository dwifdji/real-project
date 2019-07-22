
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetTemplateOptionsResultCondition;
import com._360pai.core.model.asset.AssetTemplateOptionsResult;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetTemplateOptionsResult的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateOptionsResultDao
 * @Description: AssetTemplateOptionsResult基础操作的Dao
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetTemplateOptionsResultDao extends BaseDao<AssetTemplateOptionsResult, AssetTemplateOptionsResultCondition> {
    int deleteForGroupId(Integer groupId);
}
