
package com.tzCloud.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.core.condition.order.TServiceOrderCondition;
import com.tzCloud.core.model.order.TServiceOrder;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TServiceOrder数据层的基础操作</p>
 * @ClassName: TServiceOrderMapper
 * @Description: TServiceOrder数据层的基础操作
 * @author Generator
 * @date 2019年06月19日 15时47分59秒
 */
@Mapper
public interface TServiceOrderMapper extends BaseMapper<TServiceOrder, TServiceOrderCondition>{

}
