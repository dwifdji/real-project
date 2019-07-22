package com._360pai.core.service.assistant.impl;

import com._360pai.core.common.constants.ServiceConfigEnum;
import com._360pai.core.condition.assistant.TServiceConfigCondition;
import com._360pai.core.dao.assistant.TServiceConfigDao;
import com._360pai.core.facade.assistant.req.ServiceConfigReq;
import com._360pai.core.facade.withfudig.resp.WithfudigConfigTotalResp;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.core.service.assistant.TServiceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 描述
 *
 * @author : whisky_vip
 * @date : 2018/9/6 18:41
 */
@Service
public class TServiceConfigServiceImpl implements TServiceConfigService {

    @Autowired
    private TServiceConfigDao tServiceConfigDao;

    @Override
    public int updateTotalNum(ServiceConfigReq.UpdateTotalNum req) {
        TServiceConfig tServiceConfig = selectByConfigType(ServiceConfigEnum.WITHFUDIG_TOTAL_NUM);
        tServiceConfig.setConfigValue(req.getTotalNum().toString());
        tServiceConfig.setOperatorId(req.getOperatorId().toString());
        tServiceConfig.setUpdateTime(new Date());
        return tServiceConfigDao.updateById(tServiceConfig);
    }

    @Override
    public int updateTotalAmount(ServiceConfigReq.UpdateTotalAmount req) {
        TServiceConfig tServiceConfig = selectByConfigType(ServiceConfigEnum.WITHFUDIG_TOTAL_AMOUNT);
        tServiceConfig.setConfigValue(req.getTotalAmount().toString());
        tServiceConfig.setOperatorId(req.getOperatorId().toString());
        tServiceConfig.setUpdateTime(new Date());
        return tServiceConfigDao.updateById(tServiceConfig);
    }

    @Override
    public WithfudigConfigTotalResp getTotalData() {
        TServiceConfig           tServiceConfigNum    = selectByConfigType(ServiceConfigEnum.WITHFUDIG_TOTAL_NUM);
        TServiceConfig           tServiceConfigAmount = selectByConfigType(ServiceConfigEnum.WITHFUDIG_TOTAL_AMOUNT);
        WithfudigConfigTotalResp resp                 = new WithfudigConfigTotalResp();
        resp.setTotalNum(new BigDecimal(tServiceConfigNum.getConfigValue()));
        resp.setTotalAmount(new BigDecimal(tServiceConfigAmount.getConfigValue()));
        return resp;
    }

    @Override
    public TServiceConfig selectByConfigType(ServiceConfigEnum serviceConfigEnum) {
        TServiceConfigCondition condition = new TServiceConfigCondition();
        condition.setConfigType(serviceConfigEnum.getKey());
        TServiceConfig tServiceConfig = tServiceConfigDao.selectFirst(condition);
        if (tServiceConfig != null) {
            return tServiceConfig;
        } else {
            tServiceConfig = new TServiceConfig();
            tServiceConfig.setConfigType(serviceConfigEnum.getKey());
            tServiceConfig.setConfigValue(serviceConfigEnum.getValue());
            tServiceConfig.setCreatedTime(new Date());
            tServiceConfig.setUpdateTime(new Date());
            tServiceConfigDao.insert(tServiceConfig);
        }
        return tServiceConfig;
    }

    @Override
    public String findByConfigType(ServiceConfigEnum serviceConfigEnum) {
        TServiceConfig config = selectByConfigType(serviceConfigEnum);
        if (config != null) {
            return config.getConfigValue();
        }
        return serviceConfigEnum.getValue();
    }
}
