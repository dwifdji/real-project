
package com._360pai.core.dao.enrolling.impl;

import java.util.List;

import javax.annotation.Resource;

import com._360pai.core.model.enrolling.EnrollingActivity;
import org.springframework.stereotype.Service;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.enrolling.FavoriteEnrollingActivityCondition;
import com._360pai.core.dao.enrolling.FavoriteEnrollingActivityDao;
import com._360pai.core.dao.enrolling.mapper.FavoriteEnrollingActivityMapper;
import com._360pai.core.dto.enrolling.ActivityIdReqDto;
import com._360pai.core.model.enrolling.FavoriteEnrollingActivity;
import com._360pai.core.vo.enrolling.EnrollingInfoVo;

@Service
public class FavoriteEnrollingActivityDaoImpl extends AbstractDaoImpl<FavoriteEnrollingActivity, FavoriteEnrollingActivityCondition, BaseMapper<FavoriteEnrollingActivity,FavoriteEnrollingActivityCondition>> implements FavoriteEnrollingActivityDao{
	
	@Resource
	private FavoriteEnrollingActivityMapper favoriteEnrollingActivityMapper;
	
	@Override
	protected BaseMapper<FavoriteEnrollingActivity, FavoriteEnrollingActivityCondition> daoSupport() {
		return favoriteEnrollingActivityMapper;
	}

	@Override
	protected FavoriteEnrollingActivityCondition blankCondition() {
		return new FavoriteEnrollingActivityCondition();
	}

    @Override
    public List<EnrollingInfoVo> getFocusList(ActivityIdReqDto dto) {
        return favoriteEnrollingActivityMapper.getFocusList(dto);
    }

	@Override
	public Integer deleteModel(String id) {
		return favoriteEnrollingActivityMapper.deleteModel(id);
	}

	@Override
	public Integer deleteModelList(List<String> focusList) {
		return favoriteEnrollingActivityMapper.deleteModelList(focusList);
	}

	@Override
	public List<EnrollingActivity> getBeginIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteEnrollingActivityMapper.getBeginIn5MinuteList(accountId, partyId);
	}

	@Override
	public List<EnrollingActivity> getEndIn5MinuteList(Integer accountId, Integer partyId) {
		return favoriteEnrollingActivityMapper.getEndIn5MinuteList(accountId, partyId);
	}
}
