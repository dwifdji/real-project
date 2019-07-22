
package com.winback.gateway.dao.sms.mapper;

import com.winback.arch.core.abs.BaseMapper;
import com.winback.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.winback.gateway.model.sms.TGatewaySmsRecord;
import org.apache.ibatis.annotations.Mapper;

import com.winback.gateway.condition.sms.TGatewaySmsRecordCondition;
import com.winback.gateway.model.sms.TGatewaySmsRecord;

/**
 * <p>TGatewaySmsRecord数据层的基础操作</p>
 * @ClassName: TGatewaySmsRecordMapper
 * @Description: TGatewaySmsRecord数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 19时33分16秒
 */
@Mapper
public interface TGatewaySmsRecordMapper extends BaseMapper<TGatewaySmsRecord, TGatewaySmsRecordCondition> {

}
