
package com._360pai.core.dao.fastway;

import com._360pai.core.condition.fastway.TFastwayDisposeApplyCondition;
import com._360pai.core.model.fastway.TFastwayDisposeApply;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TFastwayDisposeApply的基础操作Dao</p>
 * @ClassName: TFastwayDisposeApplyDao
 * @Description: TFastwayDisposeApply基础操作的Dao
 * @author Generator
 * @date 2018年11月26日 10时48分58秒
 */
public interface TFastwayDisposeApplyDao extends BaseDao<TFastwayDisposeApply,TFastwayDisposeApplyCondition>{

    List<TFastwayDisposeApply> findByParam(Map<String, Object> query);
}
