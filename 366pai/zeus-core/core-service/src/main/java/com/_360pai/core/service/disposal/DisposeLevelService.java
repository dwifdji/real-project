package com._360pai.core.service.disposal;

import com._360pai.core.facade.account.req.DisposeProviderReq;
import com._360pai.core.model.disposal.TDisposeLevel;
import com.github.pagehelper.PageInfo;

import java.util.Date;

/**
 * @author xiaolei
 * @create 2018-10-29 15:02
 */
public interface DisposeLevelService {
    boolean isFirstLevel(Integer providerId, String cityId);
    boolean isFirstLevel(Integer partyId);
    Integer getFirstLevelLowOfficeByCityId(String cityId);
    Integer addFirstLevelLowOffice(Integer providerId, String cityId, Integer operatorId);
    PageInfo getFirstLevelCityPartnerList(DisposeProviderReq.GetProviderList req);
    PageInfo getCityPartnerList(DisposeProviderReq.GetProviderList req);
    int addOrReplaceFirstPartner(Integer levelId, Integer providerId, Integer operatorId);
    TDisposeLevel getById(Integer levelId);
    int removeFirstPartner(Integer levelId, Integer operatorId);
    int updateContractInfo(Integer levelId, Integer operatorId, Date contractDate, String contractNo);
    TDisposeLevel getByProviderIdAndCityId(Integer provider, Integer cityId);
    int updateById(TDisposeLevel level);
}
