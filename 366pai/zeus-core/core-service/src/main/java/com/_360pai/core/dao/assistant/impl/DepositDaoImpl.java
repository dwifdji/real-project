
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.DepositCondition;
import com._360pai.core.dao.assistant.DepositDao;
import com._360pai.core.dao.assistant.mapper.DepositMapper;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.Deposit;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DepositDaoImpl extends AbstractDaoImpl<Deposit, DepositCondition, BaseMapper<Deposit,DepositCondition>> implements DepositDao{
	
	@Resource
	private DepositMapper depositMapper;
	
	@Override
	protected BaseMapper<Deposit, DepositCondition> daoSupport() {
		return depositMapper;
	}

	@Override
	protected DepositCondition blankCondition() {
		return new DepositCondition();
	}

	@Override
	public Integer getDepositCount(Integer activityId) {
		return depositMapper.getDepositCount(activityId, null);
	}

	@Override
    public Integer getDepositCount(Integer activityId, Integer agencyId) {
        return depositMapper.getDepositCount(activityId, agencyId);
    }

    @Override
    public List<Map> myDepositList(Integer partyId,String categoryId,String name) {
        return depositMapper.myDepositList(partyId,categoryId,name);
    }

    @Override
    public Deposit getByActivityIdPartId(Integer activityId, Integer partyId) {
		return depositMapper.getByActivityIdPartId(activityId,partyId);
    }

	@Override
	public List<Deposit> selectNoDealYX(Integer activityId) {
		return depositMapper.selectNoDealYX(activityId);
	}

	@Override
	public int updateDealYX(Integer level) {
		return depositMapper.updateDealYX(level);
	}

	@Override
	public List<AuctionActivity> getBeginIn5MinuteList(Integer partyId) {
		return depositMapper.getBeginIn5MinuteList(partyId);
	}

	@Override
	public List<AuctionActivity> getEndIn5MinuteList(Integer partyId) {
		return depositMapper.getEndIn5MinuteList(partyId);
	}
}
