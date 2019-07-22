
package com.winback.core.dao.connect;

import com.winback.core.condition.connect.TConnectRoomCondition;
import com.winback.core.model.connect.TConnectRoom;
import com.winback.arch.core.abs.BaseDao;

/**
 * <p>TConnectRoom的基础操作Dao</p>
 * @ClassName: TConnectRoomDao
 * @Description: TConnectRoom基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TConnectRoomDao extends BaseDao<TConnectRoom,TConnectRoomCondition>{

    TConnectRoom getRomeByAccountId(String accountId);
}
