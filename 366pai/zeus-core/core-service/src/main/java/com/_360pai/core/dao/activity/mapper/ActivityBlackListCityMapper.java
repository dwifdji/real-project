
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.ActivityBlackListCityCondition;
import com._360pai.core.model.activity.ActivityBlackListCity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ActivityBlackListCity数据层的基础操作</p>
 * @ClassName: ActivityBlackListCityMapper
 * @Description: ActivityBlackListCity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface ActivityBlackListCityMapper extends BaseMapper<ActivityBlackListCity, ActivityBlackListCityCondition>{
    List<Integer> getIdListByActivityId(@Param("activityId") Integer activityId);

    int deleteById(@Param("id") Integer id);
}
