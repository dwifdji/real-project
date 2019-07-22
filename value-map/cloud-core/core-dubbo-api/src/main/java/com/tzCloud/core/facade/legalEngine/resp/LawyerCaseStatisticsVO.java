package com.tzCloud.core.facade.legalEngine.resp;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2019-06-20 13:12
 */
@Data
public class LawyerCaseStatisticsVO implements Serializable {
    private static final long serialVersionUID = 3960098477063163047L;

    // 法院统计
    private List<Map<String, Object>> courtList;
    // 案由统计
    private List<Map<String, Object>> caseTypeList;
    // 诉讼地域统计
    private List<Map<String, Object>> locationList;
    // 结案数量统计
    private List<Map<String, Object>> caseCountByMonthList;
}
