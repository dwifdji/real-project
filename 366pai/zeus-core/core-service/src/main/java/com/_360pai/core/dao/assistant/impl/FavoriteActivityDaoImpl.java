
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.FavoriteActivityCondition;
import com._360pai.core.dao.assistant.FavoriteActivityDao;
import com._360pai.core.dao.assistant.mapper.FavoriteActivityMapper;
import com._360pai.core.model.activity.AuctionActivity;
import com._360pai.core.model.assistant.FavoriteActivity;
import com._360pai.core.vo.activity.FavoriteActivityVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class FavoriteActivityDaoImpl extends AbstractDaoImpl<FavoriteActivity, FavoriteActivityCondition, BaseMapper<FavoriteActivity,FavoriteActivityCondition>> implements FavoriteActivityDao{
	
	@Resource
	private FavoriteActivityMapper favoriteActivityMapper;
	
	@Override
	protected BaseMapper<FavoriteActivity, FavoriteActivityCondition> daoSupport() {
		return favoriteActivityMapper;
	}

	@Override
	protected FavoriteActivityCondition blankCondition() {
		return new FavoriteActivityCondition();
	}

    @Override
    public int cancelFavor(Integer id, Integer paramsId) {
        return favoriteActivityMapper.cancelFavor(id,paramsId);
    }

    @Override
    public List<FavoriteActivityVo> myFavor(Map<String, Object> params) {
        return favoriteActivityMapper.myFavor(params);
    }

	@Override
	public int unFavor(Integer partyId, Integer activityId,String type ,Integer resourceId, Integer accountId) {
		return favoriteActivityMapper.unFavor(partyId,activityId,type, resourceId, accountId);
	}

	@Override
	public FavoriteActivity selectByPartyIdAndActivityId(Integer partyId, Integer activityId) {
		return favoriteActivityMapper.selectByPartyIdAndActivityId(partyId,activityId);
	}

    @Override
    public int appletFavoriteCount(Integer partyId, Integer accountId) {
        return favoriteActivityMapper.favoriteCount(partyId, accountId,"1");
    }

	@Override
	public int activityFavorCount(Integer activityId) {
		FavoriteActivityCondition condition = new FavoriteActivityCondition();
		condition.setActivityId(activityId);
		List<FavoriteActivity> list = favoriteActivityMapper.selectByCondition(condition);
		return list.size();
	}

	@Override
	public List<FavoriteActivityVo> getMyShopFavor(Integer partyId, Integer accountId) {

		return favoriteActivityMapper.getMyShopFavor(partyId, accountId);
	}

	@Override
	public FavoriteActivity selectShopFavorActivityId(Integer partyId, Integer activityId, String shopId, Integer accountId) {
		return favoriteActivityMapper.selectShopFavorActivityId(partyId, activityId, shopId, accountId);
	}

	@Override
	public List<AuctionActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteActivityMapper.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<AuctionActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteActivityMapper.getEndIn5MinuteList(accountId, partyId);
	}
}
