
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TAppletContractSearchRecordCondition;
import com.winback.core.model.contract.TAppletContractSearchRecord;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TAppletContractSearchRecord的基础操作Dao</p>
 * @ClassName: TAppletContractSearchRecordDao
 * @Description: TAppletContractSearchRecord基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分37秒
 */
public interface TAppletContractSearchRecordDao extends BaseDao<TAppletContractSearchRecord,TAppletContractSearchRecordCondition>{

    void saveSearchRecord(Integer extBindId, String keyword);

    List<String> getKeywordList(Integer extBindId);

    void clearSearchRecord(Integer extBindId);

    void deleteSearchRecord(Integer extBindId, String keyword);
}
