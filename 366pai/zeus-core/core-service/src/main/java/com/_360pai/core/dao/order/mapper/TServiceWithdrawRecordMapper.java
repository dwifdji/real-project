
package com._360pai.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.order.TServiceWithdrawRecordCondition;
import com._360pai.core.model.order.TServiceWithdrawRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>TServiceWithdrawRecord数据层的基础操作</p>
 * @ClassName: TServiceWithdrawRecordMapper
 * @Description: TServiceWithdrawRecord数据层的基础操作
 * @author Generator
 * @date 2018年10月07日 14时12分38秒
 */
@Mapper
public interface TServiceWithdrawRecordMapper extends BaseMapper<TServiceWithdrawRecord, TServiceWithdrawRecordCondition>{
    List<TServiceWithdrawRecord> selectWithdrawRecordByParam(Map<String, Object> queryParam);
    List<TServiceWithdrawRecord> selectAdminWithdrawRecordByParam(Map<String, Object> queryParam);
}
