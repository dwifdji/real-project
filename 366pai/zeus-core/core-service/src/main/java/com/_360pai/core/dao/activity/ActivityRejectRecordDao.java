
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.ActivityRejectRecordCondition;
import com._360pai.core.model.activity.ActivityRejectRecord;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>ActivityRejectRecord的基础操作Dao</p>
 * @ClassName: ActivityRejectRecordDao
 * @Description: ActivityRejectRecord基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface ActivityRejectRecordDao extends BaseDao<ActivityRejectRecord,ActivityRejectRecordCondition>{
    int save(Integer activityId, String reason);
}
