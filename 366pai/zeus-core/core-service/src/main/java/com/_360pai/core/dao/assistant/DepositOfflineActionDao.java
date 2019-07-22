
package com._360pai.core.dao.assistant;

import com._360pai.core.condition.assistant.DepositOfflineActionCondition;
import com._360pai.core.model.assistant.DepositOfflineAction;
import com._360pai.arch.core.abs.BaseDao;
import com.github.pagehelper.PageInfo;

import java.util.Map;

/**
 * <p>DepositOfflineAction的基础操作Dao</p>
 * @ClassName: DepositOfflineActionDao
 * @Description: DepositOfflineAction基础操作的Dao
 * @author Generator
 * @date 2018年10月03日 14时24分09秒
 */
public interface DepositOfflineActionDao extends BaseDao<DepositOfflineAction,DepositOfflineActionCondition>{
    PageInfo getList(int page, int perPage, Map<String, Object> params, String orderBy);

    PageInfo getRefundList(int page, int perPage, Map<String, Object> params, String orderBy);
}
