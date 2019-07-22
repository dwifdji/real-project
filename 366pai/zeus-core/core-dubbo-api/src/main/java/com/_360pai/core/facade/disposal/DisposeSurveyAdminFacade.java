package com._360pai.core.facade.disposal;

import com._360pai.arch.common.PageInfoResp;
import com._360pai.core.facade.account.req.DisposeProviderReq;

/**
 * @author xiaolei
 * @create 2018-10-25 10:29
 */
public interface DisposeSurveyAdminFacade {

    /**
     * 获取1级城市合伙人(律师事务所)列表
     * @param req
     * @return
     */
    PageInfoResp getFirstLevelCityPartnerList(DisposeProviderReq.GetProviderList req);

    /**
     * 获取城市合伙人(律师事务所)列表
     * @param req
     * @return
     */
    PageInfoResp getCityPartnerList(DisposeProviderReq.GetProviderList req);

    /**
     * 更新一级合伙人
     * @param levelId
     * @param providerId
     * @param operatorId
     * @return
     */
    int addOrReplaceFirstPartner(Integer levelId, Integer providerId, Integer operatorId);

    /**
     * 删除合伙人
     * @param levelId
     * @return
     */
    int removeFirstPartner(Integer levelId, Integer operatorId);

    /**
     * 更新合同信息
     * @param req
     * @return
     */
    int updateContractInfo(DisposeProviderReq.UpdateContractInfo req);

}
