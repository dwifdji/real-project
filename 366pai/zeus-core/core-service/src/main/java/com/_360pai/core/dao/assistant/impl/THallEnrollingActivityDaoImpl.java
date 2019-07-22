
package com._360pai.core.dao.assistant.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.assistant.THallEnrollingActivityCondition;
import com._360pai.core.dao.assistant.THallEnrollingActivityDao;
import com._360pai.core.dao.assistant.mapper.THallEnrollingActivityMapper;
import com._360pai.core.model.assistant.THallEnrollingActivity;
import com._360pai.core.vo.enrolling.web.HomeEnrollingActivityVO;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.annotation.Resource;

@Service
public class THallEnrollingActivityDaoImpl extends AbstractDaoImpl<THallEnrollingActivity, THallEnrollingActivityCondition, BaseMapper<THallEnrollingActivity,THallEnrollingActivityCondition>> implements THallEnrollingActivityDao {
	
	@Resource
	private THallEnrollingActivityMapper tHallEnrollingActivityMapper;
	
	@Override
	protected BaseMapper<THallEnrollingActivity, THallEnrollingActivityCondition> daoSupport() {
		return tHallEnrollingActivityMapper;
	}

	@Override
	protected THallEnrollingActivityCondition blankCondition() {
		return new THallEnrollingActivityCondition();
	}

	@Override
	public List<HomeEnrollingActivityVO> getHallEnrollingActivities(Integer hallId) {
		return tHallEnrollingActivityMapper.getHallEnrollingActivities(hallId);
	}

}
