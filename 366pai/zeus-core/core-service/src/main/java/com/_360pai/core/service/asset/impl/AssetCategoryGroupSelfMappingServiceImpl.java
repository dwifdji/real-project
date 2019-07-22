package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.AssetCategoryGroupSelfMappingDao;
import com._360pai.core.service.asset.AssetCategoryGroupSelfMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetCategoryGroupSelfMappingServiceImpl	implements AssetCategoryGroupSelfMappingService {

	@Autowired
	private AssetCategoryGroupSelfMappingDao assetCategoryGroupSelfMappingDao;


}