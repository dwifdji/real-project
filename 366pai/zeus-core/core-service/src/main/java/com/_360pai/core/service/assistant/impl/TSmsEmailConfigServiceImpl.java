package com._360pai.core.service.assistant.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TSmsEmailConfigCondition;
import com._360pai.core.dao.assistant.TSmsEmailConfigDao;
import com._360pai.core.model.assistant.TSmsEmailConfig;
import com._360pai.core.service.assistant.TSmsEmailConfigService;

/**
 * @author 刘好磊
 * @Title: TSmsEmailConfigServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/10/13
 */
@Service
public class TSmsEmailConfigServiceImpl implements TSmsEmailConfigService {
	
	@Autowired
	private TSmsEmailConfigDao tSmsEmailConfigDao;

	@Override
	public TSmsEmailConfig getMobileByType(String type) {

		TSmsEmailConfigCondition tSmsEmailConfigCondition = new TSmsEmailConfigCondition();
		tSmsEmailConfigCondition.setBusType(type);
		tSmsEmailConfigCondition.setStatus("1");
		return tSmsEmailConfigDao.selectFirst(tSmsEmailConfigCondition);
	} 
	
	
}
