
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetTemplateFieldMappingCondition;
import com._360pai.core.model.asset.AssetTemplateFieldMapping;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>AssetTemplateFieldMapping的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateFieldMappingDao
 * @Description: AssetTemplateFieldMapping基础操作的Dao
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetTemplateFieldMappingDao extends BaseDao<AssetTemplateFieldMapping, AssetTemplateFieldMappingCondition> {
    Boolean isInMapping(Integer groupId, Integer fieldId);

    int deleteMapping(Integer groupId);
}
