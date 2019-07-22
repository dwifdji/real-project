
package com._360pai.core.dao.order.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.order.TServiceBusinessRecordCondition;
import com._360pai.core.model.order.TServiceBusinessRecord;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>TServiceBusinessRecord数据层的基础操作</p>
 * @ClassName: TServiceBusinessRecordMapper
 * @Description: TServiceBusinessRecord数据层的基础操作
 * @author Generator
 * @date 2018年10月08日 19时37分27秒
 */
@Mapper
public interface TServiceBusinessRecordMapper extends BaseMapper<TServiceBusinessRecord, TServiceBusinessRecordCondition>{

    List<TServiceBusinessRecord> getBusinessRecordByReportType(TServiceBusinessRecordCondition condition);

}
