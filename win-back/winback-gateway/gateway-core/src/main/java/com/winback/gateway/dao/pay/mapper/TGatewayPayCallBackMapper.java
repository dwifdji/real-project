
package com.winback.gateway.dao.pay.mapper;

import com.winback.gateway.condition.pay.TGatewayPayCallBackCondition;
import com.winback.gateway.model.pay.TGatewayPayCallBack;
import org.apache.ibatis.annotations.Mapper;

import com.winback.gateway.condition.pay.TGatewayPayCallBackCondition;
import com.winback.gateway.model.pay.TGatewayPayCallBack;
import com.winback.arch.core.abs.BaseMapper;

/**
 * <p>TGatewayPayCallBack数据层的基础操作</p>
 * @ClassName: TGatewayPayCallBackMapper
 * @Description: TGatewayPayCallBack数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
@Mapper
public interface TGatewayPayCallBackMapper extends BaseMapper<TGatewayPayCallBack, TGatewayPayCallBackCondition>{

}
