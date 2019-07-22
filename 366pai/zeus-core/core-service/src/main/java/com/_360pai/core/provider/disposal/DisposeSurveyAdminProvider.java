package com._360pai.core.provider.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.arch.common.utils.DateUtil;
import com._360pai.core.common.constants.DisposalEnum;
import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.facade.disposal.DisposeSurveyAdminFacade;
import com._360pai.core.facade.disposal.resp.CityPartnerResp;
import com._360pai.core.model.disposal.TDisposeLevel;
import com._360pai.core.service.disposal.DisposeLevelService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author xiaolei
 * @create 2018-10-25 10:49
 */
@Component
@Service(version = "1.0.0")
public class DisposeSurveyAdminProvider implements DisposeSurveyAdminFacade {

    @Autowired
    private DisposeLevelService disposeLevelService;

    @Override
    public PageInfoResp getFirstLevelCityPartnerList(DisposeProviderReq.GetProviderList req) {
        req.setDisposeType(DisposalEnum.DisposeType.LAW_OFFICE.getKey());
        PageInfo pageInfo = disposeLevelService.getFirstLevelCityPartnerList(req);
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public PageInfoResp getCityPartnerList(DisposeProviderReq.GetProviderList req) {
        req.setDisposeType(DisposalEnum.DisposeType.LAW_OFFICE.getKey());
        PageInfo pageInfo = disposeLevelService.getCityPartnerList(req);
        pageInfo.setList(convertModel(pageInfo.getList()));
        return new PageInfoResp(pageInfo);
    }

    @Override
    public int addOrReplaceFirstPartner(Integer levelId, Integer providerId, Integer operatorId) {
        return disposeLevelService.addOrReplaceFirstPartner(levelId, providerId, operatorId);
    }

    @Override
    public int removeFirstPartner(Integer levelId,  Integer operatorId) {
        return disposeLevelService.removeFirstPartner(levelId, operatorId);
    }

    @Override
    public int updateContractInfo(DisposeProviderReq.UpdateContractInfo req) {
        return disposeLevelService.updateContractInfo(req.getLevelId(),
                req.getOperatorId(), req.getContractDate(), req.getContractNo());
    }


    private List<CityPartnerResp> convertModel(List<TDisposeLevel> source) {
        List<CityPartnerResp> target = new LinkedList<>();
        source.forEach( u -> {
            CityPartnerResp cityPartnerResp = new CityPartnerResp();
            BeanUtils.copyProperties(u, cityPartnerResp);
            cityPartnerResp.setLevelId(u.getId());
            cityPartnerResp.setProviderId(u.getProviderId());
            if (null != u.getContractDate()) {
                cityPartnerResp.setContractDate(DateUtil.format(u.getContractDate(), DateUtil.NORM_DATE_PATTERN));
            }
            target.add(cityPartnerResp);
        });
        return target;
    }
}
