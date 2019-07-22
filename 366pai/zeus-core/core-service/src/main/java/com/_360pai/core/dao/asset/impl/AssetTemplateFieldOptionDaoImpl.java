
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetTemplateFieldOptionCondition;
import com._360pai.core.dao.asset.mapper.AssetTemplateFieldOptionMapper;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetTemplateFieldOptionDao;

import java.util.List;

@Service
public class AssetTemplateFieldOptionDaoImpl extends AbstractDaoImpl<AssetTemplateFieldOption, AssetTemplateFieldOptionCondition, BaseMapper<AssetTemplateFieldOption,AssetTemplateFieldOptionCondition>> implements AssetTemplateFieldOptionDao{
	
	@Resource
	private AssetTemplateFieldOptionMapper assetTemplateFieldOptionMapper;
	
	@Override
	protected BaseMapper<AssetTemplateFieldOption, AssetTemplateFieldOptionCondition> daoSupport() {
		return assetTemplateFieldOptionMapper;
	}

	@Override
	protected AssetTemplateFieldOptionCondition blankCondition() {
		return new AssetTemplateFieldOptionCondition();
	}

	@Override
	public List<Integer> selectIdsForFieldId(Integer fieldId) {
		return assetTemplateFieldOptionMapper.selectIdsForFieldId(fieldId);
	}

    @Override
    public int deleteAssetTemplateFieldOption(Integer id) {
		return assetTemplateFieldOptionMapper.deleteAssetTemplateFieldOption(id);
    }
}
