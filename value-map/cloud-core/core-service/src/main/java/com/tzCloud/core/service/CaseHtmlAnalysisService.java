package com.tzCloud.core.service;

import com.hankcs.hanlp.model.perceptron.PerceptronNERecognizer;
import com.hankcs.hanlp.seg.Segment;
import com.tzCloud.arch.common.PageInfoResp;
import com.tzCloud.core.model.caseMatching.CaseHtmlAnalysis;
import com.tzCloud.core.model.caseMatching.CaseHtmlData;

/**
 * @author xiaolei
 * @create 2019-03-04 19:22
 */
public interface CaseHtmlAnalysisService {
    /**
     * case_html_analysis 表全量更新数据
     */
    void analysisFull();

    /**
     * case_html_analysis 表增量更新数据
     */
    void analysisIncrement();

    /**
     * case_html_analysis 表全量更新数据
     */
    PageInfoResp analysisFull(int pageNum, int pageSize);

    /**
     * case_html_analysis 表增量更新数据
     */
    PageInfoResp analysisIncrement(int pageNum, int pageSize);

    CaseHtmlAnalysis analysis(CaseHtmlData object, PerceptronNERecognizer recognizer, Segment segment);
}
