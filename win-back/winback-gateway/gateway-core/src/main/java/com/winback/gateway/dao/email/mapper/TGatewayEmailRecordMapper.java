
package com.winback.gateway.dao.email.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.email.TGatewayEmailRecordCondition;
import com.winback.gateway.model.email.TGatewayEmailRecord;
import org.apache.ibatis.annotations.Mapper;

import com.winback.gateway.condition.email.TGatewayEmailRecordCondition;
import com.winback.gateway.model.email.TGatewayEmailRecord;

/**
 * <p>TGatewayEmailRecord数据层的基础操作</p>
 * @ClassName: TGatewayEmailRecordMapper
 * @Description: TGatewayEmailRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时33分16秒
 */
@Mapper
public interface TGatewayEmailRecordMapper extends BaseMapper<TGatewayEmailRecord, TGatewayEmailRecordCondition> {

}
