
package com.tzCloud.core.dao.view.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.caseMatching.resp.LawyerForRankingListResp;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.view.ViewBriefLawyerRankingCondition;
import com.tzCloud.core.dao.view.mapper.ViewBriefLawyerRankingMapper;
import com.tzCloud.core.model.view.ViewBriefLawyerRanking;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.view.ViewBriefLawyerRankingDao;

import java.util.List;

@Service
public class ViewBriefLawyerRankingDaoImpl extends AbstractDaoImpl<ViewBriefLawyerRanking, ViewBriefLawyerRankingCondition, BaseMapper<ViewBriefLawyerRanking,ViewBriefLawyerRankingCondition>> implements ViewBriefLawyerRankingDao{
	
	@Resource
	private ViewBriefLawyerRankingMapper viewBriefLawyerRankingMapper;
	
	@Override
	protected BaseMapper<ViewBriefLawyerRanking, ViewBriefLawyerRankingCondition> daoSupport() {
		return viewBriefLawyerRankingMapper;
	}

	@Override
	protected ViewBriefLawyerRankingCondition blankCondition() {
		return new ViewBriefLawyerRankingCondition();
	}

    @Override
    public List<LawyerForRankingListResp> getLawyerRankingList(ViewBriefLawyerRankingCondition condition) {
		return viewBriefLawyerRankingMapper.getLawyerRankingList(condition);
    }
}
