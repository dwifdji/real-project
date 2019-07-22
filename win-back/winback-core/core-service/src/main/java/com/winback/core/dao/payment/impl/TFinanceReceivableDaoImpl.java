
package com.winback.core.dao.payment.impl;

import javax.annotation.Resource;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ReceivableDto;
import com.winback.core.vo.finance.ReceivableAuditVo;
import com.winback.core.vo.finance.ReceivableVo;
import org.springframework.stereotype.Service;

import com.winback.core.condition.payment.TFinanceReceivableCondition;
import com.winback.core.dao.payment.mapper.TFinanceReceivableMapper;
import com.winback.core.model.payment.TFinanceReceivable;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.payment.TFinanceReceivableDao;

import java.util.List;

@Service
public class TFinanceReceivableDaoImpl extends AbstractDaoImpl<TFinanceReceivable, TFinanceReceivableCondition, BaseMapper<TFinanceReceivable,TFinanceReceivableCondition>> implements TFinanceReceivableDao{
	
	@Resource
	private TFinanceReceivableMapper tFinanceReceivableMapper;
	
	@Override
	protected BaseMapper<TFinanceReceivable, TFinanceReceivableCondition> daoSupport() {
		return tFinanceReceivableMapper;
	}

	@Override
	protected TFinanceReceivableCondition blankCondition() {
		return new TFinanceReceivableCondition();
	}

	@Override
	public List<ReceivableVo> getReceivableList(ReceivableDto dto) {
		return tFinanceReceivableMapper.getReceivableList(dto);
	}

	@Override
	public List<ReceivableAuditVo> getReceivableAuditList(CommonDto dto) {
		return tFinanceReceivableMapper.getReceivableAuditList(dto);
	}

	@Override
	public String getReceivableSum(ReceivableDto dto) {
		return tFinanceReceivableMapper.getReceivableSum(dto);
	}
}
