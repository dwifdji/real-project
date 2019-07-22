
package com._360pai.core.dao.order;

import com._360pai.core.condition.order.TServiceBusinessRecordCondition;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TServiceBusinessRecord的基础操作Dao</p>
 * @ClassName: TServiceBusinessRecordDao
 * @Description: TServiceBusinessRecord基础操作的Dao
 * @author Generator
 * @date 2018年10月08日 19时37分27秒
 */
public interface TServiceBusinessRecordDao extends BaseDao<TServiceBusinessRecord,TServiceBusinessRecordCondition>{

    List<TServiceBusinessRecord> getBusinessRecordByReportType(TServiceBusinessRecordCondition condition);

}
