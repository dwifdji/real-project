
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.ActivityBlackListCityCondition;
import com._360pai.core.model.activity.ActivityBlackListCity;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ActivityBlackListCity的基础操作Dao</p>
 * @ClassName: ActivityBlackListCityDao
 * @Description: ActivityBlackListCity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
public interface ActivityBlackListCityDao extends BaseDao<ActivityBlackListCity,ActivityBlackListCityCondition>{

    List<Integer> getCityIdListByActivityId(Integer activityId);

    boolean syncCityIdListByActivityId(Integer activityId, List<Integer> newCityIdList);
}
