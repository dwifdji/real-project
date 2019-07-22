
package com.winback.core.dao.connect.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.winback.core.condition.connect.TConnectRoomCondition;
import com.winback.core.model.connect.TConnectRoom;
import com.winback.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TConnectRoom数据层的基础操作</p>
 * @ClassName: TConnectRoomMapper
 * @Description: TConnectRoom数据层的基础操作
 * @author Generator
 * @date 2019年01月16日 15时40分04秒
 */
@Mapper
public interface TConnectRoomMapper extends BaseMapper<TConnectRoom, TConnectRoomCondition>{

    TConnectRoom getRomeByAccountId(@Param("accountId") String accountId);
}
