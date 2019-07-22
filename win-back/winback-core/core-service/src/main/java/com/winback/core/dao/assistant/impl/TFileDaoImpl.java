
package com.winback.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.assistant.TFileCondition;
import com.winback.core.dao.assistant.mapper.TFileMapper;
import com.winback.core.model.assistant.TFile;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.assistant.TFileDao;

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
