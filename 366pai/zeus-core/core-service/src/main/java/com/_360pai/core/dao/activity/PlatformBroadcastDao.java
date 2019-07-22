
package com._360pai.core.dao.activity;

import com._360pai.core.condition.activity.PlatformBroadcastCondition;
import com._360pai.core.model.activity.PlatformBroadcast;
import com._360pai.arch.core.abs.BaseDao;

import java.util.List;

/**
 * <p>PlatformBroadcast的基础操作Dao</p>
 * @ClassName: PlatformBroadcastDao
 * @Description: PlatformBroadcast基础操作的Dao
 * @author Generator
 * @date 2018年08月10日 17时37分15秒
 */
public interface PlatformBroadcastDao extends BaseDao<PlatformBroadcast,PlatformBroadcastCondition>{

    List<PlatformBroadcast> getBroadcastList();
}
