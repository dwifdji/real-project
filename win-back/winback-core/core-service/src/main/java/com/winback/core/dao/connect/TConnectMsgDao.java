
package com.winback.core.dao.connect;

import com.winback.core.condition.connect.TConnectMsgCondition;
import com.winback.core.model.connect.TConnectMsg;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.connect.HistoryMsgVo;

import java.util.List;

/**
 * <p>TConnectMsg的基础操作Dao</p>
 * @ClassName: TConnectMsgDao
 * @Description: TConnectMsg基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TConnectMsgDao extends BaseDao<TConnectMsg,TConnectMsgCondition>{

    List<HistoryMsgVo> getMsgList(TConnectMsgCondition condition);

    List<HistoryMsgVo> getRoomMsgHistoryList(String roomId);

    TConnectMsg getMaxCreateTimeMsg(String roomId);

    List<HistoryMsgVo> getWebRoomHistoryMsgList(String accountId);
}
