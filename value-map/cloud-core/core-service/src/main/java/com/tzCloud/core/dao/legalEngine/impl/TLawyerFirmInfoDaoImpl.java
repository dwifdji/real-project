
package com.tzCloud.core.dao.legalEngine.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzCloud.core.condition.legalEngine.TLawyerFirmInfoCondition;
import com.tzCloud.core.dao.legalEngine.mapper.TLawyerFirmInfoMapper;
import com.tzCloud.core.model.legalEngine.TLawyerFirmInfo;
import com.tzCloud.arch.core.abs.BaseMapper;
import com.tzCloud.arch.core.abs.AbstractDaoImpl;
import com.tzCloud.core.dao.legalEngine.TLawyerFirmInfoDao;

import java.util.List;

@Service
public class TLawyerFirmInfoDaoImpl extends AbstractDaoImpl<TLawyerFirmInfo, TLawyerFirmInfoCondition, BaseMapper<TLawyerFirmInfo,TLawyerFirmInfoCondition>> implements TLawyerFirmInfoDao{
	
	@Resource
	private TLawyerFirmInfoMapper tLawyerFirmInfoMapper;
	
	@Override
	protected BaseMapper<TLawyerFirmInfo, TLawyerFirmInfoCondition> daoSupport() {
		return tLawyerFirmInfoMapper;
	}

	@Override
	protected TLawyerFirmInfoCondition blankCondition() {
		return new TLawyerFirmInfoCondition();
	}

	@Override
	public List<TLawyerFirmInfo> selectByFirmNameLike(String name) {
		return tLawyerFirmInfoMapper.selectByFirmNameLike(name);
	}
}
