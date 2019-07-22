
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.TJobActivityViewCountRecodeCondition;
import com._360pai.core.model.assistant.TJobActivityViewCountRecode;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TJobActivityViewCountRecode的基础操作Dao</p>
 *
 * @author Generator
 * @ClassName: TJobActivityViewCountRecodeDao
 * @Description: TJobActivityViewCountRecode基础操作的Dao
 * @date 2018年12月04日 13时50分38秒
 */
public interface TJobActivityViewCountRecodeDao extends BaseDao<TJobActivityViewCountRecode, TJobActivityViewCountRecodeCondition> {

    int getTotalActivityViewCount(Integer activityType, Integer activityId);
}
