
package com.tzCloud.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.order.TServiceConfigCondition;
import com.tzCloud.core.model.order.TServiceConfig;
import com.tzCloud.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TServiceConfig数据层的基础操作</p>
 * @ClassName: TServiceConfigMapper
 * @Description: TServiceConfig数据层的基础操作
 * @author Generator
 * @date 2019年06月19日 15时47分59秒
 */
@Mapper
public interface TServiceConfigMapper extends BaseMapper<TServiceConfig, TServiceConfigCondition>{

    List<TServiceConfig> selectByLike(String configStart);

}
