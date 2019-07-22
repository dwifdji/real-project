
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.TComCityCondition;
import com.winback.core.model.assistant.TComCity;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TComCity的基础操作Dao</p>
 * @ClassName: TComCityDao
 * @Description: TComCity基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分03秒
 */
public interface TComCityDao extends BaseDao<TComCity,TComCityCondition>{
    String getName(String code);

    TComCity findBy(String code);
}
