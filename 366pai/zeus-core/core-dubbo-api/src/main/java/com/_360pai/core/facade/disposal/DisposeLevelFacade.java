package com._360pai.core.facade.disposal;

/**
 * @author xiaolei
 * @create 2018-10-30 13:18
 */
public interface DisposeLevelFacade {

    /**
     * 是否为一级合伙人
     * @param providerId
     * @return
     */
    boolean isFirstLevelPartner(Integer providerId, String cityId);

    /**
     * 获取当前城市的一级合伙人
     * @param cityId
     * @return
     */
    Integer getFirstLevelPartner(String cityId);

    /**
     * 获取当前活动的城市合伙人
     * @param activityId
     * @return
     */
    Integer getFirstLevelPartnerByActivityId(Integer activityId);

    /**
     * 是否是一级合伙人
     * @param partyId
     * @return
     */
    boolean isFirstLevelPartner(Integer partyId);
}
