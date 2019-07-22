package com._360pai.core.provider.order;

import com._360pai.core.facade.order.ServiceOrderCallBackFacade;
import com._360pai.core.service.order.ServiceOrderService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/15 15:04
 */
@Component
@Service(version = "1.0.0")
public class ServiceOrderCallBackProvider implements ServiceOrderCallBackFacade {

    @Autowired
    private ServiceOrderService serviceOrderService;

    @Override
    public void doCallBackProcess(Integer serviceOrderId, String code, String msg) {
        serviceOrderService.doProcess(serviceOrderId,code,msg);
    }
}
