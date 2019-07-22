
package com.winback.core.dao._case.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.winback.core.condition._case.TCaseAssetCondition;
import com.winback.core.dao._case.mapper.TCaseAssetMapper;
import com.winback.core.model._case.TCaseAsset;
import com.winback.arch.core.abs.BaseMapper;
import com.winback.arch.core.abs.AbstractDaoImpl;
import com.winback.core.dao._case.TCaseAssetDao;

@Service
public class TCaseAssetDaoImpl extends AbstractDaoImpl<TCaseAsset, TCaseAssetCondition, BaseMapper<TCaseAsset,TCaseAssetCondition>> implements TCaseAssetDao{
	
	@Resource
	private TCaseAssetMapper tCaseAssetMapper;
	
	@Override
	protected BaseMapper<TCaseAsset, TCaseAssetCondition> daoSupport() {
		return tCaseAssetMapper;
	}

	@Override
	protected TCaseAssetCondition blankCondition() {
		return new TCaseAssetCondition();
	}

}
