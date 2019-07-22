
package com.winback.core.dao.payment.impl;

import javax.annotation.Resource;

import com.winback.core.dto.finance.CommonDto;
import com.winback.core.dto.finance.ExpendDto;
import com.winback.core.vo.finance.ExpendAuditVo;
import com.winback.core.vo.finance.ExpendVo;
import com.winback.core.vo.finance.WorkBenchVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.payment.TFinanceExpendCondition;
import com.winback.core.dao.payment.mapper.TFinanceExpendMapper;
import com.winback.core.model.payment.TFinanceExpend;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.payment.TFinanceExpendDao;

import java.util.List;

@Service
public class TFinanceExpendDaoImpl extends AbstractDaoImpl<TFinanceExpend, TFinanceExpendCondition, BaseMapper<TFinanceExpend,TFinanceExpendCondition>> implements TFinanceExpendDao{
	
	@Resource
	private TFinanceExpendMapper tFinanceExpendMapper;
	
	@Override
	protected BaseMapper<TFinanceExpend, TFinanceExpendCondition> daoSupport() {
		return tFinanceExpendMapper;
	}

	@Override
	protected TFinanceExpendCondition blankCondition() {
		return new TFinanceExpendCondition();
	}

	@Override
	public List<ExpendVo> getExpendList(ExpendDto req) {
		return tFinanceExpendMapper.getExpendList(req);
	}

	@Override
	public List<ExpendAuditVo> getExpendAuditList(CommonDto dto) {
		return tFinanceExpendMapper.getExpendAuditList(dto);
	}

	@Override
	public List<WorkBenchVO> getFinanceWorkBench() {
		return tFinanceExpendMapper.getFinanceWorkBench();
	}

	@Override
	public String getExpendSum(ExpendDto req) {
		return tFinanceExpendMapper.getExpendSum(req);
	}
}
