
package com.tzCloud.core.dao.caseMatching.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.caseMatching.CaseHtmlAnalysisCondition;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>CaseHtmlAnalysis数据层的基础操作</p>
 * @ClassName: CaseHtmlAnalysisMapper
 * @Description: CaseHtmlAnalysis数据层的基础操作
 * @author Generator
 * @date 2019年03月05日 09时26分19秒
 */
@Mapper
public interface CaseHtmlAnalysisMapper extends BaseMapper<CaseHtmlAnalysis, CaseHtmlAnalysisCondition> {
    int insertOrUpdateAnalysisList(List<CaseHtmlAnalysis> list);
    List<CaseHtmlData> findExist();
    List<CaseHtmlData> findByEarlyTime(@Param("updateTime") String updateTime);
    int insertOrUpdateAnalysis(CaseHtmlAnalysis analysis);

}
