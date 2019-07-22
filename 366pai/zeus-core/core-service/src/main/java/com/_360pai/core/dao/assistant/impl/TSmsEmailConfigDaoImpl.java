
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TSmsEmailConfigCondition;
import com._360pai.core.dao.assistant.mapper.TSmsEmailConfigMapper;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TSmsEmailConfigDao;

@Service
public class TSmsEmailConfigDaoImpl extends AbstractDaoImpl<TSmsEmailConfig, TSmsEmailConfigCondition, BaseMapper<TSmsEmailConfig,TSmsEmailConfigCondition>> implements TSmsEmailConfigDao{
	
	@Resource
	private TSmsEmailConfigMapper tSmsEmailConfigMapper;
	
	@Override
	protected BaseMapper<TSmsEmailConfig, TSmsEmailConfigCondition> daoSupport() {
		return tSmsEmailConfigMapper;
	}

	@Override
	protected TSmsEmailConfigCondition blankCondition() {
		return new TSmsEmailConfigCondition();
	}

}
