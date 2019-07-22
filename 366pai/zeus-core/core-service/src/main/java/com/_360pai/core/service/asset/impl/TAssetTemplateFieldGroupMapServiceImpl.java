package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.TAssetTemplateFieldGroupMapDao;
import com._360pai.core.service.asset.TAssetTemplateFieldGroupMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAssetTemplateFieldGroupMapServiceImpl	implements TAssetTemplateFieldGroupMapService {

	@Autowired
	private TAssetTemplateFieldGroupMapDao tAssetTemplateFieldGroupMapDao;


}