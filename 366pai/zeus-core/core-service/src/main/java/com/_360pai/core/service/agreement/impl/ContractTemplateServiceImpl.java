package com._360pai.core.service.agreement.impl;

import com._360pai.core.condition.agreement.ContractTemplateCondition;
import com._360pai.core.dao.agreement.ContractTemplateDao;
import com._360pai.core.model.agreement.ContractTemplate;
import com._360pai.core.service.agreement.ContractTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractTemplateServiceImpl	implements ContractTemplateService{

	@Autowired
	private ContractTemplateDao contractTemplateDao;


	@Override
	public ContractTemplate getTemplate(ContractTemplateCondition template) {


		return contractTemplateDao.selectFirst(template);
	}
}