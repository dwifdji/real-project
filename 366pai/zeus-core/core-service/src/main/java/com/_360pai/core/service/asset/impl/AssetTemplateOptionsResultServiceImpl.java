package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.AssetTemplateOptionsResultDao;
import com._360pai.core.service.asset.AssetTemplateOptionsResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssetTemplateOptionsResultServiceImpl	implements AssetTemplateOptionsResultService {

	@Autowired
	private AssetTemplateOptionsResultDao assetTemplateOptionsResultDao;


}