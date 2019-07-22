
package com._360pai.core.dao.account.impl;


import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TCityPartnerCondition;
import com._360pai.core.dao.account.TCityPartnerDao;
import com._360pai.core.dao.account.mapper.TCityPartnerMapper;
import com._360pai.core.model.account.TCityPartner;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TCityPartnerDaoImpl extends AbstractDaoImpl<TCityPartner, TCityPartnerCondition, BaseMapper<TCityPartner,TCityPartnerCondition>> implements TCityPartnerDao {
	
	@Resource
	private TCityPartnerMapper tCityPartnerMapper;
	
	@Override
	protected BaseMapper<TCityPartner, TCityPartnerCondition> daoSupport() {
		return tCityPartnerMapper;
	}

	@Override
	protected TCityPartnerCondition blankCondition() {
		return new TCityPartnerCondition();
	}

}
