
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.ActivityWhiteListCityCondition;
import com._360pai.core.model.activity.ActivityWhiteListCity;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>ActivityWhiteListCity的基础操作Dao</p>
 * @ClassName: ActivityWhiteListCityDao
 * @Description: ActivityWhiteListCity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface ActivityWhiteListCityDao extends BaseDao<ActivityWhiteListCity,ActivityWhiteListCityCondition>{
    List<Integer> getCityIdListByActivityId(Integer activityId);

    boolean syncCityIdListByActivityId(Integer activityId, List<Integer> newCityIdList);
}
