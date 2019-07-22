
package com.winback.core.dao.account.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.condition.account.TLawFirmCondition;
import com.winback.core.dao.account.TLawFirmDao;
import com.winback.core.dao.account.mapper.TLawFirmMapper;
import com.winback.core.model.account.TLawFirm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TLawFirmDaoImpl extends AbstractDaoImpl<TLawFirm, TLawFirmCondition, BaseMapper<TLawFirm,TLawFirmCondition>> implements TLawFirmDao{
	
	@Resource
	private TLawFirmMapper tLawFirmMapper;
	
	@Override
	protected BaseMapper<TLawFirm, TLawFirmCondition> daoSupport() {
		return tLawFirmMapper;
	}

	@Override
	protected TLawFirmCondition blankCondition() {
		return new TLawFirmCondition();
	}

	@Override
	public PageInfo<TLawFirm> getList(int page, int perPage, Map<String, Object> params, String orderBy) {
		PageHelper.startPage(page, perPage);
		if (StringUtils.isNotBlank(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
		List<TLawFirm> list = tLawFirmMapper.getList(params);
		return new PageInfo<>(list);
	}

	@Override
	public boolean isExistName(String name) {
		TLawFirmCondition condition = new TLawFirmCondition();
		condition.setName(name);
		List<TLawFirm> list = tLawFirmMapper.selectByCondition(condition);
		return list.size() > 0;
	}

	@Override
	public boolean isExistLicenseNumber(String licenseNumber) {
		TLawFirmCondition condition = new TLawFirmCondition();
		condition.setLicenseNumber(licenseNumber);
		List<TLawFirm> list = tLawFirmMapper.selectByCondition(condition);
		return list.size() > 0;
	}
}
