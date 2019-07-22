
package com._360pai.core.dao.asset.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com._360pai.core.condition.asset.AssetRejectRecordCondition;
import com._360pai.core.dao.asset.mapper.AssetRejectRecordMapper;
import com._360pai.core.model.asset.AssetRejectRecord;
import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.arch.core.abs.AbstractDaoImpl;
import com._360pai.core.dao.asset.AssetRejectRecordDao;

import java.util.Date;

@Service
public class AssetRejectRecordDaoImpl extends AbstractDaoImpl<AssetRejectRecord, AssetRejectRecordCondition, BaseMapper<AssetRejectRecord,AssetRejectRecordCondition>> implements AssetRejectRecordDao{
	
	@Resource
	private AssetRejectRecordMapper assetRejectRecordMapper;
	
	@Override
	protected BaseMapper<AssetRejectRecord, AssetRejectRecordCondition> daoSupport() {
		return assetRejectRecordMapper;
	}

	@Override
	protected AssetRejectRecordCondition blankCondition() {
		return new AssetRejectRecordCondition();
	}

	@Override
	public int save(Integer assetId, String reason) {
		AssetRejectRecord model = new AssetRejectRecord();
		model.setAssetId(assetId);
		model.setReason(reason);
		model.setCreatedAt(new Date());
		return assetRejectRecordMapper.insert(model);
	}
}
