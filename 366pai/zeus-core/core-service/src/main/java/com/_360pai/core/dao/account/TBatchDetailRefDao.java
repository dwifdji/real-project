
package com._360pai.core.dao.account;

import com._360pai.core.condition.account.TBatchDetailRefCondition;
import com._360pai.core.model.account.TBatchDetailRef;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TBatchDetailRef的基础操作Dao</p>
 * @ClassName: TBatchDetailRefDao
 * @Description: TBatchDetailRef基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
public interface TBatchDetailRefDao extends BaseDao<TBatchDetailRef,TBatchDetailRefCondition>{
    void deleteByBatchId(Long batchId);
}
