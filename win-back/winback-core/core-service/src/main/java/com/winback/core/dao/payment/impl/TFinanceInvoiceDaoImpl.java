
package com.winback.core.dao.payment.impl;

import javax.annotation.Resource;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.InvoiceDto;
import com.winback.core.vo.finance.InvoiceAuditVo;
import com.winback.core.vo.finance.InvoiceVo;
import org.springframework.stereotype.Service;

import com.winback.core.condition.payment.TFinanceInvoiceCondition;
import com.winback.core.dao.payment.mapper.TFinanceInvoiceMapper;
import com.winback.core.model.payment.TFinanceInvoice;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.payment.TFinanceInvoiceDao;

import java.util.List;

@Service
public class TFinanceInvoiceDaoImpl extends AbstractDaoImpl<TFinanceInvoice, TFinanceInvoiceCondition, BaseMapper<TFinanceInvoice,TFinanceInvoiceCondition>> implements TFinanceInvoiceDao{
	
	@Resource
	private TFinanceInvoiceMapper tFinanceInvoiceMapper;
	
	@Override
	protected BaseMapper<TFinanceInvoice, TFinanceInvoiceCondition> daoSupport() {
		return tFinanceInvoiceMapper;
	}

	@Override
	protected TFinanceInvoiceCondition blankCondition() {
		return new TFinanceInvoiceCondition();
	}

	@Override
	public List<InvoiceVo> getInvoiceList(InvoiceDto req) {
		return tFinanceInvoiceMapper.getInvoiceList(req);
	}

	@Override
	public List<InvoiceAuditVo> getInvoiceAuditList(CommonDto dto) {
		return tFinanceInvoiceMapper.getInvoiceAuditList(dto);
	}

	@Override
	public String getInvoiceSum(InvoiceDto req) {
		return tFinanceInvoiceMapper.getInvoiceSum(req);
	}
}
