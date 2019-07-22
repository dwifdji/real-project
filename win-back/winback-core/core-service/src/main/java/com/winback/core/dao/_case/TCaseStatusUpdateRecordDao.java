
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStatusUpdateRecordCondition;
import com.winback.core.model._case.TCaseStatusUpdateRecord;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TCaseStatusUpdateRecord的基础操作Dao</p>
 * @ClassName: TCaseStatusUpdateRecordDao
 * @Description: TCaseStatusUpdateRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 15时29分52秒
 */
public interface TCaseStatusUpdateRecordDao extends BaseDao<TCaseStatusUpdateRecord,TCaseStatusUpdateRecordCondition>{

    List<TCaseStatusUpdateRecord> getPrecheckRecord(String caseId);
    List<TCaseStatusUpdateRecord> getRiskcheckRecord(String caseId);
    List<TCaseStatusUpdateRecord> getSignContractRecord(String caseId);
    List<TCaseStatusUpdateRecord> getStepRecordListByCaseId(String caseId,String recordType);
}
