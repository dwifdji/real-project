
package com.tzCloud.gateway.dao.pay.mapper;

import com.tzCloud.gateway.condition.pay.TGatewayPayWayCondition;
import com.tzCloud.gateway.model.pay.TGatewayPayWay;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.gateway.condition.pay.TGatewayPayWayCondition;
import com.tzCloud.gateway.model.pay.TGatewayPayWay;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TGatewayPayWay数据层的基础操作</p>
 * @ClassName: TGatewayPayWayMapper
 * @Description: TGatewayPayWay数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
@Mapper
public interface TGatewayPayWayMapper extends BaseMapper<TGatewayPayWay, TGatewayPayWayCondition>{

}
