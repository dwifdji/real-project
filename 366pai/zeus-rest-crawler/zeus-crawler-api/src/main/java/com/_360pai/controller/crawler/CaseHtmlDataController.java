package com._360pai.controller.crawler;

import com._360pai.seimi.service.Impl.CaseHtmlDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaolei
 * @create 2019-01-29 14:07
 */
@RestController
@Slf4j
public class CaseHtmlDataController {
    @Autowired
    private CaseHtmlDataService caseHtmlDataService;

    @RequestMapping(value = "/run/caseHtmlData/saveByCaseType")
    public String findCaseHtmlDataByCaseType(int size) {
        caseHtmlDataService.findCaseHtmlDataByCaseType(size);
        return "OK";
    }

    @RequestMapping(value = "/run/caseHtmlData/saveByTrailRound")
    public String findCaseHtmlDataByTrailRound(String trailRound, int size) {
        caseHtmlDataService.findCaseHtmlDataByTrailRound(trailRound, size);
        return "OK";
    }
}
