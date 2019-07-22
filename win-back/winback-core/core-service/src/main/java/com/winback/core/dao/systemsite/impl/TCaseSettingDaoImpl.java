
package com.winback.core.dao.systemsite.impl;

import javax.annotation.Resource;

import com.winback.core.vo.systemsite.CaseSiteVO;
import org.springframework.stereotype.Service;

import com.winback.core.condition.systemsite.TCaseSettingCondition;
import com.winback.core.dao.systemsite.mapper.TCaseSettingMapper;
import com.winback.core.model.systemsite.TCaseSetting;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao.systemsite.TCaseSettingDao;

import java.util.List;

@Service
public class TCaseSettingDaoImpl extends AbstractDaoImpl<TCaseSetting, TCaseSettingCondition, BaseMapper<TCaseSetting,TCaseSettingCondition>> implements TCaseSettingDao{
	
	@Resource
	private TCaseSettingMapper tCaseSettingMapper;
	
	@Override
	protected BaseMapper<TCaseSetting, TCaseSettingCondition> daoSupport() {
		return tCaseSettingMapper;
	}

	@Override
	protected TCaseSettingCondition blankCondition() {
		return new TCaseSettingCondition();
	}

	@Override
	public List<CaseSiteVO> getCaseSettingList(String type) {

		return tCaseSettingMapper.getCaseSettingList(type);
	}
}
