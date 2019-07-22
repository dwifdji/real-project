
package com.winback.core.dao.contract.impl;

import javax.annotation.Resource;

import com.winback.arch.common.enums.ApiCallResult;
import com.winback.core.exception.BusinessException;
import com.winback.core.model.contract.TContractOrder;
import org.springframework.stereotype.Service;

import com.winback.core.condition.contract.TContractInvoiceOrderMapCondition;
import com.winback.core.dao.contract.mapper.TContractInvoiceOrderMapMapper;
import com.winback.core.model.contract.TContractInvoiceOrderMap;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.contract.TContractInvoiceOrderMapDao;

import java.util.List;

@Service
public class TContractInvoiceOrderMapDaoImpl extends AbstractDaoImpl<TContractInvoiceOrderMap, TContractInvoiceOrderMapCondition, BaseMapper<TContractInvoiceOrderMap,TContractInvoiceOrderMapCondition>> implements TContractInvoiceOrderMapDao{
	
	@Resource
	private TContractInvoiceOrderMapMapper tContractInvoiceOrderMapMapper;
	
	@Override
	protected BaseMapper<TContractInvoiceOrderMap, TContractInvoiceOrderMapCondition> daoSupport() {
		return tContractInvoiceOrderMapMapper;
	}

	@Override
	protected TContractInvoiceOrderMapCondition blankCondition() {
		return new TContractInvoiceOrderMapCondition();
	}

	@Override
	public void createMap(Integer invoiceId, List<Integer> orderIds) {
		int result;
		for (Integer orderId : orderIds) {
			TContractInvoiceOrderMap invoiceOrderMap = new TContractInvoiceOrderMap();
			invoiceOrderMap.setInvoiceId(invoiceId);
			invoiceOrderMap.setOrderId(orderId);
			result = tContractInvoiceOrderMapMapper.insert(invoiceOrderMap);
			if (result == 0) {
				throw new BusinessException(ApiCallResult.FAILURE);
			}
		}
	}

	@Override
	public List<TContractOrder> getOrderList(Integer invoiceId) {
		return tContractInvoiceOrderMapMapper.getOrderList(invoiceId);
	}
}
