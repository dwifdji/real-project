package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.disposal.req.DisposeSurveyReq;

import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-10-25 09:41
 */
public interface DisposeSurveyFacade {

    /**
     * 根据 providerId 获取我的尽调接单
     * @param req
     * @return
     */
    PageInfoResp getSurveyByProviderId(DisposeSurveyReq.GetSurveyList req);

    /**
     * 接受尽调接单
     * @param surveyId
     * @return
     */
    int accessSurvey(Integer surveyId, Integer providerId);

    /**
     * 服务商上传尽调报告
     * @param req
     * @return
     */
    int uploadProviderReport(DisposeSurveyReq.UploadReport req);

    /**
     * 获取尽调模板
     * @return
     */
    Map<String,Object> getReportTemplate();

}
