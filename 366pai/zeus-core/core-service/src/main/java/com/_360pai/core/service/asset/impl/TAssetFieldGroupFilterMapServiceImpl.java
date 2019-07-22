package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.TAssetFieldGroupFilterMapDao;
import com._360pai.core.service.asset.TAssetFieldGroupFilterMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAssetFieldGroupFilterMapServiceImpl	implements TAssetFieldGroupFilterMapService {

	@Autowired
	private TAssetFieldGroupFilterMapDao tAssetFieldGroupFilterMapDao;


}