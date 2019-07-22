
package com.winback.core.dao.payment.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition.payment.TInvoiceCondition;
import com.winback.core.dao.payment.mapper.TInvoiceMapper;
import com.winback.core.model.payment.TInvoice;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.payment.TInvoiceDao;

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
