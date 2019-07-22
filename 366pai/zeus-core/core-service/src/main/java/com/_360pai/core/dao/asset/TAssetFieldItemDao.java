
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.TAssetFieldItemCondition;
import com._360pai.core.model.asset.TAssetFieldItem;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetFieldItem的基础操作Dao</p>
 * @ClassName: TAssetFieldItemDao
 * @Description: TAssetFieldItem基础操作的Dao
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public interface TAssetFieldItemDao extends BaseDao<TAssetFieldItem, TAssetFieldItemCondition>{

    List<Map> findFields(Integer templateCategoryId, Integer assetFieldGroupId, Integer filterOptionId, Integer filterOptionItemId,Integer filterOptionItemDataId);

    int deleteTemplateGroupField(Integer id);

    List<Map> findFieldsNotHaveOption(Integer templateCategoryId, int fieldGroupId);

    List<Map> findDisplayField(Integer templateCategoryId, int fieldGroupId);
}
