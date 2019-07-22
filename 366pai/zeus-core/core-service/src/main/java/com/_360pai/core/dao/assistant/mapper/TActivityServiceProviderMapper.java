
package com._360pai.core.dao.assistant.mapper;

import com._360pai.core.model.account.TDisposeProvider;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.assistant.TActivityServiceProviderCondition;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TActivityServiceProvider数据层的基础操作</p>
 * @ClassName: TActivityServiceProviderMapper
 * @Description: TActivityServiceProvider数据层的基础操作
 * @author Generator
 * @date 2019年05月21日 12时56分21秒
 */
@Mapper
public interface TActivityServiceProviderMapper extends BaseMapper<TActivityServiceProvider, TActivityServiceProviderCondition>{

    List<TDisposeProvider> getAuctionDisposeProvider(@Param("activityId") Integer activityId);

    TDisposeProvider getFirstLevelDisposeProvider(@Param("cityId") Integer cityId);

    List<TDisposeProvider> getEnrollingDisposeProvider(@Param("activityId") Integer activityId);

    List<TDisposeProvider> getDisposeProvider(@Param("activityId") Integer activityId, @Param("activityType") String activityType);

    List<TActivityServiceProvider> getActivityServiceDisposeProviderEnrollRecord(Map<String, Object> params);

    List<TActivityServiceProvider> getActivityServiceFundProviderEnrollRecord(Map<String, Object> params);
}
