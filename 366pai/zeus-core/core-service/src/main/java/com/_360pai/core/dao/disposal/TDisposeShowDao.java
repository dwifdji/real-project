
package com._360pai.core.dao.disposal;

import com._360pai.core.condition.disposal.TDisposeShowCondition;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TDisposeShow的基础操作Dao</p>
 * @ClassName: TDisposeShowDao
 * @Description: TDisposeShow基础操作的Dao
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
public interface TDisposeShowDao extends BaseDao<TDisposeShow,TDisposeShowCondition>{
    List<TDisposeProvider> getShowProvider(Integer activityId);
    List<TDisposeProvider> getFirstLevelShowProvider(Integer cityId, Integer activityId);
}
