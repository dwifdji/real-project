
package com.tzCloud.gateway.dao.check.mapper;

import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.gateway.condition.check.TGatewayCheckRecordCondition;
import com.tzCloud.gateway.model.check.TGatewayCheckRecord;
import org.apache.ibatis.annotations.Mapper;

import com.tzCloud.gateway.condition.check.TGatewayCheckRecordCondition;
import com.tzCloud.gateway.model.check.TGatewayCheckRecord;

/**
 * <p>TGatewayCheckRecord数据层的基础操作</p>
 * @ClassName: TGatewayCheckRecordMapper
 * @Description: TGatewayCheckRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月24日 09时20分52秒
 */
@Mapper
public interface TGatewayCheckRecordMapper extends BaseMapper<TGatewayCheckRecord, TGatewayCheckRecordCondition> {

}
