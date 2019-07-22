
package com.winback.core.dao.systemsite.impl;

import javax.annotation.Resource;

import com.winback.core.vo.systemsite.CaseStatusMsgVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.systemsite.TCaseStatusMsgTemplateCondition;
import com.winback.core.dao.systemsite.mapper.TCaseStatusMsgTemplateMapper;
import com.winback.core.model.systemsite.TCaseStatusMsgTemplate;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.systemsite.TCaseStatusMsgTemplateDao;

import java.util.List;

@Service
public class TCaseStatusMsgTemplateDaoImpl extends AbstractDaoImpl<TCaseStatusMsgTemplate, TCaseStatusMsgTemplateCondition, BaseMapper<TCaseStatusMsgTemplate,TCaseStatusMsgTemplateCondition>> implements TCaseStatusMsgTemplateDao{
	
	@Resource
	private TCaseStatusMsgTemplateMapper tCaseStatusMsgTemplateMapper;
	
	@Override
	protected BaseMapper<TCaseStatusMsgTemplate, TCaseStatusMsgTemplateCondition> daoSupport() {
		return tCaseStatusMsgTemplateMapper;
	}

	@Override
	protected TCaseStatusMsgTemplateCondition blankCondition() {
		return new TCaseStatusMsgTemplateCondition();
	}

	@Override
	public List<CaseStatusMsgVO> getCaseStatusMsgList(String type) {
		return tCaseStatusMsgTemplateMapper.getCaseStatusMsgList(type);
	}
}
