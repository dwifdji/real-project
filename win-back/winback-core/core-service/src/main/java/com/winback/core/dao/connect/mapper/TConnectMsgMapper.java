
package com.winback.core.dao.connect.mapper;

import com.winback.core.vo.connect.HistoryMsgVo;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.connect.TConnectMsgCondition;
import com.winback.core.model.connect.TConnectMsg;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TConnectMsg数据层的基础操作</p>
 * @ClassName: TConnectMsgMapper
 * @Description: TConnectMsg数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
@Mapper
public interface TConnectMsgMapper extends BaseMapper<TConnectMsg, TConnectMsgCondition>{

    List<HistoryMsgVo> getMsgList(TConnectMsgCondition condition);

    List<HistoryMsgVo> getRoomMsgHistoryList(@Param("roomId") String roomId);

    TConnectMsg getMaxCreateTimeMsg(@Param("roomId")String roomId);

    List<HistoryMsgVo> getWebRoomHistoryMsgList(@Param("accountId")String accountId);
}
