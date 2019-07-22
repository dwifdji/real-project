
package com._360pai.core.dao.disposal.mapper;

import com._360pai.core.model.account.TDisposeProvider;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.disposal.TDisposeShowCondition;
import com._360pai.core.model.disposal.TDisposeShow;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TDisposeShow数据层的基础操作</p>
 * @ClassName: TDisposeShowMapper
 * @Description: TDisposeShow数据层的基础操作
 * @author Generator
 * @date 2018年10月29日 12时44分58秒
 */
@Mapper
public interface TDisposeShowMapper extends BaseMapper<TDisposeShow, TDisposeShowCondition>{
    List<TDisposeProvider> getShowProvider(@Param("activityId") Integer activityId);
    List<TDisposeProvider> getFirstLevelShowProvider(@Param("cityId") Integer cityId,@Param("activityId") Integer activityId);
}
