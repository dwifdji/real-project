
package com._360pai.core.dao.account;

import com._360pai.arch.core.abs.BaseDao;
import com._360pai.core.condition.account.TAccountViewRecordCondition;
import com._360pai.core.facade.account.req.AcctReq;
import com._360pai.core.model.account.TAccountViewRecord;

/**
 * <p>TAccountViewRecord的基础操作Dao</p>
 * @ClassName: TAccountViewRecordDao
 * @Description: TAccountViewRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月14日 09时48分08秒
 */
public interface TAccountViewRecordDao extends BaseDao<TAccountViewRecord, TAccountViewRecordCondition>{

    void updateActivityByPartyId(AcctReq.ViewRecordRequest viewRecordRequest);
}
