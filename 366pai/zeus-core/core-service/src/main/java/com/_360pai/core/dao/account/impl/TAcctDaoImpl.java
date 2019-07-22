
package com._360pai.core.dao.account.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.account.TAcctCondition;
import com._360pai.core.dao.account.TAcctDao;
import com._360pai.core.dao.account.mapper.TAcctMapper;
import com._360pai.core.facade.account.vo.AcctVo;
import com._360pai.core.model.account.TAcct;
import com._360pai.core.model.account.TAcctDetail;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAcctDaoImpl extends AbstractDaoImpl<TAcct, TAcctCondition, BaseMapper<TAcct,TAcctCondition>> implements TAcctDao{
	
	@Resource
	private TAcctMapper tAcctMapper;
	
	@Override
	protected BaseMapper<TAcct, TAcctCondition> daoSupport() {
		return tAcctMapper;
	}

	@Override
	protected TAcctCondition blankCondition() {
		return new TAcctCondition();
	}

	@Override
	public TAcct getByPartyIdTypeForUpdate(String type,Integer partyId) {
		return tAcctMapper.getByPartyIdTypeForUpdate(type,partyId);
	}

	@Override
	public int addAmt(TAcct acct) {
		return tAcctMapper.addAmt(acct);
	}

	@Override
	public int subAmt(TAcct acct) {
		return tAcctMapper.subAmt(acct);
	}


	@Override
	public TAcct getByIdForUpdate(Integer id) {
		return tAcctMapper.getByIdForUpdate(id);
	}


	@Override
	public TAcct getByPartyIdType(Integer partyId, String type) {
		TAcctCondition condition = new TAcctCondition();
		condition.setPartyId(partyId);
		condition.setType(type);
		List<TAcct> list = tAcctMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<AcctVo> list = tAcctMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public Map<String, Object> getSummaryInfo(Map<String, Object> params) {
		return tAcctMapper.getSummaryInfo(params);
	}

}
