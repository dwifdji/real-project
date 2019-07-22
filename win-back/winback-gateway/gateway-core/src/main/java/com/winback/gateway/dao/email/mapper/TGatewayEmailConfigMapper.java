
package com.winback.gateway.dao.email.mapper;

import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import com.winback.gateway.condition.email.TGatewayEmailConfigCondition;
import com.winback.gateway.model.email.TGatewayEmailConfig;

/**
 * <p>TGatewayEmailConfig数据层的基础操作</p>
 * @ClassName: TGatewayEmailConfigMapper
 * @Description: TGatewayEmailConfig数据层的基础操作
 * @author Generator
 * @date 2019年03月07日 20时57分16秒
 */
@Mapper
public interface TGatewayEmailConfigMapper extends BaseMapper<TGatewayEmailConfig, TGatewayEmailConfigCondition> {

}
