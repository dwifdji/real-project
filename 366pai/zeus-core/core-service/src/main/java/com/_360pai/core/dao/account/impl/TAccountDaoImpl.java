
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.account.TAccountCondition;
import com._360pai.core.dao.account.TAccountDao;
import com._360pai.core.dao.account.mapper.TAccountMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.facade.account.vo.ApplyRecordVo;
import com._360pai.core.model.account.TAccount;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TAccountDaoImpl extends AbstractDaoImpl<TAccount, TAccountCondition, BaseMapper<TAccount,TAccountCondition>> implements TAccountDao{
	
	@Resource
	private TAccountMapper tAccountMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_account";
	
	@Override
	protected BaseMapper<TAccount, TAccountCondition> daoSupport() {
		return tAccountMapper;
	}

	@Override
	protected TAccountCondition blankCondition() {
		return new TAccountCondition();
	}

	@Override
	public PageInfo getAccountList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAccount> list = tAccountMapper.getAccountList(params);
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo getCompanyAccountList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAccount> list = tAccountMapper.getCompanyAccountList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistMobile(String mobile) {
		TAccountCondition condition = new TAccountCondition();
		condition.setMobile(mobile);
		List<TAccount> list = tAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public TAccount getByMobile(String mobile) {
		TAccountCondition condition = new TAccountCondition();
		condition.setMobile(mobile);
		List<TAccount> list = tAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccount getAgencyAdminAccount(Integer agencyId) {
		TAccountCondition condition = new TAccountCondition();
		condition.setAgencyId(agencyId);
		condition.setIsAgencyAdmin(1);
		List<TAccount> list = tAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccount selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TAccount party = super.selectById(id);
		//	if (party != null) {
		//		doCache(party);
		//	}
		//	return party;
		//} else {
		//	return JSON.parseObject(cacheStr, TAccount.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TAccount account) {
		//int result = super.updateById(account);
		//if (result > 0) {
		//	delCache(account.getId());
		//}
		//return result;
		return super.updateById(account);
	}

	@Override
	public int unBindAgency(Integer id) {
		//int result = tAccountMapper.unBindAgency(id);
		//if (result > 0) {
		//	delCache(id);
		//}
		//return result;
		return tAccountMapper.unBindAgency(id);
	}

	@Override
	public PageInfo getApplyRecordList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<ApplyRecordVo> list = tAccountMapper.getApplyRecordList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int updateCurrentPartyId(Integer id, Integer currentPartyId) {
		//int result = tAccountMapper.updateCurrentPartyId(id, currentPartyId);
		//if (result > 0) {
		//	delCache(id);
		//}
		//return result;
		return tAccountMapper.updateCurrentPartyId(id, currentPartyId);
	}

	@Override
	public String getLatestApplyStatus(Integer accountId) {
		Map<String, Object> params = new HashMap<>();
		params.put("accountId", accountId);
		List<ApplyRecordVo> list = tAccountMapper.getApplyRecordList(params);
		if (list.size() > 0) {
			return list.get(0).getStatus();
		}
		return null;
	}

	@Override
	public TAccount getByShopId(Integer shopId) {
		TAccountCondition condition = new TAccountCondition();
		condition.setShopId(shopId);
		List<TAccount> list = tAccountMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getAppletAccountListNeedRepair(int page, int perPage, Map<String, Object> params) {
		PageHelper.startPage(page, perPage);
		List<TAccount> list = tAccountMapper.getAppletAccountListNeedRepair(params);
		return new PageInfo<>(list);
	}

	@Override
	public String getMobile(Integer accountId) {
		TAccount account = super.selectById(accountId);
		if (account == null) {
			return "";
		}
		return account.getMobile();
	}

	private void doCache(TAccount account) {
		try {
			redisCachemanager.hSet(tableKey, account.getId() + "", JSON.toJSONString(account));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, account.getId()));
		}
	}

	private void delCache(Integer id) {
		try {
			redisCachemanager.hDel(tableKey, id + "");
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, id));
		}
	}
}
