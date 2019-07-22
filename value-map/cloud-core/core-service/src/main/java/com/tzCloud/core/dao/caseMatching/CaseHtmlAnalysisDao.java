
package com.tzCloud.core.dao.caseMatching;

import com.tzCloud.arch.core.abs.BaseDao;
import com.tzCloud.core.condition.caseMatching.CaseHtmlAnalysisCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;

import java.util.List;

/**
 * <p>CaseHtmlAnalysis的基础操作Dao</p>
 * @ClassName: CaseHtmlAnalysisDao
 * @Description: CaseHtmlAnalysis基础操作的Dao
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
public interface CaseHtmlAnalysisDao extends BaseDao<CaseHtmlAnalysis, CaseHtmlAnalysisCondition> {
    int insertOrUpdateAnalysisList(List<CaseHtmlAnalysis> list);
    List<CaseHtmlData> findExist();
    List<CaseHtmlData> findByEarlyTime(String updateTime);
    int insertOrUpdateAnalysis(CaseHtmlAnalysis analysis);
}
