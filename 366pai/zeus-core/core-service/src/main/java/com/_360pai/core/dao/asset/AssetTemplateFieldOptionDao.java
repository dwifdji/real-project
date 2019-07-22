
package com._360pai.core.dao.asset;

import com._360pai.core.condition.asset.AssetTemplateFieldOptionCondition;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>AssetTemplateFieldOption的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateFieldOptionDao
 * @Description: AssetTemplateFieldOption基础操作的Dao
 * @date 2018年08月10日 17时37分14秒
 */
public interface AssetTemplateFieldOptionDao extends BaseDao<AssetTemplateFieldOption, AssetTemplateFieldOptionCondition> {
    List<Integer> selectIdsForFieldId(Integer fieldId);

    int deleteAssetTemplateFieldOption(Integer id);
}
