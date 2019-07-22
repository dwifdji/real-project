
package com.winback.core.dao.account.impl;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.core.facade.account.vo.Party;
import com.winback.core.vo.finance.WorkBenchVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.winback.core.condition.account.TAccountCondition;
import com.winback.core.dao.account.mapper.TAccountMapper;
import com.winback.core.model.account.TAccount;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.account.TAccountDao;

import java.util.List;
import java.util.Map;

@Service
public class TAccountDaoImpl extends AbstractDaoImpl<TAccount, TAccountCondition, BaseMapper<TAccount,TAccountCondition>> implements TAccountDao{
	
	@Resource
	private TAccountMapper tAccountMapper;
	
	@Override
	protected BaseMapper<TAccount, TAccountCondition> daoSupport() {
		return tAccountMapper;
	}

	@Override
	protected TAccountCondition blankCondition() {
		return new TAccountCondition();
	}

	@Override
	public TAccount findByMobile(String mobile) {
		TAccountCondition condition = new TAccountCondition();
		condition.setMobile(mobile);
		List<TAccount> list = tAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<TAccount> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAccount> list = tAccountMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<Party> getPartyList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<Party> list = tAccountMapper.getPartyList(params);
		return new PageInfo<>(list);
	}

	@Override
	public String getMobile(Integer accountId) {
		if (accountId == null) {
			return "";
		}
		TAccount account = super.selectById(accountId);
		if (account != null) {
			return account.getMobile();
		}
		return "";
	}

	@Override
	public List<WorkBenchVO> getTodayRole(String searchDay) {

		return tAccountMapper.getTodayRole(searchDay);
	}
}
