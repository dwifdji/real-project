
package com.winback.core.dao._case.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseStepBranchCondition;
import com.winback.core.model._case.TCaseStepBranch;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TCaseStepBranch数据层的基础操作</p>
 * @ClassName: TCaseStepBranchMapper
 * @Description: TCaseStepBranch数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
@Mapper
public interface TCaseStepBranchMapper extends BaseMapper<TCaseStepBranch, TCaseStepBranchCondition>{

    TCaseStepBranch getMaxCaseStepBranch(@Param("stepId") String stepId);

    void saveBatchCaseBranch(List<TCaseStepBranch> tCaseStepBranches);

    void deleteBatchCaseStepBranch(@Param("stepId")Integer stepId);
}
