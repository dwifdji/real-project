package com.tzCloud.valuation.controller.report;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tzCloud.arch.common.ResponseModel;
import com.tzCloud.arch.common.enums.ApiCallResult;
import com.tzCloud.facade.house.req.HouseReq;
import com.tzCloud.facade.report.ReportFacade;
import com.tzCloud.facade.report.req.ReportReq;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/open/valuationMap/report")
public class ReportController {

    @Reference(version = "1.0.0")
    private ReportFacade reportFacade;

    @PostMapping("/saveReportApplicant")
    public ResponseModel saveReportApplicant(@RequestBody ReportReq.SaveReporttReq req) {
        if(StringUtils.isBlank(req.getApplicantName()) || StringUtils.isBlank(req.getApplicantPhone())) {
            return ResponseModel.fail(ApiCallResult.EMPTY.getDesc());
        }

        return reportFacade.saveReportApplicant(req);
    }
}
