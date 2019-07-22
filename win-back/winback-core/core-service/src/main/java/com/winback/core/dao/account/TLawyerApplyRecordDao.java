
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.core.condition.account.TLawyerApplyRecordCondition;
import com.winback.core.model.account.TAccount;
import com.winback.core.model.account.TLawyerApplyRecord;
import com.winback.arch.core.abs.BaseDao;

import java.util.Map;

/**
 * <p>TLawyerApplyRecord的基础操作Dao</p>
 * @ClassName: TLawyerApplyRecordDao
 * @Description: TLawyerApplyRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
public interface TLawyerApplyRecordDao extends BaseDao<TLawyerApplyRecord,TLawyerApplyRecordCondition>{
    boolean hasPendingApply(Integer accountId);

    TLawyerApplyRecord findLatestByAccountId(Integer accountId);

    PageInfo<TLawyerApplyRecord> getList(int page, int perPage, Map<String, Object> params, String orderBy);
}
