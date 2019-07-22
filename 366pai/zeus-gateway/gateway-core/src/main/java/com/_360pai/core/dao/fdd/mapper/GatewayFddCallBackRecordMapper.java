
package com._360pai.core.dao.fdd.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.fdd.GatewayFddCallBackRecordCondition;
import com._360pai.core.model.fdd.GatewayFddCallBackRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>GatewayFddCallBackRecord数据层的基础操作</p>
 * @ClassName: GatewayFddCallBackRecordMapper
 * @Description: GatewayFddCallBackRecord数据层的基础操作
 * @author Generator
 * @date 2018年09月25日 21时54分48秒
 */
@Mapper
public interface GatewayFddCallBackRecordMapper extends BaseMapper<GatewayFddCallBackRecord, GatewayFddCallBackRecordCondition>{


    List<GatewayFddCallBackRecord> notifyFailureList();
}
