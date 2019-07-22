
package com.winback.core.dao.connect.mapper;

import com.winback.core.vo.connect.RoomMsgBackVO;
import com.winback.core.vo.connect.RoomMsgListVO;
import com.winback.core.vo.connect.RoomPersonVo;
import com.winback.core.vo.operate.OperateIconListVO;
import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>TConnectRoomPerson数据层的基础操作</p>
 * @ClassName: TConnectRoomPersonMapper
 * @Description: TConnectRoomPerson数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
@Mapper
public interface TConnectRoomPersonMapper extends BaseMapper<TConnectRoomPerson, TConnectRoomPersonCondition>{


    List<RoomPersonVo> getRoomPersonList(TConnectRoomPersonCondition condition);


    void  batchSave(List<TConnectRoomPerson> batchList);

    List<RoomMsgListVO> getRoomMsgList();

    List<RoomMsgBackVO> getRoomMsgBackList(List<String> roomIds);

    void updateUnreadNum(@Param("accountId") String accountId);
}
