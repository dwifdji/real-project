package com.tzCloud.core.service.impl;

import com.tzCloud.core.common.constants.ServiceConfigEnum;
import com.tzCloud.core.condition.order.TServiceConfigCondition;
import com.tzCloud.core.dao.order.TServiceConfigDao;
import com.tzCloud.core.model.order.TServiceConfig;
import com.tzCloud.core.service.TServiceConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
    public TServiceConfig selectByConfigType(String configType) {
        if (!ServiceConfigEnum.contansKey(configType)) {return null;}
        TServiceConfigCondition condition = new TServiceConfigCondition();
        condition.setConfigType(configType);
        TServiceConfig tServiceConfig = tServiceConfigDao.selectFirst(condition);
        if (tServiceConfig != null) {
            return tServiceConfig;
        } else {
            tServiceConfig = new TServiceConfig();
            tServiceConfig.setConfigType(configType);
            tServiceConfig.setConfigValue(ServiceConfigEnum.getValueByKey(configType));
            tServiceConfig.setCreatedTime(new Date());
            tServiceConfig.setUpdateTime(new Date());
            tServiceConfigDao.insert(tServiceConfig);
        }
        return tServiceConfig;
    }

    @Override
    public List<TServiceConfig> selectByLike(String configStart) {
        return tServiceConfigDao.selectByLike(configStart);
    }

}
