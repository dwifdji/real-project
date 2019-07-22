
package com.winback.gateway.dao.pay.mapper;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import org.apache.ibatis.annotations.Mapper;

import com.winback.gateway.condition.pay.TGatewayPayOrderCondition;
import com.winback.gateway.model.pay.TGatewayPayOrder;
import com.winback.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TGatewayPayOrder数据层的基础操作</p>
 * @ClassName: TGatewayPayOrderMapper
 * @Description: TGatewayPayOrder数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
@Mapper
public interface TGatewayPayOrderMapper extends BaseMapper<TGatewayPayOrder, TGatewayPayOrderCondition>{
    /**
     *
     *获取未支付订单
     */
    List<TGatewayPayOrder> getNotPayOrder();
}
