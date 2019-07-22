
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStepBranchCondition;
import com.winback.core.model._case.TCaseStepBranch;
import com.winback.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>TCaseStepBranch的基础操作Dao</p>
 * @ClassName: TCaseStepBranchDao
 * @Description: TCaseStepBranch基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public interface TCaseStepBranchDao extends BaseDao<TCaseStepBranch,TCaseStepBranchCondition>{

    TCaseStepBranch getMaxCaseStepBranch(String stepId);

    void saveBatchCaseBranch(List<TCaseStepBranch> tCaseStepBranches);

    void deleteBatchCaseStepBranch(Integer id);
}
