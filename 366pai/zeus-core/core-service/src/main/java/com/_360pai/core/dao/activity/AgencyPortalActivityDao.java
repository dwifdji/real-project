
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.AgencyPortalActivityCondition;
import com._360pai.core.model.activity.AgencyPortalActivity;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>AgencyPortalActivity的基础操作Dao</p>
 * @ClassName: AgencyPortalActivityDao
 * @Description: AgencyPortalActivity基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface AgencyPortalActivityDao extends BaseDao<AgencyPortalActivity,AgencyPortalActivityCondition>{

    int deleteNotify(Integer id);

    List<AgencyPortalActivity> getByActivity(Integer activityId);

    AgencyPortalActivity getOrCreateInstance(Integer activityId, Integer agencyPortalId, Integer agencyId);
}
