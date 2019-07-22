package com.tzCloud.core.facade.caseMatching.req;

import com.tzCloud.arch.common.RequestModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/2/22 14:10
 */
public class CaseMatchingReq {

    @Data
    public static class BriefSearch extends RequestModel {
        private java.lang.String searchStr;
    }

    @Data
    public static class CaseMatchingSearch extends RequestModel {
        private java.lang.String briefStr;
        /**
         * 保留字段，地域，目前都是上海
         */
        private java.lang.String locationStr;
    }

    @Data
    public static class LawFirmSearch extends RequestModel {
        /**
         * 案由类型
         */
        private java.lang.String briefStr;

        private java.lang.String courtName;
        /**
         * 保留字段，地域，目前都是上海
         */
        private java.lang.String locationStr;
    }

    @Data
    public static class LawyerSearch extends RequestModel {
        /**
         * 案由类型
         */
        private java.lang.String briefStr;

        private java.lang.String courtName;

        private java.lang.String lawFirmName;

        /**
         * 保留字段，地域，目前都是上海
         */
        private java.lang.String locationStr;
    }

    @Data
    public static class LawyerDetail extends RequestModel {
        private java.lang.String lawyerName;
        private java.lang.String lawFirmName;
    }

    @Data
    public static class CaseListByLawyer extends RequestModel {
        private java.lang.String lawyerName;
        private java.lang.String brief;
    }

    @Data
    public static class CaseDetail extends RequestModel {
        private java.lang.String docId;
    }
}
