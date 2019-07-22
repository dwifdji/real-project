
package com.tzCloud.core.dao.view.impl;

import javax.annotation.Resource;

import com.tzCloud.core.facade.caseMatching.resp.LawyerFirmResp;
import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.view.ViewBriefCourtLawfirmCondition;
import com.tzCloud.core.dao.view.mapper.ViewBriefCourtLawfirmMapper;
import com.tzCloud.core.model.view.ViewBriefCourtLawfirm;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.view.ViewBriefCourtLawfirmDao;

import java.util.List;

@Service
public class ViewBriefCourtLawfirmDaoImpl extends AbstractDaoImpl<ViewBriefCourtLawfirm, ViewBriefCourtLawfirmCondition, BaseMapper<ViewBriefCourtLawfirm,ViewBriefCourtLawfirmCondition>> implements ViewBriefCourtLawfirmDao{
	
	@Resource
	private ViewBriefCourtLawfirmMapper viewBriefCourtLawfirmMapper;
	
	@Override
	protected BaseMapper<ViewBriefCourtLawfirm, ViewBriefCourtLawfirmCondition> daoSupport() {
		return viewBriefCourtLawfirmMapper;
	}

	@Override
	protected ViewBriefCourtLawfirmCondition blankCondition() {
		return new ViewBriefCourtLawfirmCondition();
	}

    @Override
    public List<LawyerFirmResp> getLawFirmList(ViewBriefCourtLawfirmCondition req) {
		return viewBriefCourtLawfirmMapper.getLawFirmList(req);
    }
}
