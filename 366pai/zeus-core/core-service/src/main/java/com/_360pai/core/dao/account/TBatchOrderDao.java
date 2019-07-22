
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TBatchOrderCondition;
import com._360pai.core.model.account.TBatchOrder;

import java.util.List;

/**
 * <p>TBatchOrder的基础操作Dao</p>
 * @ClassName: TBatchOrderDao
 * @Description: TBatchOrder基础操作的Dao
 * @author Generator
 * @date 2018年11月29日 15时57分11秒
 */
public interface TBatchOrderDao extends BaseDao<TBatchOrder,TBatchOrderCondition>{
    TBatchOrder selectMaxId();

    List<TBatchOrder> searchBatchOrder(Long batchOrderNo, String status, String beginTime, String endTime);

}
