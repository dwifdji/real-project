
package com._360pai.core.dao.asset.impl;

import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetTemplateFieldGroupMapCondition;
import com._360pai.core.dao.asset.TAssetTemplateFieldGroupMapDao;
import com._360pai.core.dao.asset.mapper.TAssetTemplateFieldGroupMapMapper;
import com._360pai.core.model.asset.TAssetTemplateFieldGroupMap;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class TAssetTemplateFieldGroupMapDaoImpl extends AbstractDaoImpl<TAssetTemplateFieldGroupMap, TAssetTemplateFieldGroupMapCondition, BaseMapper<TAssetTemplateFieldGroupMap,TAssetTemplateFieldGroupMapCondition>> implements TAssetTemplateFieldGroupMapDao {
	
	@Resource
	private TAssetTemplateFieldGroupMapMapper tAssetTemplateFieldGroupMapMapper;
	
	@Override
	protected BaseMapper<TAssetTemplateFieldGroupMap, TAssetTemplateFieldGroupMapCondition> daoSupport() {
		return tAssetTemplateFieldGroupMapMapper;
	}

	@Override
	protected TAssetTemplateFieldGroupMapCondition blankCondition() {
		return new TAssetTemplateFieldGroupMapCondition();
	}

    @Override
    public List<Map> getTemplateGroup(Integer paramsId) {
        return tAssetTemplateFieldGroupMapMapper.getTemplateGroup(paramsId);
    }
}
