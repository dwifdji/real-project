
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.account.TUserCondition;
import com._360pai.core.dao.account.TUserDao;
import com._360pai.core.dao.account.mapper.TUserMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.account.TUser;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TUserDaoImpl extends AbstractDaoImpl<TUser, TUserCondition, BaseMapper<TUser,TUserCondition>> implements TUserDao{
	
	@Resource
	private TUserMapper tUserMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_user";

	@Override
	protected BaseMapper<TUser, TUserCondition> daoSupport() {
		return tUserMapper;
	}

	@Override
	protected TUserCondition blankCondition() {
		return new TUserCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TUser> list = tUserMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TUser selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TUser party = super.selectById(id);
		//	if (party != null) {
		//		doCache(party);
		//	}
		//	return party;
		//} else {
		//	return JSON.parseObject(cacheStr, TUser.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TUser party) {
		//int result = super.updateById(party);
		//if (result > 0) {
		//	delCache(party.getId());
		//}
		//return result;
		return super.updateById(party);
	}

	@Override
	public TUser getByAccountId(Integer accountId) {
		TUserCondition condition = new TUserCondition();
		condition.setAccountId(accountId);
		List<TUser> list = tUserMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TUser getByCertificateNumber(String certificateNumber) {
		TUserCondition condition = new TUserCondition();
		condition.setCertificateNumber(certificateNumber);
		List<TUser> list = tUserMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TUser getByName(String name) {
		TUserCondition condition = new TUserCondition();
		condition.setName(name);
		List<TUser> list = tUserMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TUser getByMobile(String mobile) {
		TUserCondition condition = new TUserCondition();
		condition.setMobile(mobile);
		List<TUser> list = tUserMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getListByPage(int page, int perPage) {
		PageHelper.startPage(page, perPage);
		List<TUser> list = tUserMapper.selectAll();
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistCertificateNumber(String certificateNumber) {
		TUser user = getByCertificateNumber(certificateNumber);
		return user != null;
	}

	private void doCache(TUser model) {
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
