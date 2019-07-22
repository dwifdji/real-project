
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.WorkingDayCondition;
import com._360pai.core.model.assistant.WorkingDay;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>WorkingDay的基础操作Dao</p>
 * @ClassName: WorkingDayDao
 * @Description: WorkingDay基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分18秒
 */
public interface WorkingDayDao extends BaseDao<WorkingDay,WorkingDayCondition>{

    int deleteWorkingDay(Integer paramsId);
}
