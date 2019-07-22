package com._360pai.core.service.asset.impl;

import com._360pai.core.condition.asset.TAssetFieldCondition;
import com._360pai.core.dao.asset.TAssetFieldDao;
import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.model.asset.AssetField;
import com._360pai.core.model.asset.TAssetField;
import com._360pai.core.service.asset.TAssetFieldService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TAssetFieldServiceImpl implements TAssetFieldService {

    @Autowired
    private TAssetFieldDao tAssetFieldDao;


    @Override
    public int insert(TAssetField params) {
        return tAssetFieldDao.insert(params);
    }

    @Override
    public int updateAssetField(TAssetField params) {
        return tAssetFieldDao.updateById(params);
    }

    @Override
    public PageInfo searchAssetFields(TAssetFieldDto dto, int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<Map> list = tAssetFieldDao.searchAssetFields(dto);
        return new PageInfo<>(list);
    }

    @Override
    public Object findAssetFieldsByGroupId(Integer fieldGroupId) {
        TAssetFieldCondition condition = new TAssetFieldCondition();
        condition.setFieldGroupId(fieldGroupId);
        return tAssetFieldDao.selectList(condition);
    }
}