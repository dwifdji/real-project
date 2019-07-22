
package com.tzCloud.core.dao.view.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.caseMatching.resp.CourtResp;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.view.ViewBriefCourtCondition;
import com.tzCloud.core.dao.view.mapper.ViewBriefCourtMapper;
import com.tzCloud.core.model.view.ViewBriefCourt;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.view.ViewBriefCourtDao;

import java.util.List;

@Service
public class ViewBriefCourtDaoImpl extends AbstractDaoImpl<ViewBriefCourt, ViewBriefCourtCondition, BaseMapper<ViewBriefCourt,ViewBriefCourtCondition>> implements ViewBriefCourtDao{
	
	@Resource
	private ViewBriefCourtMapper viewBriefCourtMapper;
	
	@Override
	protected BaseMapper<ViewBriefCourt, ViewBriefCourtCondition> daoSupport() {
		return viewBriefCourtMapper;
	}

	@Override
	protected ViewBriefCourtCondition blankCondition() {
		return new ViewBriefCourtCondition();
	}

    @Override
    public List<CourtResp> getCourtList(ViewBriefCourtCondition viewBriefCourtCondition) {

		return viewBriefCourtMapper.getCourtList(viewBriefCourtCondition);
    }

	@Override
	public Integer getTotalCaseCount(String briefStr) {
		return viewBriefCourtMapper.getTotalCaseCount(briefStr);
	}
}
