package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-05-05 14:19
 */
@Data
public class LawFirmAnalysisVO implements Serializable {
    private static final long serialVersionUID = -1382697813640824032L;

    // 案由分布
    private Map<String, Integer> briefDistributed;
    // 判决胜诉率
    private Map<String, Integer> judgeResultCount;
    // 法院层级
    private Map<String, Integer> courtLevel;
    // 代理客户
    private Map<String, Integer> dsrxxStatistics;
    // 承办法官
    private Map<String, Integer> judgeStatistics;
    // 承办法院
    private Map<String, Integer> courtStatistics;

}
