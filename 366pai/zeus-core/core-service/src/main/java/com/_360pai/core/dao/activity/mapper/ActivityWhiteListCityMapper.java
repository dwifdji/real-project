
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.ActivityWhiteListCityCondition;
import com._360pai.core.model.activity.ActivityWhiteListCity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>ActivityWhiteListCity数据层的基础操作</p>
 * @ClassName: ActivityWhiteListCityMapper
 * @Description: ActivityWhiteListCity数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface ActivityWhiteListCityMapper extends BaseMapper<ActivityWhiteListCity, ActivityWhiteListCityCondition>{

    List<Integer> getIdListByActivityId(@Param("activityId") Integer activityId);

    int deleteById(@Param("id") Integer id);
}
