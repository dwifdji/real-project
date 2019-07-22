
package com.winback.core.dao._case.mapper;

import com.winback.core.dto._case.CaseStatusStepDto;
import com.winback.core.vo._case.*;
import com.winback.core.vo.operate.CaseStepVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition._case.TCaseStepCondition;
import com.winback.core.model._case.TCaseStep;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TCaseStep数据层的基础操作</p>
 * @ClassName: TCaseStepMapper
 * @Description: TCaseStep数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
@Mapper
public interface TCaseStepMapper extends BaseMapper<TCaseStep, TCaseStepCondition>{

    List<TCaseStepGroupVO> getCaseSteps(@Param("type")String type);

    TCaseStep getOrderDescByTypeGroup(@Param("groupId")String groupId);

    void updateOrderDescByGroupId(@Param("groupId")Integer groupId, @Param("orderDesc")Integer orderDesc);

    TCaseStepDetailVO getCaseStepById(@Param("id") String id);

    List<TCaseStepSelectVO> getCaseStepNotBranch(@Param("type") String type);

    List<TCaseCurrentStepVO> getCurrentSteps(@Param("caseId") String caseId, @Param("type") String type);

    List<TCaseStepMsgTemplateVO> getAllStepMsg(@Param("type") String type);

    List<TCaseStatusStepVO> getLawsuitManagements(CaseStatusStepDto params);

    List<CaseStepVO> getAllCaseStep(@Param("type")String type);

    String getApplyPerson(@Param("caseId")String caseId);

    void saveCaseBranchTypeList(List<TCaseStep> tCaseStepBranches);

    void deleteBatchCaseStepBranch(@Param("stepId")Integer stepId);

    TCaseStep getFirstStep(@Param("type")String type);

    TCaseStep getLastLitigationStep(@Param("type")String type, @Param("caseId")String caseId);

    List<TCaseStep> getAllSmaSteps(@Param("type")String type);

    void batchUpdateCaseStep(ArrayList<TCaseStep> newCaseSteps);
}
