
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.PartnerAgencyCondition;
import com._360pai.core.dao.assistant.mapper.PartnerAgencyMapper;
import com._360pai.core.model.assistant.PartnerAgency;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.PartnerAgencyDao;

@Service
public class PartnerAgencyDaoImpl extends AbstractDaoImpl<PartnerAgency, PartnerAgencyCondition, BaseMapper<PartnerAgency,PartnerAgencyCondition>> implements PartnerAgencyDao{
	
	@Resource
	private PartnerAgencyMapper partnerAgencyMapper;
	
	@Override
	protected BaseMapper<PartnerAgency, PartnerAgencyCondition> daoSupport() {
		return partnerAgencyMapper;
	}

	@Override
	protected PartnerAgencyCondition blankCondition() {
		return new PartnerAgencyCondition();
	}

    @Override
    public int deletePartnerAgency(Integer paramsId) {
        return partnerAgencyMapper.deletePartnerAgency(paramsId);
    }
}
