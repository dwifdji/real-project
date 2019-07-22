
package com.winback.core.dao.operate.impl;

import javax.annotation.Resource;

import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.core.dto.operate.OperateIconDto;
import com.winback.core.vo.operate.HomeContractCategoryVO;
import com.winback.core.vo.operate.HomeIconCategoryVO;
import com.winback.core.vo.operate.OperateIconListVO;
import com.winback.core.vo.operate.QuickReleaseVO;
import org.springframework.stereotype.Service;


import com.winback.core.condition.operate.TOperateIconInfoCondition;
import com.winback.core.dao.operate.mapper.TOperateIconInfoMapper;
import com.winback.core.model.operate.TOperateIconInfo;
import com.winback.core.dao.operate.TOperateIconInfoDao;

import java.util.List;

@Service
public class TOperateIconInfoDaoImpl extends AbstractDaoImpl<TOperateIconInfo, TOperateIconInfoCondition, BaseMapper<TOperateIconInfo,TOperateIconInfoCondition>> implements TOperateIconInfoDao{
	
	@Resource
	private TOperateIconInfoMapper tOperateIconInfoMapper;
	
	@Override
	protected BaseMapper<TOperateIconInfo, TOperateIconInfoCondition> daoSupport() {
		return tOperateIconInfoMapper;
	}

	@Override
	protected TOperateIconInfoCondition blankCondition() {
		return new TOperateIconInfoCondition();
	}

	@Override
	public List<OperateIconListVO> getReleaseAreaList(OperateIconDto params) {
		return tOperateIconInfoMapper.getReleaseAreaList(params);
	}

	@Override
	public List<HomeIconCategoryVO> getQualityCaseIcons(Integer limitSize) {
		return tOperateIconInfoMapper.getQualityCaseIcons(limitSize);
	}

	@Override
	public List<HomeContractCategoryVO> getContractModelIcons(Integer limitSize) {
		return tOperateIconInfoMapper.getContractModelIcons(limitSize);
	}

	@Override
	public List<QuickReleaseVO> getCaseTypeList(Integer limitSize) {

		return tOperateIconInfoMapper.getCaseTypeList(limitSize);
	}


}
