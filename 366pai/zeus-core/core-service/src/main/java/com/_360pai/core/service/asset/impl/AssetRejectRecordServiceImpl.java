package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.AssetRejectRecordDao;
import com._360pai.core.service.asset.AssetRejectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetRejectRecordServiceImpl	implements AssetRejectRecordService {

	@Autowired
	private AssetRejectRecordDao assetRejectRecordDao;
}