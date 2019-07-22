
package com.winback.core.dao.contract;

import com.winback.arch.core.abs.BaseDao;
import com.winback.core.condition.contract.TAccountContractMapCondition;
import com.winback.core.model.contract.TAccountContractMap;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

/**
 * <p>TAccountContractMap的基础操作Dao</p>
 * @ClassName: TAccountContractMapDao
 * @Description: TAccountContractMap基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 13时38分36秒
 */
public interface TAccountContractMapDao extends BaseDao<TAccountContractMap,TAccountContractMapCondition>{
    TAccountContractMap findBy(Integer accountId, Integer contractId);

    boolean hasBuy(Integer accountId, Integer contractId);

    List<Integer> getContractIdList(Integer accountId);
}
