
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.account.TCompanyCondition;
import com._360pai.core.dao.account.TCompanyDao;
import com._360pai.core.dao.account.mapper.TCompanyMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.account.TCompany;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TCompanyDaoImpl extends AbstractDaoImpl<TCompany, TCompanyCondition, BaseMapper<TCompany,TCompanyCondition>> implements TCompanyDao{
	
	@Resource
	private TCompanyMapper tCompanyMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_company";

	@Override
	protected BaseMapper<TCompany, TCompanyCondition> daoSupport() {
		return tCompanyMapper;
	}

	@Override
	protected TCompanyCondition blankCondition() {
		return new TCompanyCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TCompany> list = tCompanyMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TCompany getById(Integer id) {
		TCompanyCondition condition = new TCompanyCondition();
		condition.setId(id);
		List<TCompany> list = tCompanyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean isExistLicense(String license) {
		TCompanyCondition condition = new TCompanyCondition();
		condition.setLicense(license);
		List<TCompany> list = tCompanyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public TCompany getByLicense(String license) {
		TCompanyCondition condition = new TCompanyCondition();
		condition.setLicense(license);
		List<TCompany> list = tCompanyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TCompany selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TCompany party = super.selectById(id);
		//	if (party != null) {
		//		doCache(party);
		//	}
		//	return party;
		//} else {
		//	return JSON.parseObject(cacheStr, TCompany.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TCompany model) {
		//int result = super.updateById(model);
		//if (result > 0) {
		//	delCache(model.getId());
		//}
		//return result;
		return super.updateById(model);
	}

	@Override
	public List<TCompany> getByAccountId(Integer accountId) {
		TCompanyCondition condition = new TCompanyCondition();
		condition.setAccountId(accountId);
		condition.setDeleteFlag(false);
		return tCompanyMapper.selectByCondition(condition);
	}

	@Override
	public TCompany getLatestByAccountId(Integer accountId) {
		List<TCompany> list = getByAccountId(accountId);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private void doCache(TCompany model) {
		try {
			redisCachemanager.hSet(tableKey, model.getId() + "", JSON.toJSONString(model));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, model.getId()));
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
