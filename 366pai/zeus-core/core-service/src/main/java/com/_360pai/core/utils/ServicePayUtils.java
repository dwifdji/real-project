package com._360pai.core.utils;

import com._360pai.arch.core.sysconfig.properties.GatewayProperties;
import com._360pai.arch.core.sysconfig.properties.SystemProperties;
import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.common.constants.ServiceOrderEnum;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.service.assistant.TServiceConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/11/15 09:47
 */
@Component
@Slf4j
public class ServicePayUtils {

    @Autowired
    private TServiceConfigService tServiceConfigService;
    @Autowired
    private GatewayProperties     gatewayProperties;
    @Autowired
    private SystemProperties      systemProperties;

    /**
     * 描述 是否需要支付
     * false 需要支付
     * true 不需要支付
     *
     * @author : whisky_vip
     * @date : 2018/11/15 9:58
     */
    public Boolean noNeedPay(Integer partyId, ServiceConfigEnum serviceConfigEnum, ServiceOrderEnum.OrderType orderType) {
        TServiceConfig tServiceConfig = tServiceConfigService.selectByConfigType(serviceConfigEnum);
        BigDecimal     price          = new BigDecimal(tServiceConfig.getConfigValue());
        if (price.compareTo(BigDecimal.ZERO) <= 0 || checkNotPayContainsPartyId(partyId, orderType)) {
            return true;
        }
        return false;
    }

    public Boolean checkNotPayContainsPartyId(Integer partyId, ServiceOrderEnum.OrderType orderType) {
        try {
            String partyIds = "";
            if (ServiceOrderEnum.OrderType.COMPRADOR_REQUIREMENT.equals(orderType)) {
                partyIds = gatewayProperties.getCompradorNotPay();
            } else if (ServiceOrderEnum.OrderType.WITHFUDIG_REQUIREMENT.equals(orderType)) {
                partyIds = gatewayProperties.getWithfudigNotPay();
            } else if (ServiceOrderEnum.OrderType.DIPOSAL_REQUIREMENT.equals(orderType)) {
                partyIds = gatewayProperties.getDisposalNotPay();
            }
            return (partyId != null && !StringUtils.isBlank(partyIds))
                    && Arrays.asList(partyIds.split(",")).contains(partyId.toString());
        } catch (Exception e) {
            log.error("获取不需要支付的partyId 异常，异常信息为{}", e);
        }
        return false;
    }

    public int getServicePayExpiredTime() {
        return Optional.ofNullable(systemProperties.getServicePayExpired()).orElse(60 * 24);
    }
}
