
package com._360pai.core.dao.fdd.impl;


import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.fdd.GatewayFddTemplateCondition;
import com._360pai.core.dao.fdd.GatewayFddTemplateDao;
import com._360pai.core.dao.fdd.mapper.GatewayFddTemplateMapper;
import com._360pai.core.model.fdd.GatewayFddTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GatewayFddTemplateDaoImpl extends AbstractDaoImpl<GatewayFddTemplate, GatewayFddTemplateCondition, BaseMapper<GatewayFddTemplate,GatewayFddTemplateCondition>> implements GatewayFddTemplateDao {
	
	@Resource
	private GatewayFddTemplateMapper gatewayFddTemplateMapper;
	
	@Override
	protected BaseMapper<GatewayFddTemplate, GatewayFddTemplateCondition> daoSupport() {
		return gatewayFddTemplateMapper;
	}

	@Override
	protected GatewayFddTemplateCondition blankCondition() {
		return new GatewayFddTemplateCondition();
	}

}
