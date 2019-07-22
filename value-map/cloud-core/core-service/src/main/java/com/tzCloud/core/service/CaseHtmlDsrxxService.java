package com.tzCloud.core.service;

import com.tzCloud.arch.common.PageInfoResp;

/**
 * @author xiaolei
 * @create 2019-03-08 15:01
 */
public interface CaseHtmlDsrxxService {
    /**
     * case_html_dsrxx 表全量更新数据
     */
    void parseDsrxxFull();

    /**
     * case_html_dsrxx 表增量量更新数据
     */
    void parseDsrxxIncrement();

    /**
     * case_html_dsrxx 表增量量更新数据
     */
    PageInfoResp parseDsrxxIncrement(int pageNum, int pageSize);

    /**
     * 从dsrxx_parse_status 查出未更新的docId 进行解析
     */
    void parseDsrxxIncrementNew();

    /**
     * 从dsrxx_parse_status 查出未更新的docId 进行解析
     */
    void parseDsrxxIncrementThread();

    /**
     * 更新 case_html_dsrxx 表问题数据
     */
    void parseDsrxxUpdate();
}
