
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.winback.core.vo._case.TCaseStepNoticeTemplateVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseStepNoticeTemplateCondition;
import com.winback.core.dao._case.mapper.TCaseStepNoticeTemplateMapper;
import com.winback.core.model._case.TCaseStepNoticeTemplate;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseStepNoticeTemplateDao;

import java.util.List;

@Service
public class TCaseStepNoticeTemplateDaoImpl extends AbstractDaoImpl<TCaseStepNoticeTemplate, TCaseStepNoticeTemplateCondition, BaseMapper<TCaseStepNoticeTemplate,TCaseStepNoticeTemplateCondition>> implements TCaseStepNoticeTemplateDao{
	
	@Resource
	private TCaseStepNoticeTemplateMapper tCaseStepNoticeTemplateMapper;
	
	@Override
	protected BaseMapper<TCaseStepNoticeTemplate, TCaseStepNoticeTemplateCondition> daoSupport() {
		return tCaseStepNoticeTemplateMapper;
	}

	@Override
	protected TCaseStepNoticeTemplateCondition blankCondition() {
		return new TCaseStepNoticeTemplateCondition();
	}

	@Override
	public List<TCaseStepNoticeTemplateVO> getTemplateByStepId(String id) {

		return tCaseStepNoticeTemplateMapper.getTemplateByStepId(id);
	}

	@Override
	public List<TCaseStepNoticeTemplateVO> getTemplateByBranchId(String id) {
		return tCaseStepNoticeTemplateMapper.getTemplateByBranchId(id);
	}
}
