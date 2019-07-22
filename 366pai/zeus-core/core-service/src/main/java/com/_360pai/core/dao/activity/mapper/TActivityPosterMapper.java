
package com._360pai.core.dao.activity.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.activity.TActivityPosterCondition;
import com._360pai.core.model.activity.TActivityPoster;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TActivityPoster数据层的基础操作</p>
 * @ClassName: TActivityPosterMapper
 * @Description: TActivityPoster数据层的基础操作
 * @author Generator
 * @date 2019年07月01日 10时42分57秒
 */
@Mapper
public interface TActivityPosterMapper extends BaseMapper<TActivityPoster, TActivityPosterCondition>{
    List<TActivityPoster> getFrontList(Map<String, Object> params);

    List<Map> getManualAuctionActivityList(@Param("activityIdList") List<Integer> activityIdList);

    List<Map> getManualEnrollingActivityList(@Param("activityIdList") List<Integer> activityIdList);
}
