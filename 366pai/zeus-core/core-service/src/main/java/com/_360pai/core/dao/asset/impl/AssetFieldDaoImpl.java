
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.AssetFieldCondition;
import com._360pai.core.dao.asset.AssetFieldDao;
import com._360pai.core.dao.asset.mapper.AssetFieldMapper;
import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.model.asset.AssetField;
import com._360pai.core.vo.asset.AssetFieldVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AssetFieldDaoImpl extends AbstractDaoImpl<AssetField, AssetFieldCondition, BaseMapper<AssetField, AssetFieldCondition>> implements AssetFieldDao {

	@Resource
	private AssetFieldMapper assetFieldMapper;

	@Override
	protected BaseMapper<AssetField, AssetFieldCondition> daoSupport() {
		return assetFieldMapper;
	}

	@Override
	protected AssetFieldCondition blankCondition() {
		return new AssetFieldCondition();
	}

	@Override
	public List<AssetFieldVo> selectAssetFieldAndGroup(AssetFieldDto params) {
		return assetFieldMapper.selectAssetFieldAndGroup(params);
	}
}
