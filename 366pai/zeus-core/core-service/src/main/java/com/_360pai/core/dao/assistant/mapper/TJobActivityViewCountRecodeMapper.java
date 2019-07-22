
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.condition.assistant.TJobActivityViewCountRecodeCondition;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.model.assistant.TJobActivityViewCountRecode;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TJobActivityViewCountRecode数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TJobActivityViewCountRecodeMapper
 * @Description: TJobActivityViewCountRecode数据层的基础操作
 * @date 2018年12月04日 13时50分38秒
 */
@Mapper
public interface TJobActivityViewCountRecodeMapper extends BaseMapper<TJobActivityViewCountRecode, TJobActivityViewCountRecodeCondition> {

    int getTotalActivityViewCount(@Param("activityType") Integer activityType, @Param("activityId") Integer activityId);
}
