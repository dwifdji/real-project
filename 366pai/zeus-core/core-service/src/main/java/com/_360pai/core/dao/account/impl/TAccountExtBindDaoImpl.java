
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.AccountEnum;
import com._360pai.core.condition.account.TAccountExtBindCondition;
import com._360pai.core.dao.account.TAccountExtBindDao;
import com._360pai.core.dao.account.mapper.TAccountExtBindMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.facade.applet.vo.InviteRecord;
import com._360pai.core.model.account.TAccountExtBind;
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
public class TAccountExtBindDaoImpl extends AbstractDaoImpl<TAccountExtBind, TAccountExtBindCondition, BaseMapper<TAccountExtBind,TAccountExtBindCondition>> implements TAccountExtBindDao{
	
	@Resource
	private TAccountExtBindMapper tAccountExtBindMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_account_ext_bind";
	
	@Override
	protected BaseMapper<TAccountExtBind, TAccountExtBindCondition> daoSupport() {
		return tAccountExtBindMapper;
	}

	@Override
	protected TAccountExtBindCondition blankCondition() {
		return new TAccountExtBindCondition();
	}

	@Override
	public TAccountExtBind selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TAccountExtBind model = super.selectById(id);
		//	if (model != null) {
		//		doCache(model);
		//	}
		//	return model;
		//} else {
		//	return JSON.parseObject(cacheStr, TAccountExtBind.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TAccountExtBind model) {
		//int result = super.updateById(model);
		//if (result > 0) {
		//	delCache(model.getId());
		//}
		//return result;
		return super.updateById(model);
	}

	@Override
	public TAccountExtBind findAppletByOpenId(String openId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setExtUserId(openId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountExtBind findCalculatorByOpenId(String openId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.CALCULATOR.getKey());
		condition.setExtUserId(openId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo getInviteRecordListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<InviteRecord> list = tAccountExtBindMapper.getInviteRecordList(params);
		return new PageInfo<>(list);
	}

	@Override
	public TAccountExtBind findAppletByAccountId(Integer accountId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setAccountId(accountId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountExtBind findAppletByCurrentPartyId(Integer currentPartyId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setCurrentPartyId(currentPartyId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int inviteCount(String inviteType, Integer inviteCode) {
		return tAccountExtBindMapper.inviteCount(inviteType, inviteCode);
	}

	@Override
	public TAccountExtBind findMp360PaiBy(String unionId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.MP_360PAI.getKey());
		condition.setUnionId(unionId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountExtBind findMp360PaiByOpenId(String openId) {
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.MP_360PAI.getKey());
		condition.setExtUserId(openId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAccountExtBind findAppletBy(String unionId) {
		if (StringUtils.isBlank(unionId)) {
			return null;
		}
		TAccountExtBindCondition condition = new TAccountExtBindCondition();
		condition.setExtType(AccountEnum.ExtType.APPLET.getKey());
		condition.setUnionId(unionId);
		List<TAccountExtBind> list = tAccountExtBindMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private void doCache(TAccountExtBind model) {
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
