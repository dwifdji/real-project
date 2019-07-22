
package com.winback.core.dao.account;

import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.account.TFranchiseeApplyRecordCondition;
import com.winback.core.model.account.TFranchiseeApplyRecord;

import java.util.Map;

/**
 * <p>TFranchiseeApplyRecord的基础操作Dao</p>
 * @ClassName: TFranchiseeApplyRecordDao
 * @Description: TFranchiseeApplyRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分02秒
 */
public interface TFranchiseeApplyRecordDao extends BaseDao<TFranchiseeApplyRecord,TFranchiseeApplyRecordCondition>{
    boolean hasPendingApply(Integer accountId);

    TFranchiseeApplyRecord findLatestByAccountId(Integer accountId);

    PageInfo<TFranchiseeApplyRecord> getList(int page, int perPage, Map<String, Object> params, String orderBy);
}
