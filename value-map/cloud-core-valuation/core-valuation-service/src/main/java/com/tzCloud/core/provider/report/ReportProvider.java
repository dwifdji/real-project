package com.tzCloud.core.provider.report;

import com.alibaba.dubbo.config.annotation.Service;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.core.service.report.ReportService;
import com.tzCloud.facade.report.ReportFacade;
import com.tzCloud.facade.report.req.ReportReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(version = "1.0.0")
public class ReportProvider implements ReportFacade {

    @Autowired
    private ReportService reportService;

    @Override
    public ResponseModel saveReportApplicant(ReportReq.SaveReporttReq req) {
        Integer count = reportService.saveReportApplicant(req);
        if(count == 0) {
            return ResponseModel.fail();
        }

        return ResponseModel.succ();
    }
}
