
package com.winback.core.dao.assistant;

import com.winback.core.condition.assistant.TAppReadMessageMapCondition;
import com.winback.core.model.assistant.TAppReadMessageMap;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TAppReadMessageMap的基础操作Dao</p>
 * @ClassName: TAppReadMessageMapDao
 * @Description: TAppReadMessageMap基础操作的Dao
 * @author Generator
 * @date 2019年01月18日 10时01分28秒
 */
public interface TAppReadMessageMapDao extends BaseDao<TAppReadMessageMap,TAppReadMessageMapCondition>{
    void  readMessage(Integer accountId, Integer messageId);
}
