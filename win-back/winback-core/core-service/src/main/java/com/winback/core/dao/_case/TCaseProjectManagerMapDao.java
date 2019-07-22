
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseProjectManagerMapCondition;
import com.winback.core.model._case.TCaseProjectManagerMap;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TCaseProjectManagerMap的基础操作Dao</p>
 * @ClassName: TCaseProjectManagerMapDao
 * @Description: TCaseProjectManagerMap基础操作的Dao
 * @author Generator
 * @date 2019年05月06日 15时34分52秒
 */
public interface TCaseProjectManagerMapDao extends BaseDao<TCaseProjectManagerMap,TCaseProjectManagerMapCondition>{
    int countCaseNum(Integer accountId);

    int countSuccessCaseNum(Integer accountId);

    TCaseProjectManagerMap findBy(String caseId);

    List<TCaseProjectManagerMap> findBy(Integer accountId);

    boolean isCaseAllocatedToThisAccount(String caseId, Integer accountId);
}
