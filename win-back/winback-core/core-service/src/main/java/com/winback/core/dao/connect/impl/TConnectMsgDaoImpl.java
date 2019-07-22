
package com.winback.core.dao.connect.impl;

import javax.annotation.Resource;

import com.winback.core.vo.connect.HistoryMsgVo;
import org.springframework.stereotype.Service;

import com.winback.core.condition.connect.TConnectMsgCondition;
import com.winback.core.dao.connect.mapper.TConnectMsgMapper;
import com.winback.core.model.connect.TConnectMsg;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.connect.TConnectMsgDao;

import java.util.List;

@Service
public class TConnectMsgDaoImpl extends AbstractDaoImpl<TConnectMsg, TConnectMsgCondition, BaseMapper<TConnectMsg,TConnectMsgCondition>> implements TConnectMsgDao{
	
	@Resource
	private TConnectMsgMapper tConnectMsgMapper;
	
	@Override
	protected BaseMapper<TConnectMsg, TConnectMsgCondition> daoSupport() {
		return tConnectMsgMapper;
	}

	@Override
	protected TConnectMsgCondition blankCondition() {
		return new TConnectMsgCondition();
	}

	@Override
	public List<HistoryMsgVo> getMsgList(TConnectMsgCondition condition) {
		return tConnectMsgMapper.getMsgList(condition);
	}

	@Override
	public List<HistoryMsgVo> getRoomMsgHistoryList(String roomId) {
		return tConnectMsgMapper.getRoomMsgHistoryList(roomId);
	}

	@Override
	public TConnectMsg getMaxCreateTimeMsg(String roomId) {
		return tConnectMsgMapper.getMaxCreateTimeMsg(roomId);
	}

	@Override
	public List<HistoryMsgVo> getWebRoomHistoryMsgList(String accountId) {
		return tConnectMsgMapper.getWebRoomHistoryMsgList(accountId);
	}
}
