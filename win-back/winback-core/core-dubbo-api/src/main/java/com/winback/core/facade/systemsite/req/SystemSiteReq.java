package com.winback.core.facade.systemsite.req;

import com.winback.arch.common.RequestModel;
import lombok.Getter;
import lombok.Setter;

public class SystemSiteReq {

    @Setter
    @Getter
    public static class SaveCaseSiteReq extends RequestModel{

        private String type;

        private String name;

        private String status;
    }

    @Setter
    @Getter
    public static class UpdateCaseSiteReq extends RequestModel{
        private String id;

        private String updateFlag;

        private String type;

        private String name;

        private String status;
    }

    @Setter
    @Getter
    public static class CaseStatusMsgSaveReq extends RequestModel{
        private String caseStatus;

        private String msgBody;

        private String status;

    }

    @Setter
    @Getter
    public static class CaseStatusMsgUpdateeReq extends RequestModel{
        private String id;

        private String updateFlag;

        private String caseStatus;

        private String msgBody;

        private String status;

        private String caseStatusDesc;
    }

    @Setter
    @Getter
    public static class CaseSiteListReq extends RequestModel {
        private String id;
        private String type;
    }

    @Setter
    @Getter
    public static class CaseStatusListReq extends RequestModel {
        private String id;
        private String type;
    }
}
