
package com.tzCloud.gateway.dao.pay.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.gateway.condition.pay.TGatewayPayOrderCondition;
import com.tzCloud.gateway.model.pay.TGatewayPayOrder;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TGatewayPayOrder数据层的基础操作</p>
 * @ClassName: TGatewayPayOrderMapper
 * @Description: TGatewayPayOrder数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
@Mapper
public interface TGatewayPayOrderMapper extends BaseMapper<TGatewayPayOrder, TGatewayPayOrderCondition>{

}
