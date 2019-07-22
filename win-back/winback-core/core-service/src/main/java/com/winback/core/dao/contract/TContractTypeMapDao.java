
package com.winback.core.dao.contract;

import com.winback.core.condition.contract.TContractTypeMapCondition;
import com.winback.core.model.contract.TContractType;
import com.winback.core.model.contract.TContractTypeMap;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TContractTypeMap的基础操作Dao</p>
 * @ClassName: TContractTypeMapDao
 * @Description: TContractTypeMap基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TContractTypeMapDao extends BaseDao<TContractTypeMap,TContractTypeMapCondition>{
    String getFullName(Integer contractId);

    void saveContractTypeMap(Integer contractId, Integer contractTypeId);

    void syncContractTypeMap(Integer contractId, Integer contractTypeId);

    TContractType getContractType(Integer contractId);
}
