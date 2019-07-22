
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractDownloadRecordCondition;
import com.winback.core.model.contract.TContractDownloadRecord;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TContractDownloadRecord的基础操作Dao</p>
 * @ClassName: TContractDownloadRecordDao
 * @Description: TContractDownloadRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractDownloadRecordDao extends BaseDao<TContractDownloadRecord,TContractDownloadRecordCondition>{
    String getLatestEmail(Integer accountId);

    void saveDownloadRecord(Integer accountId, Integer contractId);

    void saveDownloadRecord(Integer accountId, String email, List<Integer> contractIds);

    void saveDownloadRecord(Integer accountId, String email, Integer contractId);
}
