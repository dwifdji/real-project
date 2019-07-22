
package com.tzCloud.core.dao.view.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.caseMatching.resp.LawyerForSearchingListResp;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.view.ViewBriefLawfirmLawyerCondition;
import com.tzCloud.core.dao.view.mapper.ViewBriefLawfirmLawyerMapper;
import com.tzCloud.core.model.view.ViewBriefLawfirmLawyer;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.view.ViewBriefLawfirmLawyerDao;

import java.util.List;

@Service
public class ViewBriefLawfirmLawyerDaoImpl extends AbstractDaoImpl<ViewBriefLawfirmLawyer, ViewBriefLawfirmLawyerCondition, BaseMapper<ViewBriefLawfirmLawyer,ViewBriefLawfirmLawyerCondition>> implements ViewBriefLawfirmLawyerDao{
	
	@Resource
	private ViewBriefLawfirmLawyerMapper viewBriefLawfirmLawyerMapper;
	
	@Override
	protected BaseMapper<ViewBriefLawfirmLawyer, ViewBriefLawfirmLawyerCondition> daoSupport() {
		return viewBriefLawfirmLawyerMapper;
	}

	@Override
	protected ViewBriefLawfirmLawyerCondition blankCondition() {
		return new ViewBriefLawfirmLawyerCondition();
	}

    @Override
    public List<LawyerForSearchingListResp> getLawyerList(ViewBriefLawfirmLawyerCondition condition) {
		return viewBriefLawfirmLawyerMapper.getLawyerList(condition);
    }

	@Override
	public List<ViewBriefLawfirmLawyer> selectNeedProcessList(ViewBriefLawfirmLawyerCondition condition) {
		return viewBriefLawfirmLawyerMapper.selectNeedProcessList(condition);
	}
}
