
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.TownCondition;
import com._360pai.core.model.assistant.Town;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>Town的基础操作Dao</p>
 * @ClassName: TownDao
 * @Description: Town基础操作的Dao
 * @
 * @date 2019年04月25日 14时53分26秒
 */
public interface TownDao extends BaseDao<Town,TownCondition>{

    String getAreaDetail(String townId);

    String getAreaDetailByTownId(String townId);

    String getAreaDetailByAreaId(String areaId);
}