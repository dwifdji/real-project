
package com.winback.core.dao.connect.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.connect.TConnectRoomCondition;
import com.winback.core.dao.connect.mapper.TConnectRoomMapper;
import com.winback.core.model.connect.TConnectRoom;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.connect.TConnectRoomDao;

@Service
public class TConnectRoomDaoImpl extends AbstractDaoImpl<TConnectRoom, TConnectRoomCondition, BaseMapper<TConnectRoom,TConnectRoomCondition>> implements TConnectRoomDao{
	
	@Resource
	private TConnectRoomMapper tConnectRoomMapper;
	
	@Override
	protected BaseMapper<TConnectRoom, TConnectRoomCondition> daoSupport() {
		return tConnectRoomMapper;
	}

	@Override
	protected TConnectRoomCondition blankCondition() {
		return new TConnectRoomCondition();
	}

	@Override
	public TConnectRoom getRomeByAccountId(String accountId) {

		return tConnectRoomMapper.getRomeByAccountId(accountId);
	}
}
