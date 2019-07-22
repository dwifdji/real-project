
package com._360pai.core.dao.account.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.account.TInvoiceCondition;
import com._360pai.core.dao.account.mapper.TInvoiceMapper;
import com._360pai.core.model.account.TInvoice;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.account.TInvoiceDao;

@Service
public class TInvoiceDaoImpl extends AbstractDaoImpl<TInvoice, TInvoiceCondition, BaseMapper<TInvoice,TInvoiceCondition>> implements TInvoiceDao{
	
	@Resource
	private TInvoiceMapper tInvoiceMapper;
	
	@Override
	protected BaseMapper<TInvoice, TInvoiceCondition> daoSupport() {
		return tInvoiceMapper;
	}

	@Override
	protected TInvoiceCondition blankCondition() {
		return new TInvoiceCondition();
	}

}
