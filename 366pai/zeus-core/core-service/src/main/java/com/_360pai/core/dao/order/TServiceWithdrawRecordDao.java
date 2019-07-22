
package com._360pai.core.dao.order;

import com._360pai.core.condition.order.TServiceWithdrawRecordCondition;
import com._360pai.core.model.order.TServiceWithdrawRecord;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * <p>TServiceWithdrawRecord的基础操作Dao</p>
 * @ClassName: TServiceWithdrawRecordDao
 * @Description: TServiceWithdrawRecord基础操作的Dao
 * @author Generator
 * @date 2018年10月07日 14时12分38秒
 */
public interface TServiceWithdrawRecordDao extends BaseDao<TServiceWithdrawRecord,TServiceWithdrawRecordCondition>{

    List<TServiceWithdrawRecord> selectWithdrawRecordByParam(Map<String, Object> queryParam);
    List<TServiceWithdrawRecord> selectAdminWithdrawRecordByParam(Map<String, Object> queryParam);
}
