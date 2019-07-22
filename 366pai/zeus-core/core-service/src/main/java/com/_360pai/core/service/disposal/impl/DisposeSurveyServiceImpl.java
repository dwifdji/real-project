package com._360pai.core.service.disposal.impl;

import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.disposal.TDisposeSurveyCondition;
import com._360pai.core.dao.disposal.TDisposeSurveyDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author xiaolei
 * @create 2018-10-24 16:55
 */
@Service
public class DisposeSurveyServiceImpl implements DisposeSurveyService {

    @Autowired
    private TDisposeSurveyDao disposeSurveyDao;

    @Override
    public PageInfo getDisposeSurveyByProviderId(Integer providerId, int pageNum, int pageSize) {
        TDisposeSurveyCondition condition = new TDisposeSurveyCondition();
        condition.setProviderId(providerId);
        PageHelper.startPage(pageNum, pageSize);
        List<TDisposeSurvey> tDisposeSurveys = disposeSurveyDao.selectList(condition);
        Collections.sort(tDisposeSurveys, (var1, var2) -> var2.getId().compareTo(var1.getId()));
        PageInfo<TDisposeSurvey> pageInfo = new PageInfo<>(tDisposeSurveys);
        return pageInfo;
    }

    @Override
    public int updateSurveyById(TDisposeSurvey survey) {
        return disposeSurveyDao.updateById(survey);
    }

    @Override
    public TDisposeSurvey getDisposeSurveyById(Integer surveyId) {
        return disposeSurveyDao.selectById(surveyId);
    }

    @Override
    public int uploadProviderReport(Integer surveyId, String basisReport, String completeReport, Integer providerId) {
        Optional.ofNullable(basisReport).orElseThrow(()    -> new BusinessException("基础报告缺失"));
        Optional.ofNullable(completeReport).orElseThrow(() -> new BusinessException("完整报告缺失"));
        TDisposeSurvey byId = getDisposeSurveyById(surveyId);
        Optional.ofNullable(byId).orElseThrow(() -> new BusinessException("参数异常"));
//        if (!providerId.equals(byId.getProviderId())) {
//            throw new BusinessException("权限不足");
//        }
        byId.setCompleteSurvey(completeReport);
        byId.setCompleteSurveyPrice(new BigDecimal(ServiceConfigEnum.COMPLETE_REPORT_PRICE.getValue()));
        byId.setBasisSurvey(basisReport);
        byId.setBasisSurveyPrice(new BigDecimal(ServiceConfigEnum.BASIS_REPORT_PRICE.getValue()));
        byId.setSurveyStatus(DisposalEnum.SurveyStatus.UPLOADED.getKey());
        byId.setUpdateTime(new Date());
        int i = updateSurveyById(byId);
        return i;
    }

    @Override
    public TDisposeSurvey getDisposeSurveyByAssetId(Integer assetId) {
        TDisposeSurveyCondition condition = new TDisposeSurveyCondition();
        condition.setAssetId(assetId);
        condition.setDelFlag(false);
        List<TDisposeSurvey> tDisposeSurveys = disposeSurveyDao.selectList(condition);
        if (CollectionUtils.isEmpty(tDisposeSurveys)) {
            return null;
        }
        return tDisposeSurveys.get(0);
    }

    @Override
    public int addDisposeSurvey(String surveyNo, Integer assetId, Integer providerId, Date assignTime, String assetName, String cityId) {
        TDisposeSurvey pojo = new TDisposeSurvey();
        pojo.setSurveyNo(surveyNo);
        pojo.setAssetId(assetId);
        pojo.setProviderId(providerId);
        pojo.setAssignTime(assignTime);
        pojo.setAssetName(assetName);
        pojo.setCityId(cityId);
        pojo.setSurveyStatus(DisposalEnum.SurveyStatus.PENDING_ACCESS.getKey());
        int insert = disposeSurveyDao.insert(pojo);
        return insert;
    }

    @Override
    public TDisposeSurvey getDisposeSurveyBySurveyNo(String surveyNo) {
        TDisposeSurveyCondition condition = new TDisposeSurveyCondition();
        condition.setDelFlag(false);
        condition.setSurveyNo(surveyNo);
        List<TDisposeSurvey> tDisposeSurveys = disposeSurveyDao.selectList(condition);
        return CollectionUtils.isEmpty(tDisposeSurveys) ? null : tDisposeSurveys.get(0);
    }

}
