
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.TAssetTemplateFieldGroupMapCondition;
import com._360pai.core.model.asset.TAssetTemplateFieldGroupMap;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetTemplateFieldGroupMap的基础操作Dao</p>
 * @ClassName: TAssetTemplateFieldGroupMapDao
 * @Description: TAssetTemplateFieldGroupMap基础操作的Dao
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public interface TAssetTemplateFieldGroupMapDao extends BaseDao<TAssetTemplateFieldGroupMap, TAssetTemplateFieldGroupMapCondition>{

    List<Map> getTemplateGroup(Integer paramsId);
}
