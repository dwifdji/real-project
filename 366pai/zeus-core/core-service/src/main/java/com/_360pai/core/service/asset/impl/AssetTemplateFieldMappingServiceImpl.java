package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.AssetTemplateFieldMappingDao;
import com._360pai.core.service.asset.AssetTemplateFieldMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetTemplateFieldMappingServiceImpl	implements AssetTemplateFieldMappingService {

	@Autowired
	private AssetTemplateFieldMappingDao assetTemplateFieldMappingDao;


}