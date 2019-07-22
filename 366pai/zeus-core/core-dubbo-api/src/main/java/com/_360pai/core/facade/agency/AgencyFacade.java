package com._360pai.core.facade.agency;

import java.util.Map;

public interface AgencyFacade {


    Map<String ,Object> getAgencyById(Integer id);


    void updateAgency(Map<String ,Object> map);

    Map<String, Object> getDefaultAgency();
}
