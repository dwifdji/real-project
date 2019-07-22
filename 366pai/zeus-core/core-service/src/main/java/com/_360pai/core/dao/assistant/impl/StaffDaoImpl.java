
package com._360pai.core.dao.assistant.impl;

import javax.annotation.Resource;

import com._360pai.arch.common.constant.RedisKeyConstant;
import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.core.redis.RedisCachemanager;
import com._360pai.core.exception.DaoException;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com._360pai.core.condition.assistant.StaffCondition;
import com._360pai.core.dao.assistant.mapper.StaffMapper;
import com._360pai.core.model.assistant.Staff;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.assistant.StaffDao;

import java.util.List;
import java.util.Map;

@Service
public class StaffDaoImpl extends AbstractDaoImpl<Staff, StaffCondition, BaseMapper<Staff,StaffCondition>> implements StaffDao{
	
	@Resource
	private StaffMapper staffMapper;
	@Resource
	private RedisCachemanager redisCachemanager;

	private String tableKey = RedisKeyConstant.TABLE + "staff";
	
	@Override
	protected BaseMapper<Staff, StaffCondition> daoSupport() {
		return staffMapper;
	}

	@Override
	protected StaffCondition blankCondition() {
		return new StaffCondition();
	}

	@Override
	public Staff getByMobile(String mobile) {
		StaffCondition condition = new StaffCondition();
		condition.setMobile(mobile);
		List<Staff> list = staffMapper.selectByCondition(condition);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Staff getByQq(String qq) {
		StaffCondition condition = new StaffCondition();
		condition.setQq(qq);
		List<Staff> list = staffMapper.selectByCondition(condition);
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
		List<Staff> list = staffMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public int deleteById(Integer id) {
		int result = staffMapper.deleteById(id);
		if (result > 0) {
			delCache(id);
		}
		return result;
	}

	@Override
	public Staff selectById(Integer id) {
		String cacheStr = (String) redisCachemanager.hGet(tableKey, id + "");
		if (StringUtils.isEmpty(cacheStr)) {
			Staff staff = super.selectById(id);
			if (staff != null) {
				doCache(staff);
			}
			return staff;
		} else {
			return JSON.parseObject(cacheStr, Staff.class);
		}
	}

	@Override
	public int updateById(Staff staff) {
		int result = super.updateById(staff);
		if (result > 0) {
			delCache(staff.getId());
		}
		return result;
	}

	@Override
	public String getName(Integer id) {
		if (id != null) {
			Staff staff = selectById(id);
			if (staff != null) {
				return staff.getName();
			}
		}
		return "";
	}

	private void doCache(Staff staff) {
		try {
			redisCachemanager.hSet(tableKey, staff.getId() + "", JSON.toJSONString(staff));
		} catch (Exception e) {
			e.printStackTrace();
			throw new DaoException(ApiCallResult.DBERROR, String.format("%s,%d", tableKey, staff.getId()));
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
