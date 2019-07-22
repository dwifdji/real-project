
package com.tzCloud.core.dao.assistant;

import com.tzCloud.core.condition.assistant.TConfigCondition;
import com.tzCloud.core.model.assistant.TConfig;
import com.tzCloud.arch.core.abs.BaseDao;

/**
 * <p>TConfig的基础操作Dao</p>
 * @ClassName: TConfigDao
 * @Description: TConfig基础操作的Dao
 * @author Generator
 * @date 2019年06月19日 15时49分48秒
 */
public interface TConfigDao extends BaseDao<TConfig,TConfigCondition>{
    TConfig findBy(String configType);

    String getValue(String configType);
}
