
package com.tzCloud.core.dao.assistant.impl;

import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.core.condition.assistant.TSmsEmailConfigCondition;
import com.tzCloud.core.dao.assistant.TSmsEmailConfigDao;
import com.tzCloud.core.dao.assistant.mapper.TSmsEmailConfigMapper;
import com.tzCloud.core.model.assistant.TSmsEmailConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TSmsEmailConfigDaoImpl extends AbstractDaoImpl<TSmsEmailConfig, TSmsEmailConfigCondition, BaseMapper<TSmsEmailConfig,TSmsEmailConfigCondition>> implements TSmsEmailConfigDao {
	
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
