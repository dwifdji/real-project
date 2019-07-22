package com._360pai.core.provider.assistant;

import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.facade.assistant.ServiceConfigFacade;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.req.WithfudigConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.service.assistant.TServiceConfigService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/7 09:48
 */
@Component
@Service(version = "1.0.0")
public class ServiceConfigProvider implements ServiceConfigFacade {

    @Autowired
    private TServiceConfigService tServiceConfigService;


    @Override
    public WithfudigConfigTotalResp getTotalData() {
        return tServiceConfigService.getTotalData();
    }

    @Override
    public String selectByConfigType(ServiceConfigEnum serviceConfigEnum) {
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(serviceConfigEnum);
        return tServiceConfig.getConfigValue();
    }

    @Override
    public int updateTotalNum(ServiceConfigReq.UpdateTotalNum req) {
        return tServiceConfigService.updateTotalNum(req);
    }

    @Override
    public int updateTotalAmount(ServiceConfigReq.UpdateTotalAmount req) {
        return tServiceConfigService.updateTotalAmount(req);
    }
}
