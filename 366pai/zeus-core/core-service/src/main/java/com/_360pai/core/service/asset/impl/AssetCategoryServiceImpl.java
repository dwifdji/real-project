package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.core.common.constants.AssetEnum;
import com._360pai.core.condition.asset.AssetCategoryCondition;
import com._360pai.core.condition.asset.AssetFieldCondition;
import com._360pai.core.condition.asset.AssetFieldItemCondition;
import com._360pai.core.condition.asset.TAssetCategoryCondition;
import com._360pai.core.dao.asset.*;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.*;
import com._360pai.core.service.asset.AssetCategoryService;
import com._360pai.core.vo.asset.AssetFieldGroupVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述:
 * @author : whisky_vip
 * @date : 2018/8/17 10:22
 */
@Service
public class AssetCategoryServiceImpl implements AssetCategoryService {

    @Autowired
    private AssetCategoryDao assetCategoryDao;
    @Autowired
    private TAssetCategoryDao tAssetCategoryDao;

    @Override
    public AssetCategory getById(Integer categoryId) {
        AssetCategoryCondition condition = new AssetCategoryCondition();
        condition.setId(categoryId);
        return assetCategoryDao.selectFirst(condition);
    }

    @Autowired
    private AssetFieldGroupDao assetFieldGroupDao;
    @Autowired
    private AssetFieldDao assetFieldDao;
    @Autowired
    private AssetFieldItemDao assetFieldItemDao;

    @Override
    public PageInfo findAssetCategoryList(int page, int prePage) {
        PageHelper.startPage(page, prePage);
        List<AssetCategory> assetCategories = assetCategoryDao.selectAll();
        return new PageInfo<>(assetCategories);
    }

    @Override
    public int insert(AssetCategory req) {
        AssetCategory category = findByName(req);
        if (null != category) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "该模板已存在");
        }
        return assetCategoryDao.insert(req);
    }

    @Override
    public int updateCategory(AssetCategory req) {
        if (StringUtils.isBlank(req.getName())) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "模板名称不能为空");
        }
        AssetCategory byId = findById(req);
        if (null == byId) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "模板不存在");
        }
        return assetCategoryDao.updateById(req);
    }

    @Override
    public Object getFieldItems(Integer categoryId) {
        List<AssetFieldGroup> assetFieldGroups = assetFieldGroupDao.selectAll();
        List<AssetFieldGroupVo> vos = new ArrayList<>();
        for (AssetFieldGroup group : assetFieldGroups) {
            AssetFieldGroupVo vo = new AssetFieldGroupVo();
            AssetFieldCondition condition = new AssetFieldCondition();
            condition.setGroupId(group.getId());
            List<AssetField> assetFields = assetFieldDao.selectList(condition);

            AssetFieldItemCondition itemCondition = new AssetFieldItemCondition();
            itemCondition.setCategoryId(categoryId);
            itemCondition.setGroupId(group.getId());
            List<AssetFieldItem> assetFieldItems = assetFieldItemDao.selectList(itemCondition);

            vo.setId(group.getId());
            vo.setName(group.getName());
            vo.setOrderNum(group.getOrderNum());
            vo.setFields(assetFields);
            vo.setItems(assetFieldItems);
            vos.add(vo);
        }
        return vos;
    }

    @Override
    public List<TAssetCategory> getAuctionAssetCategoryList() {
        TAssetCategoryCondition condition = new TAssetCategoryCondition();
        condition.setBusinessType(AssetEnum.CategoryBusinessType.AUCTION.getKey());
        condition.setEnabled(true);
        return tAssetCategoryDao.selectList(condition);
    }

    public AssetCategory findByName(AssetCategory params) {
        AssetCategoryCondition categoryCondition = new AssetCategoryCondition();
        categoryCondition.setName(params.getName());
        categoryCondition.setGroupId(params.getGroupId());
        return assetCategoryDao.selectFirst(categoryCondition);
    }

    public AssetCategory findById(AssetCategory params) {
        AssetCategoryCondition categoryCondition = new AssetCategoryCondition();
        categoryCondition.setId(params.getId());
        return assetCategoryDao.selectFirst(categoryCondition);
    }
}
