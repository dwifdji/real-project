package com._360pai.core.service.asset.impl;

import com._360pai.core.dao.asset.TAssetFieldItemDao;
import com._360pai.core.model.asset.TAssetFieldItem;
import com._360pai.core.service.asset.TAssetFieldItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TAssetFieldItemServiceImpl implements TAssetFieldItemService {

    @Autowired
    private TAssetFieldItemDao tAssetFieldItemDao;

    @Override
    public Object getTemplateGroupField(TAssetFieldItem tAssetFieldItem) {
        return tAssetFieldItemDao.findFields(tAssetFieldItem.getTemplateId(), tAssetFieldItem.getFieldGroupId(), null, null,null);
    }

    @Override
    public int deleteTemplateGroupField(Integer id) {
        return tAssetFieldItemDao.deleteTemplateGroupField(id);
    }
}