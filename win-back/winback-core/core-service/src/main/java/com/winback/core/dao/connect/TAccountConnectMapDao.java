
package com.winback.core.dao.connect;

import com.winback.core.condition.connect.TAccountConnectMapCondition;
import com.winback.core.model.connect.TAccountConnectMap;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TAccountConnectMap的基础操作Dao</p>
 * @ClassName: TAccountConnectMapDao
 * @Description: TAccountConnectMap基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TAccountConnectMapDao extends BaseDao<TAccountConnectMap,TAccountConnectMapCondition>{
    TAccountConnectMap findBy(Integer accountId, String clientId);

    TAccountConnectMap findLatestBy(Integer accountId);
}
