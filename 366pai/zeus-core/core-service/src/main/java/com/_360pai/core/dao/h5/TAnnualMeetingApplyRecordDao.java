
package com._360pai.core.dao.h5;

import com._360pai.core.condition.h5.TAnnualMeetingApplyRecordCondition;
import com._360pai.core.model.h5.TAnnualMeetingApplyRecord;
import com._360pai.arch.core.abs.BaseDao;

/**
 * <p>TAnnualMeetingApplyRecord的基础操作Dao</p>
 * @ClassName: TAnnualMeetingApplyRecordDao
 * @Description: TAnnualMeetingApplyRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月08日 13时12分26秒
 */
public interface TAnnualMeetingApplyRecordDao extends BaseDao<TAnnualMeetingApplyRecord,TAnnualMeetingApplyRecordCondition>{
    boolean isAlreadyApply(String mobile);
}
