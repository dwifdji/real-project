
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import javafx.scene.chart.Axis;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.NotifyPartyActivityCondition;
import com._360pai.core.dao.assistant.mapper.NotifyPartyActivityMapper;
import com._360pai.core.model.assistant.NotifyPartyActivity;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.NotifyPartyActivityDao;

import java.util.List;
import java.util.Map;

@Service
public class NotifyPartyActivityDaoImpl extends AbstractDaoImpl<NotifyPartyActivity, NotifyPartyActivityCondition, BaseMapper<NotifyPartyActivity,NotifyPartyActivityCondition>> implements NotifyPartyActivityDao{
	
	@Resource
	private NotifyPartyActivityMapper notifyPartyActivityMapper;
	
	@Override
	protected BaseMapper<NotifyPartyActivity, NotifyPartyActivityCondition> daoSupport() {
		return notifyPartyActivityMapper;
	}

	@Override
	protected NotifyPartyActivityCondition blankCondition() {
		return new NotifyPartyActivityCondition();
	}

    @Override
    public List<Map> myNotify(Integer partyId, Integer accountId,String categoryId,String name) {
        return notifyPartyActivityMapper.myNotify( partyId, accountId,categoryId,name);
    }


	@Override
	public int deleteNotify(Integer id) {
		return notifyPartyActivityMapper.deleteNotify(id);
	}

    @Override
    public int cancel(Integer activityId, Integer partyPrimaryId, Integer accountId) {
        return notifyPartyActivityMapper.cancel(activityId,partyPrimaryId, accountId);
    }

    @Override
    public NotifyPartyActivity selectByPartyIdAndActivityId(Integer partyId, Integer activityId, Integer accountId) {
        return notifyPartyActivityMapper.selectByPartyIdAndActivityId(partyId,activityId,accountId);
    }

	@Override
	public int activityNotifiersCount(Integer activityId) {
		NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
		condition.setActivityId(activityId);
		List<NotifyPartyActivity> list = notifyPartyActivityMapper.selectByCondition(condition);
		return list.size();
	}

	@Override
	public List<NotifyPartyActivity> getByActivityId(Integer activityId) {
		NotifyPartyActivityCondition condition = new NotifyPartyActivityCondition();
		condition.setActivityId(activityId);
		return notifyPartyActivityMapper.selectByCondition(condition);
	}

	@Override
	public List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyActivityMapper.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return notifyPartyActivityMapper.getEndIn5MinuteList(accountId, partyId);
	}
}
