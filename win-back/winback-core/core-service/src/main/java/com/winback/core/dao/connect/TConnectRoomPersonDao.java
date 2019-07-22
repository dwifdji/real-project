
package com.winback.core.dao.connect;

import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.arch.core.abs.BaseDao;
import com.winback.core.vo.connect.RoomMsgBackVO;
import com.winback.core.vo.connect.RoomMsgListVO;
import com.winback.core.vo.connect.RoomPersonVo;
import com.winback.core.vo.operate.OperateIconListVO;

import java.util.List;

/**
 * <p>TConnectRoomPerson的基础操作Dao</p>
 * @ClassName: TConnectRoomPersonDao
 * @Description: TConnectRoomPerson基础操作的Dao
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
public interface TConnectRoomPersonDao extends BaseDao<TConnectRoomPerson,TConnectRoomPersonCondition>{


    void batchSave(List<TConnectRoomPerson> batchList);


    List<RoomPersonVo> getRoomPersonList(TConnectRoomPersonCondition condition);

    List<RoomMsgListVO> getRoomMsgList();

    List<RoomMsgBackVO> getRoomMsgBackList(List<String> roomIds);

    void updateUnreadNum(String accountId);
}
