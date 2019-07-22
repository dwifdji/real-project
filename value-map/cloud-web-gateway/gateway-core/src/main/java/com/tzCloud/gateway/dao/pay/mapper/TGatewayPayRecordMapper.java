
package com.tzCloud.gateway.dao.pay.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.gateway.condition.pay.TGatewayPayRecordCondition;
import com.tzCloud.gateway.model.pay.TGatewayPayRecord;
import com.tzCloud.arch.core.abs.BaseMapper;

/**
 * <p>TGatewayPayRecord数据层的基础操作</p>
 * @ClassName: TGatewayPayRecordMapper
 * @Description: TGatewayPayRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时25分32秒
 */
@Mapper
public interface TGatewayPayRecordMapper extends BaseMapper<TGatewayPayRecord, TGatewayPayRecordCondition>{

}
