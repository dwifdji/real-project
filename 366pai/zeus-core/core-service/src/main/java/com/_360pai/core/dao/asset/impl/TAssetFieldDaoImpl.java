
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldCondition;
import com._360pai.core.dao.asset.TAssetFieldDao;
import com._360pai.core.dao.asset.mapper.TAssetFieldMapper;
import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.model.asset.TAssetField;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAssetFieldDaoImpl extends AbstractDaoImpl<TAssetField, TAssetFieldCondition, BaseMapper<TAssetField,TAssetFieldCondition>> implements TAssetFieldDao {
	
	@Resource
	private TAssetFieldMapper tAssetFieldMapper;
	
	@Override
	protected BaseMapper<TAssetField, TAssetFieldCondition> daoSupport() {
		return tAssetFieldMapper;
	}

	@Override
	protected TAssetFieldCondition blankCondition() {
		return new TAssetFieldCondition();
	}

    @Override
    public List<Map> searchAssetFields(TAssetFieldDto dto) {
		return 	tAssetFieldMapper.searchAssetFields(dto);
    }

    @Override
    public TAssetField findUnit(String key) {
        return tAssetFieldMapper.findUnit(key);

    }
}
