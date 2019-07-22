
package com._360pai.core.dao.asset;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.asset.TAssetFieldCondition;
import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.model.asset.TAssetField;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetField的基础操作Dao</p>
 * @ClassName: TAssetFieldDao
 * @Description: TAssetField基础操作的Dao
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
public interface TAssetFieldDao extends BaseDao<TAssetField, TAssetFieldCondition>{

    List<Map> searchAssetFields(TAssetFieldDto dto);

    TAssetField findUnit(String key);
}
