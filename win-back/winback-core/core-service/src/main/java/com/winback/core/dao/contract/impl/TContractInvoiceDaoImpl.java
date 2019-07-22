
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.model.contract.TContract;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractInvoiceCondition;
import com.winback.core.dao.contract.mapper.TContractInvoiceMapper;
import com.winback.core.model.contract.TContractInvoice;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractInvoiceDao;

import java.util.List;
import java.util.Map;

@Service
public class TContractInvoiceDaoImpl extends AbstractDaoImpl<TContractInvoice, TContractInvoiceCondition, BaseMapper<TContractInvoice,TContractInvoiceCondition>> implements TContractInvoiceDao{
	
	@Resource
	private TContractInvoiceMapper tContractInvoiceMapper;
	
	@Override
	protected BaseMapper<TContractInvoice, TContractInvoiceCondition> daoSupport() {
		return tContractInvoiceMapper;
	}

	@Override
	protected TContractInvoiceCondition blankCondition() {
		return new TContractInvoiceCondition();
	}

	@Override
	public PageInfo<TContractInvoice> getFrontList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContractInvoice> list = tContractInvoiceMapper.getFrontList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<TContractInvoice> getBackgroundList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TContractInvoice> list = tContractInvoiceMapper.getBackgroundList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<String> getOrderNoList(Integer invoiceId) {
		return tContractInvoiceMapper.getOrderNoList(invoiceId);
	}
}
