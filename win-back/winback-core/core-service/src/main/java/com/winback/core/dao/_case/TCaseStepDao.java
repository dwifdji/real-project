
package com.winback.core.dao._case;

import com.winback.core.condition._case.TCaseStepCondition;
import com.winback.core.dto._case.CaseStatusStepDto;
import com.winback.core.facade._case.req.CaseStepReq;
import com.winback.core.model._case.TCaseStep;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo._case.*;
import com.winback.core.vo.operate.CaseStepVO;
import com.winback.core.vo.operate.QuickReleaseVO;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>TCaseStep的基础操作Dao</p>
 * @ClassName: TCaseStepDao
 * @Description: TCaseStep基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 16时13分05秒
 */
public interface TCaseStepDao extends BaseDao<TCaseStep,TCaseStepCondition>{

    List<TCaseStepGroupVO> getCaseSteps(String type);

    TCaseStep getOrderDescByTypeGroup(String groupId);

    void updateOrderDescByGroupId(Integer groupId, Integer orderDesc);

    TCaseStepDetailVO getCaseStepById(String id);

    List<TCaseStepSelectVO> getCaseStepNotBranch(String type);

    List<TCaseCurrentStepVO> getCurrentSteps(String caseId, String type);

    List<TCaseStepMsgTemplateVO> getAllStepMsg(String type);

    List<TCaseStatusStepVO> getLawsuitManagements(CaseStatusStepDto params);

    List<CaseStepVO> getAllCaseStep(String type);

    String getApplyPerson(String caseId);

    void saveCaseBranchTypeList(List<TCaseStep> tCaseStepBranches);

    void deleteBatchCaseStepBranch(Integer id);

    TCaseStep getFirstStep(String type);

    TCaseStep getLastLitigationStep(String s, String caseId);

    List<TCaseStep> getAllSmaSteps(String type);

    void batchUpdateCaseStep(ArrayList<TCaseStep> newCaseSteps);
}
