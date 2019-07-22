
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.common.constants.ServiceConfigEnum;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TServiceConfigCondition;
import com._360pai.core.dao.assistant.mapper.TServiceConfigMapper;
import com._360pai.core.model.assistant.TServiceConfig;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TServiceConfigDao;

import java.util.Date;
import java.util.List;

/**
 * 描述 服务设置
 *
 * @author : whisky_vip
 * @date : 2018/9/7 9:25
 */
@Service
public class TServiceConfigDaoImpl extends AbstractDaoImpl<TServiceConfig, TServiceConfigCondition, BaseMapper<TServiceConfig, TServiceConfigCondition>> implements TServiceConfigDao {

    @Resource
    private TServiceConfigMapper tServiceConfigMapper;

    @Override
    protected BaseMapper<TServiceConfig, TServiceConfigCondition> daoSupport() {
        return tServiceConfigMapper;
    }

    @Override
    protected TServiceConfigCondition blankCondition() {
        return new TServiceConfigCondition();
    }

}
