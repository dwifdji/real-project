
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.TFileCondition;
import com._360pai.core.dao.assistant.mapper.TFileMapper;
import com._360pai.core.model.assistant.TFile;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.TFileDao;

@Service
public class TFileDaoImpl extends AbstractDaoImpl<TFile, TFileCondition, BaseMapper<TFile,TFileCondition>> implements TFileDao{
	
	@Resource
	private TFileMapper tFileMapper;
	
	@Override
	protected BaseMapper<TFile, TFileCondition> daoSupport() {
		return tFileMapper;
	}

	@Override
	protected TFileCondition blankCondition() {
		return new TFileCondition();
	}

}
