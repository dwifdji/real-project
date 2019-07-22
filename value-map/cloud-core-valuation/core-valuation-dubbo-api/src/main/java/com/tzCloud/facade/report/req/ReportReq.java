package com.tzCloud.facade.report.req;

import com.tzCloud.arch.common.RequestModel;
import com.tzCloud.arch.common.RequestNotFilterModel;
import lombok.Getter;
import lombok.Setter;

public class ReportReq {
    @Setter
    @Getter
    public static class SaveReporttReq extends RequestNotFilterModel {
        private String applicantName;

        private String applicantPhone;

        private String remark;
    }

}
