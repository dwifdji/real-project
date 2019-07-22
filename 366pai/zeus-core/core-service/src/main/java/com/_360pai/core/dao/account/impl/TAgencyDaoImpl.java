
package com._360pai.core.dao.account.impl;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.PinYin4jUtils;
import com._360pai.arch.common.utils.RandomNumberGenerator;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.common.constants.AgencyEnum;
import com._360pai.core.condition.account.TAgencyCondition;
import com._360pai.core.dao.account.TAgencyDao;
import com._360pai.core.dao.account.mapper.TAgencyMapper;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.exception.DaoException;
import com._360pai.core.model.account.TAgency;
import com._360pai.core.model.account.TAgencyApplyRecord;
import com._360pai.core.utils.BusinessUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TAgencyDaoImpl extends AbstractDaoImpl<TAgency, TAgencyCondition, BaseMapper<TAgency,TAgencyCondition>> implements TAgencyDao{
	
	@Resource
	private TAgencyMapper tAgencyMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "t_agency";
	
	@Override
	protected BaseMapper<TAgency, TAgencyCondition> daoSupport() {
		return tAgencyMapper;
	}

	@Override
	protected TAgencyCondition blankCondition() {
		return new TAgencyCondition();
	}

	@Override
	public PageInfo getListByPage(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TAgency> list = tAgencyMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistLicense(String license) {
		TAgencyCondition condition = new TAgencyCondition();
		condition.setLicense(license);
		List<TAgency> list = tAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isExistCode(String code) {
		TAgencyCondition condition = new TAgencyCondition();
		condition.setCode(code);
		List<TAgency> list = tAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public TAgency createFromApply(TAgencyApplyRecord apply) {
		TAgency agency = new TAgency();
		BeanUtils.copyProperties(apply, agency);
		agency.setPinyin(PinYin4jUtils.hanziToPinyinRemoveBrackets(agency.getName()));
		agency.setTrusteePhone(agency.getMobile());
		agency.setId(null);
		agency.setWebsiteStatus(AgencyEnum.AgencyPortalStatus.ONLINE.getKey());
		agency.setCreateTime(new Date());
		agency.setUpdateTime(new Date());
		int result = tAgencyMapper.insert(agency);
		if (result == 0) {
			throw new BusinessException(ApiCallResult.FAILURE);
		}
		return agency;
	}

	@Override
	public TAgency selectById(Integer id) {
		//String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		//if (StringUtils.isEmpty(cacheStr)) {
		//	TAgency agency = super.selectById(id);
		//	if (agency != null) {
		//		doCache(agency);
		//	}
		//	return agency;
		//} else {
		//	return JSON.parseObject(cacheStr, TAgency.class);
		//}
		return super.selectById(id);
	}

	@Override
	public int updateById(TAgency agency) {
		//int result = super.updateById(agency);
		//if (result > 0) {
		//	delCache(agency.getId());
		//}
		//return result;
		return super.updateById(agency);
	}

	@Override
	public boolean isExistMobile(String mobile) {
		TAgencyCondition condition = new TAgencyCondition();
		condition.setMobile(mobile);
		List<TAgency> list = tAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public TAgency getByCode(String code) {
		TAgencyCondition condition = new TAgencyCondition();
		condition.setCode(code);
		List<TAgency> list = tAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public TAgency getByLicense(String license) {
		TAgencyCondition condition = new TAgencyCondition();
		condition.setLicense(license);
		List<TAgency> list = tAgencyMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getName(Integer id) {
		if (id == null) {
			return "";
		}
		TAgency agency = super.selectById(id);
		if (agency == null) {
			return "";
		}
		return agency.getName();
	}

	@Override
	public PageInfo<Map<String, Object>> searchOnlineList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<Map<String, Object>> list = tAgencyMapper.searchOnlineList(params);
		return new PageInfo<>(list);
	}

	@Override
	public List<Map<String, Object>> getOnlineList(Map<String, Object> params) {
		return tAgencyMapper.getOnlineList(params);
	}


	private void doCache(TAgency agency) {
		try {
			redisCachemanager.hSet(tableKey, agency.getId() + "", JSON.toJSONString(agency));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, agency.getId()));
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
