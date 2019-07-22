package com.tzCloud.core.service;


import com.tzCloud.core.model.order.TServiceConfig;

import java.util.List;

/**
 * 描述 服务设置
 *
 * @author : whisky_vip
 * @date : 2018/9/6 18:41
 */
public interface TServiceConfigService {

    TServiceConfig selectByConfigType(String configType);

    List<TServiceConfig> selectByLike(String configStart);

}
