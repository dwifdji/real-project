
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStatusDescCondition;
import com.winback.core.model._case.TCaseStatusDesc;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TCaseStatusDesc的基础操作Dao</p>
 * @ClassName: TCaseStatusDescDao
 * @Description: TCaseStatusDesc基础操作的Dao
 * @author Generator
 * @date 2019年02月13日 18时26分53秒
 */
public interface TCaseStatusDescDao extends BaseDao<TCaseStatusDesc,TCaseStatusDescCondition>{
        TCaseStatusDesc getStatusDescByStatus(String caseStatus);
}
