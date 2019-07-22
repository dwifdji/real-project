package com._360pai.core.service.assistant;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2019/4/25 17:07
 */
public interface DetailViewRecodeService {
    void insertEnrollingAppletRecode(Integer activityId, Integer accountId, Integer partyId);

    void insertEnrollingWebRecode(Integer activityId, Integer accountId, Integer partyId);

    void insertActivityAppletRecode(Integer activityId, Integer accountId, Integer partyId);

    void insertActivityWebRecode(Integer activityId, Integer accountId, Integer partyId);
}
