
package com._360pai.core.dao.order;

import com._360pai.core.condition.order.TServiceAdjustedRecordCondition;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TServiceAdjustedRecord的基础操作Dao</p>
 * @ClassName: TServiceAdjustedRecordDao
 * @Description: TServiceAdjustedRecord基础操作的Dao
 * @author Generator
 * @date 2018年09月30日 11时19分10秒
 */
public interface TServiceAdjustedRecordDao extends BaseDao<TServiceAdjustedRecord,TServiceAdjustedRecordCondition>{

    List<TServiceAdjustedRecord> selectAdjustedRecordByIds(Integer[] adjustedIds);
}
