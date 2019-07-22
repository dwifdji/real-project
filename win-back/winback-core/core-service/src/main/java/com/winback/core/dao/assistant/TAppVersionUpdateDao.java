
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.TAppVersionUpdateCondition;
import com.winback.core.model.assistant.TAppVersionUpdate;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TAppVersionUpdate的基础操作Dao</p>
 * @ClassName: TAppVersionUpdateDao
 * @Description: TAppVersionUpdate基础操作的Dao
 * @author Generator
 * @date 2019年05月06日 18时05分00秒
 */
public interface TAppVersionUpdateDao extends BaseDao<TAppVersionUpdate,TAppVersionUpdateCondition>{
    TAppVersionUpdate findLatestBy(String deviceType);
}
