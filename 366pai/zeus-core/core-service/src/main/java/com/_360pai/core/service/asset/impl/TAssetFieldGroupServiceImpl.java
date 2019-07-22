package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.TAssetFieldGroupCondition;
import com._360pai.core.dao.asset.TAssetFieldGroupDao;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.TAssetFieldGroup;
import com._360pai.core.service.asset.TAssetFieldGroupService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TAssetFieldGroupServiceImpl implements TAssetFieldGroupService {

    @Autowired
    private TAssetFieldGroupDao tAssetFieldGroupDao;


    @Override
    public int updateAssetFieldGroup(TAssetFieldGroup params) {
        TAssetFieldGroup byId = findById(params);
        if (byId == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改不存在");
        }
        return tAssetFieldGroupDao.updateById(params);
    }

    @Override
    public PageInfo selectAllAssetFieldGroupList(int page, int perPage) {
        PageHelper.startPage(page, perPage);
        List<TAssetFieldGroup> tAssetFieldGroups = tAssetFieldGroupDao.selectAll();
        return new PageInfo<>(tAssetFieldGroups);
    }

    @Override
    public int insertAssetFieldGroup(TAssetFieldGroup params) {
        TAssetFieldGroup byName = findByName(params);
        if (byName != null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "名称已存在");
        }
        return tAssetFieldGroupDao.insert(params);
    }

    private TAssetFieldGroup findById(TAssetFieldGroup params) {
        TAssetFieldGroupCondition condition = new TAssetFieldGroupCondition();
        condition.setId(params.getId());
        return tAssetFieldGroupDao.selectOneResult(condition);
    }

    private TAssetFieldGroup findByName(TAssetFieldGroup params) {
        TAssetFieldGroupCondition condition = new TAssetFieldGroupCondition();
        condition.setName(params.getName());
        return tAssetFieldGroupDao.selectOneResult(condition);
    }
}