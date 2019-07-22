
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.TActivityServiceProviderCondition;
import com._360pai.core.model.account.TDisposeProvider;
import com._360pai.core.model.assistant.TActivityServiceProvider;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * <p>TActivityServiceProvider的基础操作Dao</p>
 * @ClassName: TActivityServiceProviderDao
 * @Description: TActivityServiceProvider基础操作的Dao
 * @author Generator
 * @date 2019年05月21日 12时56分21秒
 */
public interface TActivityServiceProviderDao extends BaseDao<TActivityServiceProvider,TActivityServiceProviderCondition>{

    List<TDisposeProvider> getFirstLevelAuctionDisposeProvider(Integer activityId, Integer cityId);

    List<TDisposeProvider> getAuctionDisposeProvider(Integer activityId);

    List<TDisposeProvider> getEnrollingDisposeProvider(Integer activityId);

    List<TDisposeProvider> getDisposeProvider(Integer activityId, String activityType);

    TActivityServiceProvider findDisposeBy(Integer activityId, String activityType, Integer providerId);

    TActivityServiceProvider findFundBy(Integer activityId, String activityType, Integer providerId);

    PageInfo<TActivityServiceProvider> getActivityServiceDisposeProviderEnrollRecord(int page, int perPage, Map<String, Object> params);

    int countFundBy(Integer activityId, String activityType);

    PageInfo<TActivityServiceProvider> getActivityServiceFundProviderEnrollRecord(int page, int perPage, Map<String, Object> params);

    int deleteBackDisposeProvider(Integer activityId, String activityType);

    TDisposeProvider getFirstLevelDisposeProvider(Integer cityId);

    TActivityServiceProvider getAuctionDisposeProvider(Integer activityId, Integer providerId);

    int removeFirstLevelAuctionDisposeProviderByProviderId(Integer providerId);
}
