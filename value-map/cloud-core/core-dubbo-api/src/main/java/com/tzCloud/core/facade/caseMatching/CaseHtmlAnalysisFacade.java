package com.tzCloud.core.facade.caseMatching;

import com.tzCloud.arch.common.PageInfoResp;

/**
 * @author xiaolei
 * @create 2019-03-26 09:50
 */
public interface CaseHtmlAnalysisFacade
{

    /**
     * case_html_analysis 表增量更新数据
     */
    PageInfoResp analysisIncrement(int pageNum, int pageSize);

    /**
     * case_html_analysis 表增量更新数据
     */
    PageInfoResp analysisFull(int pageNum, int pageSize);

}
