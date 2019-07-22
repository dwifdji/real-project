
package com.tzCloud.core.dao.applicant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.applicant.TMapApplicantCondition;
import com.tzCloud.core.dao.applicant.mapper.TMapApplicantMapper;
import com.tzCloud.core.model.applicant.TMapApplicant;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.applicant.TMapApplicantDao;

@Service
public class TMapApplicantDaoImpl extends AbstractDaoImpl<TMapApplicant, TMapApplicantCondition, BaseMapper<TMapApplicant,TMapApplicantCondition>> implements TMapApplicantDao{
	
	@Resource
	private TMapApplicantMapper tMapApplicantMapper;
	
	@Override
	protected BaseMapper<TMapApplicant, TMapApplicantCondition> daoSupport() {
		return tMapApplicantMapper;
	}

	@Override
	protected TMapApplicantCondition blankCondition() {
		return new TMapApplicantCondition();
	}

}
