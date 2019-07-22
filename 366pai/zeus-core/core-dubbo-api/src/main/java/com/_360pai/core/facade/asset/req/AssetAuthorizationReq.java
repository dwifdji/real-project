package com._360pai.core.facade.asset.req;

import com._360pai.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author xiaolei
 * @create 2018-11-05 16:50
 */
public class AssetAuthorizationReq {

    @Getter
    @Setter
    public static class PreSignTuneAuthProtocol extends RequestModel {
        @NotNull
        private Integer activityId;
        private Integer partyId;
        @NotEmpty
        private String  reportSource;
        private String  surveyReport;
        private String  surveyReportPrice;

    }

    @Getter
    @Setter
    public static class SignTuneAuthProtocol extends RequestModel {
        private Integer activityId;
        private Integer partyId;
        private String  protocol;
        private String  protocolViewUrl;
        private String  protocolDownloadUrl;
        private String  protocolType;
        private String[]  surveyReports;
        private String  surveyReportPrice;
        private String  reportSource;
    }
}
