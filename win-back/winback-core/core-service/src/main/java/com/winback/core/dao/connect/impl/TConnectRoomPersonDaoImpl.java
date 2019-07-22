
package com.winback.core.dao.connect.impl;

import javax.annotation.Resource;

import com.winback.core.vo.connect.RoomMsgBackVO;
import com.winback.core.vo.connect.RoomMsgListVO;
import com.winback.core.vo.connect.RoomPersonVo;
import com.winback.core.vo.operate.OperateIconListVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.connect.TConnectRoomPersonCondition;
import com.winback.core.dao.connect.mapper.TConnectRoomPersonMapper;
import com.winback.core.model.connect.TConnectRoomPerson;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.connect.TConnectRoomPersonDao;

import java.util.List;

@Service
public class TConnectRoomPersonDaoImpl extends AbstractDaoImpl<TConnectRoomPerson, TConnectRoomPersonCondition, BaseMapper<TConnectRoomPerson,TConnectRoomPersonCondition>> implements TConnectRoomPersonDao{
	
	@Resource
	private TConnectRoomPersonMapper tConnectRoomPersonMapper;

	@Override
	protected BaseMapper<TConnectRoomPerson, TConnectRoomPersonCondition> daoSupport() {
		return tConnectRoomPersonMapper;
	}

	@Override
	protected TConnectRoomPersonCondition blankCondition() {
		return new TConnectRoomPersonCondition();
	}

	@Override
	public void batchSave(List<TConnectRoomPerson> batchList) {
		tConnectRoomPersonMapper.batchSave(batchList);
	}

	@Override
	public List<RoomPersonVo> getRoomPersonList(TConnectRoomPersonCondition condition) {
		return tConnectRoomPersonMapper.getRoomPersonList(condition);
	}

	@Override
	public List<RoomMsgListVO> getRoomMsgList() {
		return tConnectRoomPersonMapper.getRoomMsgList();
	}

	@Override
	public List<RoomMsgBackVO> getRoomMsgBackList(List<String> roomIds) {
		return tConnectRoomPersonMapper.getRoomMsgBackList(roomIds);
	}

	@Override
	public void updateUnreadNum(String accountId) {
		tConnectRoomPersonMapper.updateUnreadNum(accountId);
	}
}
