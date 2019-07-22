package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.facade.disposal.DisposeSurveyFacade;
import com._360pai.core.facade.disposal.req.DisposeSurveyReq;
import com._360pai.core.facade.disposal.resp.DisposeSurveyResp;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.City;
import com._360pai.core.model.assistant.Province;
import com._360pai.core.model.disposal.TDisposeSurvey;
import com._360pai.core.service.activity.AuctionActivityService;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.assistant.ProvinceService;
import com._360pai.core.service.disposal.DisposeSurveyService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author xiaolei
 * @create 2018-10-25 09:49
 */
@Component
@Slf4j
@Service(version = "1.0.0")
public class DisposeSurveyProvider implements DisposeSurveyFacade {

    @Autowired
    private DisposeSurveyService disposeSurveyService;
    @Autowired
    private CityService cityService;
    @Autowired
    private ProvinceService provinceService;
    @Autowired
    private SystemProperties systemProperties;
    @Autowired
    private AuctionActivityService auctionActivityService;

    @Override
    public PageInfoResp getSurveyByProviderId(DisposeSurveyReq.GetSurveyList req) {
        PageInfo pageInfo
                = disposeSurveyService.getDisposeSurveyByProviderId(req.getProviderId(), req.getPage(), req.getPerPage());
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public int accessSurvey(Integer surveyId, Integer providerId) {
        TDisposeSurvey survey = getById(surveyId);
        if (null == survey) {
            throw new BusinessException("参数异常");
        }
        if (!providerId.equals(survey.getProviderId())) {
            throw new BusinessException("无权限修改");
        }
        if (!DisposalEnum.SurveyStatus.PENDING_ACCESS.getKey().equals(survey.getSurveyStatus())) {
            throw new BusinessException("参数异常");
        }
        survey.setSurveyStatus(DisposalEnum.SurveyStatus.PENDING_UPLOAD.getKey());
        survey.setUpdateTime(new Date());
        return disposeSurveyService.updateSurveyById(survey);
    }

    @Override
    public int uploadProviderReport(DisposeSurveyReq.UploadReport req) {
        return disposeSurveyService.uploadProviderReport(req.getSurveyId(), req.getBasisReport(), req.getCompleteReport(), req.getProviderId());
    }

    @Override
    public Map<String, Object> getReportTemplate() {
        Map<String, Object> result = new HashMap<>(2);
        result.put("basis", systemProperties.getBasisReport());
        result.put("complete", systemProperties.getCompleteReport());
        return result;
    }

    private TDisposeSurvey getById(Integer surveyId) {
        return disposeSurveyService.getDisposeSurveyById(surveyId);
    }

    private List<DisposeSurveyResp> convertModel(List<TDisposeSurvey> source) {
        List<DisposeSurveyResp> target = new LinkedList<>();
        source.forEach(u -> {
            DisposeSurveyResp disposeSurveyResp = new DisposeSurveyResp();
            BeanUtils.copyProperties(u, disposeSurveyResp);
            disposeSurveyResp.setSurveyId(u.getId());
            disposeSurveyResp.setSurveyStatusDesc(DisposalEnum.SurveyStatus.getValueByKey(u.getSurveyStatus()));
            disposeSurveyResp.setRegion(getRegion(u.getCityId()));
            disposeSurveyResp.setActivityId(getActivityId(u.getAssetId()));
            target.add(disposeSurveyResp);
        });
        return target;
    }

    private Integer getActivityId(Integer assetId) {
        List<AuctionActivity> byAssetId = auctionActivityService.getByAssetId(assetId);
        return CollectionUtils.isNotEmpty(byAssetId) ? byAssetId.get(0).getId() : null;
    }

    private String getRegion(String cityId) {
        String[] split = cityId.split(",");
        String first = split[0];
        City city = cityService.getByCityId(Integer.valueOf(first));
        Province province = provinceService.getById(city.getProvinceId());
        return province.getName().concat(city.getName());
    }
}
