
package com.winback.core.dao.applet;

import com.winback.core.condition.applet.TAppletReadMessageMapCondition;
import com.winback.core.model.applet.TAppletReadMessageMap;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TAppletReadMessageMap的基础操作Dao</p>
 * @ClassName: TAppletReadMessageMapDao
 * @Description: TAppletReadMessageMap基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TAppletReadMessageMapDao extends BaseDao<TAppletReadMessageMap,TAppletReadMessageMapCondition>{
    void  readMessage(Integer extBindId, Integer messageId);
}
