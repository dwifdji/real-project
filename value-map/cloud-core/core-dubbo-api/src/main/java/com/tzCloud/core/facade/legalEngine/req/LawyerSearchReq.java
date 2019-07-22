package com.tzCloud.core.facade.legalEngine.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;

/**
 * @author xiaolei
 * @create 2019-04-23 13:04
 */
public class LawyerSearchReq {

    @Data
    public static class SearchList extends RequestModel
    {
        private static final long serialVersionUID = 2243917513666036833L;
        /* default_asc：综合升序，default_desc：综合降序，
           years_asc：执业年限升序，years_desc：职业年限降序，
           caseCount_asc：案例数升序，caseCount_desc：案例数降序 */
        private String orderBy;
        private LawyerSearchCondition conditions;
        private Integer accountId;
    }

    @Data
    public static class LawFirmAnalysis extends RequestModel
    {
        private static final long serialVersionUID = -6506541341855905954L;
        private String lawFirm;
        private Integer accountId;
    }

    @Data
    public static class SearchLawFirmList extends RequestModel
    {
        private static final long serialVersionUID = -3066350612963502178L;
        private String orderBy;
        private LawFirmSearchCondition conditions;
        private Integer accountId;
    }

    @Data
    public static class SearchLawyerCaseList extends RequestModel
    {
        private static final long serialVersionUID = -5837064356195172031L;
        private String lawyerId;
        private String brief;
    }
}
