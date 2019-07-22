package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.condition.asset.AssetFieldCondition;
import com._360pai.core.dao.asset.AssetFieldDao;
import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.AssetField;
import com._360pai.core.service.asset.AssetFieldService;
import com._360pai.core.vo.asset.AssetFieldVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetFieldServiceImpl implements AssetFieldService {

    @Autowired
    private AssetFieldDao assetFieldDao;


    @Override
    public List<AssetField> selectAssetFieldList() {
        return assetFieldDao.selectAll();
    }


    @Override
    public PageInfo selectAssetFieldAndGroup(AssetFieldDto params, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetFieldVo> assetFieldVos = assetFieldDao.selectAssetFieldAndGroup(params);
        return new PageInfo<>(assetFieldVos);
    }

    @Override
    public int insertAssetField(AssetField params) {
        if (StringUtils.isBlank(params.getLabel())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "中文名称不能为空");
        }
        AssetField label = findByName(params);
        if (null != label) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "字段名不能重复");
        }
        return assetFieldDao.insert(params);
    }

    @Override
    public int updateAssetField(AssetField params) {
        AssetField assetField = findById(params);
        if (null == assetField) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的描述字段不存在");
        }
        if (StringUtils.isBlank(params.getLabel())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "中文名称不能为空");
        }
        return assetFieldDao.updateById(params);
    }


    public AssetField findByName(AssetField params) {
        AssetFieldCondition condition = new AssetFieldCondition();
        condition.setName(params.getName());
        return assetFieldDao.selectFirst(condition);
    }

    public AssetField findById(AssetField params) {
        AssetFieldCondition condition = new AssetFieldCondition();
        condition.setId(params.getId());
        return assetFieldDao.selectFirst(condition);
    }

    @Override
    public AssetField getByName(String name) {
        AssetFieldCondition condition = new AssetFieldCondition();
        condition.setName(name);
        return assetFieldDao.selectFirst(condition);
    }
}