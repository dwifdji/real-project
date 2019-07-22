package com._360pai.core.provider.fastway;

import com._360pai.core.common.constants.FastwayEnum;
import com._360pai.core.facade.assistant.vo.CityVo;
import com._360pai.core.facade.fastway.AgencyApplyFacade;
import com._360pai.core.facade.fastway.req.AgencyApplyReq;
import com._360pai.core.model.account.TCompany;
import com._360pai.core.model.assistant.City;
import com._360pai.core.service.assistant.CityService;
import com._360pai.core.service.fastway.AgencyApplyService;
import com._360pai.core.service.fastway.impl.AgencyApplyServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaolei
 * @create 2018-11-29 14:36
 */
@Component
@Service(version = "1.0.0")
public class AgencyApplyProvider implements AgencyApplyFacade {

    @Autowired
    private AgencyApplyService agencyApplyService;
    @Autowired
    private CityService cityService;

    @Override
    public int auctionApply(AgencyApplyReq.AuctionApply req, Integer accountId) {
        JSONObject json = new JSONObject();
        json.put(FastwayEnum.AgencyType.AUCTION, req.getAgencyVO());
        return agencyApplyService.auctionApply(json, accountId, req.getSource(), req.getAgencyVO().getPartyId());
    }

    @Override
    public Map<String, Object> agencyAuthInfo(Integer accountId, Integer partyId, Integer agencyId) {
        Map<String, Object> result = new HashMap<>();
        String agencyAuthCode = agencyApplyService.getAgencyAuthCode(accountId, partyId, agencyId);
        result.put("authCode", agencyAuthCode);
        if (agencyAuthCode.equals(AgencyApplyServiceImpl.UNSUBMITTED)
                || agencyAuthCode.equals(AgencyApplyServiceImpl.REJECT)) {
            List<TCompany> byAccountId = agencyApplyService.findByAccountId(accountId);
            if (CollectionUtils.isNotEmpty(byAccountId)) {
                byAccountId.removeIf(t -> FastwayEnum.DisposeStatusEnum.waitting.getKey().equals(t.getAgencyApplyStatus()));
            }
            for (TCompany company : byAccountId)
            {
                CityVo city = new CityVo();
                if (StringUtils.isNotBlank(company.getAreaId())) {
                    city.setAreaId(Integer.parseInt(company.getAreaId()));
                    city.setAreaName(cityService.getAreaName(Integer.parseInt(company.getAreaId())));
                }
                if (StringUtils.isNotBlank(company.getCityId())) {
                    city.setId(Integer.parseInt(company.getCityId()));
                    city.setName(cityService.getCityName(Integer.parseInt(company.getCityId())));
                }

                if (StringUtils.isNotBlank(company.getProvinceId())) {
                    city.setProvinceId(Integer.parseInt(company.getProvinceId()));
                    city.setProvinceName(cityService.getProvinceName(Integer.parseInt(company.getProvinceId())));
                }
                company.setCityVo(city);
            }
            result.put("list", byAccountId);
        }
        return result;
    }

    @Override
    public void checkAgencyAbbr(String abbr) {
        agencyApplyService.checkAgencyAbbr(abbr);
    }
}
