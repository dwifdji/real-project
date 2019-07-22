package com._360pai.core.service.assistant;

import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;
import com._360pai.core.model.assistant.TServiceConfig;


/**
 * 描述 服务设置
 *
 * @author : whisky_vip
 * @date : 2018/9/6 18:41
 */
public interface TServiceConfigService {

    int updateTotalNum(ServiceConfigReq.UpdateTotalNum req);

    int updateTotalAmount(ServiceConfigReq.UpdateTotalAmount req);

    WithfudigConfigTotalResp getTotalData();

    TServiceConfig selectByConfigType(ServiceConfigEnum serviceConfigEnum);

    String findByConfigType(ServiceConfigEnum serviceConfigEnum);
}
