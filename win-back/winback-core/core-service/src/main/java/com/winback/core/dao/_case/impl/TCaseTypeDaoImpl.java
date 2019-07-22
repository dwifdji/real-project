
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import com.winback.core.vo.operate.CaseTypeVO;
import com.winback.core.vo.operate.QuickReleaseVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseTypeCondition;
import com.winback.core.dao._case.mapper.TCaseTypeMapper;
import com.winback.core.model._case.TCaseType;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseTypeDao;

import java.util.List;

@Service
public class TCaseTypeDaoImpl extends AbstractDaoImpl<TCaseType, TCaseTypeCondition, BaseMapper<TCaseType,TCaseTypeCondition>> implements TCaseTypeDao{
	
	@Resource
	private TCaseTypeMapper tCaseTypeMapper;
	
	@Override
	protected BaseMapper<TCaseType, TCaseTypeCondition> daoSupport() {
		return tCaseTypeMapper;
	}

	@Override
	protected TCaseTypeCondition blankCondition() {
		return new TCaseTypeCondition();
	}

	@Override
	public String getName(Integer id) {
		if (id == null) {
			return "";
		}
		TCaseType model = super.selectById(id);
		if (model != null) {
			return model.getName();
		}
		return "";
	}

	@Override
	public List<CaseTypeVO> getAllCaseType() {

		return tCaseTypeMapper.getAllCaseType();
	}

}
