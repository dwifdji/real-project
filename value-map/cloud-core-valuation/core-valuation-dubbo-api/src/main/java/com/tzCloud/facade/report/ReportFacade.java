package com.tzCloud.facade.report;

import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.facade.report.req.ReportReq;

public interface ReportFacade {

    ResponseModel saveReportApplicant(ReportReq.SaveReporttReq req);
}
