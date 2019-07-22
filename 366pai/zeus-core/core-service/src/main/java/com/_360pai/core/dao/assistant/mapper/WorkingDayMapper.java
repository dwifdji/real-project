
package com._360pai.core.dao.assistant.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.WorkingDayCondition;
import com._360pai.core.model.assistant.WorkingDay;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>WorkingDay数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: WorkingDayMapper
 * @Description: WorkingDay数据层的基础操作
 * @date 2018年08月10日 17时37分18秒
 */
@Mapper
public interface WorkingDayMapper extends BaseMapper<WorkingDay, WorkingDayCondition> {

    int deleteWorkingDay(@Param("paramsId") Integer paramsId);
}
