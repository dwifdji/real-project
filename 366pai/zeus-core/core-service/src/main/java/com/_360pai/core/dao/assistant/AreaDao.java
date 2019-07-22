
package com._360pai.core.dao.assistant;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.assistant.AreaCondition;
import com._360pai.core.model.assistant.Area;

/**
 * <p>Area的基础操作Dao</p>
 * @ClassName: AreaDao
 * @Description: Area基础操作的Dao
 * @author Generator
 * @date 2019年03月28日 11时25分12秒
 */
public interface AreaDao extends BaseDao<Area,AreaCondition>{

    Area selectById(Integer id);

    String getName(String id);

    String getName(Integer id);
}
