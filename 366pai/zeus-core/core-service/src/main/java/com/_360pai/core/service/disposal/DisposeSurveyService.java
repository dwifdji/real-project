package com._360pai.core.service.disposal;

import com._360pai.core.model.disposal.TDisposeSurvey;
import com.github.pagehelper.PageInfo;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-24 16:52
 */
public interface DisposeSurveyService {
    PageInfo getDisposeSurveyByProviderId(Integer providerId, int pageNum, int pageSize);
    int updateSurveyById(TDisposeSurvey survey);
    TDisposeSurvey getDisposeSurveyById(Integer surveyId);
    int uploadProviderReport(Integer surveyId, String basisReport, String completeReport, Integer providerId);
    TDisposeSurvey getDisposeSurveyByAssetId(Integer assetId);
    int addDisposeSurvey(String surveyNo,Integer assetId,Integer providerId,Date assignTime,String assetName,String cityId);
    TDisposeSurvey getDisposeSurveyBySurveyNo(String surveyNo);
}
