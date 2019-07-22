
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.condition.account.TPartyCondition;
import com._360pai.core.dao.account.TPartyDao;
import com._360pai.core.dao.account.mapper.TPartyMapper;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.account.TParty;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TPartyDaoImpl extends AbstractDaoImpl<TParty, TPartyCondition, BaseMapper<TParty,TPartyCondition>> implements TPartyDao{
	
	@Resource
	private TPartyMapper tPartyMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_party";

	@Override
	protected BaseMapper<TParty, TPartyCondition> daoSupport() {
		return tPartyMapper;
	}

	@Override
	protected TPartyCondition blankCondition() {
		return new TPartyCondition();
	}

	@Override
	public TParty selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TParty party = super.selectById(id);
		//	if (party != null) {
		//		doCache(party);
		//	}
		//	return party;
		//} else {
		//	return JSON.parseObject(cacheStr, TParty.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TParty party) {
		//int result = super.updateById(party);
		//if (result > 0) {
		//	delCache(party.getId());
		//}
		//return result;
		return super.updateById(party);
	}

	private void doCache(TParty party) {
		try {
			redisCachemanager.hSet(tableKey, party.getId() + "", JSON.toJSONString(party));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, party.getId()));
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
