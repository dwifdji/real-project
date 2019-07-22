
package com._360pai.core.dao.activity.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.activity.ViewEnrollmentsOrderCondition;
import com._360pai.core.dao.activity.mapper.ViewEnrollmentsOrderMapper;
import com._360pai.core.model.activity.ViewEnrollmentsOrder;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.activity.ViewEnrollmentsOrderDao;

@Service
public class ViewEnrollmentsOrderDaoImpl extends AbstractDaoImpl<ViewEnrollmentsOrder, ViewEnrollmentsOrderCondition, BaseMapper<ViewEnrollmentsOrder,ViewEnrollmentsOrderCondition>> implements ViewEnrollmentsOrderDao{
	
	@Resource
	private ViewEnrollmentsOrderMapper viewEnrollmentsOrderMapper;
	
	@Override
	protected BaseMapper<ViewEnrollmentsOrder, ViewEnrollmentsOrderCondition> daoSupport() {
		return viewEnrollmentsOrderMapper;
	}

	@Override
	protected ViewEnrollmentsOrderCondition blankCondition() {
		return new ViewEnrollmentsOrderCondition();
	}

}
