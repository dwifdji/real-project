
package com._360pai.core.dao.agreement.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.agreement.ContractTemplateCondition;
import com._360pai.core.dao.agreement.mapper.ContractTemplateMapper;
import com._360pai.core.model.agreement.ContractTemplate;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.agreement.ContractTemplateDao;

@Service
public class ContractTemplateDaoImpl extends AbstractDaoImpl<ContractTemplate, ContractTemplateCondition, BaseMapper<ContractTemplate,ContractTemplateCondition>> implements ContractTemplateDao{
	
	@Resource
	private ContractTemplateMapper contractTemplateMapper;
	
	@Override
	protected BaseMapper<ContractTemplate, ContractTemplateCondition> daoSupport() {
		return contractTemplateMapper;
	}

	@Override
	protected ContractTemplateCondition blankCondition() {
		return new ContractTemplateCondition();
	}

}
