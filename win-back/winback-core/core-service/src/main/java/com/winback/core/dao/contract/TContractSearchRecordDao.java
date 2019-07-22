
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractSearchRecordCondition;
import com.winback.core.model.contract.TContractSearchRecord;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TContractSearchRecord的基础操作Dao</p>
 * @ClassName: TContractSearchRecordDao
 * @Description: TContractSearchRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractSearchRecordDao extends BaseDao<TContractSearchRecord,TContractSearchRecordCondition>{
    void saveSearchRecord(Integer accountId, String keyword);

    List<String> getKeywordList(Integer accountId);

    void clearSearchRecord(Integer accountId);
}
