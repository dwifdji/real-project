package com._360pai.core.provider.agency;

import com._360pai.core.facade.agency.AgencyFacade;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.service.account.AgencyService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 */
@Component
@Service(version = "1.0.0")
public class AgencyProvider implements AgencyFacade {

    @Autowired
    private AgencyService agencyService;

    @Override
    public Map<String, Object> getAgencyById(Integer id) {
        TAgency agency = agencyService.findByAgencyId(id);

        Map<String, Object> map = new HashMap<>();
        map.put("jointStatus",agency.getIsJoint());
        return map;
    }

    @Override
    public void updateAgency(Map<String, Object> map) {

        agencyService.updateAgency(map);

    }

    @Override
    public Map<String, Object> getDefaultAgency() {
        return agencyService.getDefaultAgency();
    }
}
