package com.tzCloud.core.facade.caseMatching;

import com.tzCloud.arch.common.PageInfoResp;

/**
 * @author xiaolei
 * @create 2019-03-26 10:59
 */
public interface CaseHtmlDsrxxFacade
{
    /**
     * case_html_dsrxx 表增量量更新数据
     */
    PageInfoResp parseDsrxxIncrement(int pageNum, int pageSize);
}
