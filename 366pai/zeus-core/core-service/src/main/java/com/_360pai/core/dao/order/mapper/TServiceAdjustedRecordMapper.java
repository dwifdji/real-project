
package com._360pai.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.order.TServiceAdjustedRecordCondition;
import com._360pai.core.model.order.TServiceAdjustedRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TServiceAdjustedRecord数据层的基础操作</p>
 * @ClassName: TServiceAdjustedRecordMapper
 * @Description: TServiceAdjustedRecord数据层的基础操作
 * @author Generator
 * @date 2018年09月30日 11时19分10秒
 */
@Mapper
public interface TServiceAdjustedRecordMapper extends BaseMapper<TServiceAdjustedRecord, TServiceAdjustedRecordCondition>{

    List<TServiceAdjustedRecord> selectAdjustedRecordByIds(Integer[] adjustedIds);
}
