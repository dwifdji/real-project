package com._360pai.core.facade.disposal.req;

import com._360pai.arch.common.RequestModel;
import com._360pai.arch.common.ResponseModel;
import lombok.Getter;
import lombok.Setter;

/**
 * @author xiaolei
 * @create 2018-10-25 09:47
 */
public class DisposeSurveyReq {

    @Setter
    @Getter
    public static class GetSurveyList extends RequestModel {
        private Integer providerId;
        private Integer surveyId;
        private Integer assetId;
        private Integer activityId;
    }

    @Setter
    @Getter
    public static class UploadReport extends ResponseModel {
        private Integer surveyId;
        private String reportType;// 10: 基础尽调 20：完整尽调
        private String report;
        private String basisReport;// 基础报告
        private String completeReport;// 完整报告
        private Integer providerId;
    }
}
