package com._360pai.core.provider.asset;

import com._360pai.core.condition.asset.AssetCategoryGroupCondition;
import com._360pai.core.dto.AssetCategoryGroupDto;
import com._360pai.core.facade.asset.AssetCategoryGroupFacade;
import com._360pai.core.facade.asset.req.AssetCategoryGroupReq;
import com._360pai.core.model.asset.AssetCategoryGroup;
import com._360pai.core.service.asset.AssetCategoryGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetCategoryGroupProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 9:52
 */
@Component
@Service(version = "1.0.0")
public class AssetCategoryGroupProvider implements AssetCategoryGroupFacade {

    @Autowired
    AssetCategoryGroupService assetCategoryGroupService;

    @Override
    public Object getAllCateGoryGroupList(AssetCategoryGroupReq req) {
        AssetCategoryGroupCondition categoryGroupCondition = new AssetCategoryGroupCondition();
        categoryGroupCondition.setEnabled(req.getEnabled());
        if (req.getBusinessType() != null) {
            if (req.getBusinessType() == 1) {
                categoryGroupCondition.setBusinessType(AssetCategoryGroup.AUCTION);
            } else {
                categoryGroupCondition.setBusinessType(AssetCategoryGroup.ENROLLING);
            }
        }
        return assetCategoryGroupService.selectAllAssetCategoryGroup(categoryGroupCondition);
    }

    @Override
    public PageInfo getCateGoryGroupList(AssetCategoryGroupReq req) {
        return assetCategoryGroupService.selectAssetCategoryGroup(req.getPage(), req.getPerPage());
    }

    @Override
    public Object addCateGoryGroup(AssetCategoryGroupReq req) {
        AssetCategoryGroup group = new AssetCategoryGroup();
        BeanUtils.copyProperties(req, group);
        if (req.getBusinessType() == 1) {
            group.setBusinessType(AssetCategoryGroup.AUCTION);
        } else {
            group.setBusinessType(AssetCategoryGroup.ENROLLING);
        }
        if (req.getDealMode() == 1) {
            group.setDealMode(AssetCategoryGroup.SELL);
        } else {
            group.setDealMode(AssetCategoryGroup.SERVICE);
        }
        return assetCategoryGroupService.insertAssetCateGoryGroup(group);
    }

    @Override
    public Object updateCateGoryGroup(AssetCategoryGroupReq req) {
        AssetCategoryGroup group = new AssetCategoryGroup();
        BeanUtils.copyProperties(req, group);
        if (req.getBusinessType() == 1) {
            group.setBusinessType(AssetCategoryGroup.AUCTION);
        } else {
            group.setBusinessType(AssetCategoryGroup.ENROLLING);
        }
        if (req.getDealMode() == 1) {
            group.setDealMode(AssetCategoryGroup.SELL);
        } else {
            group.setDealMode(AssetCategoryGroup.SERVICE);
        }
        return assetCategoryGroupService.updateAssetCategoryGroup(group);
    }

    @Override
    public Object selectCateGoryGroupFields(AssetCategoryGroupReq req) {
        return assetCategoryGroupService.selectGroupFields(req.getGroupId());
    }

    @Override
    public void editCateGoryGroupFields(AssetCategoryGroupReq req) {
        AssetCategoryGroupDto dto = new AssetCategoryGroupDto();
        BeanUtils.copyProperties(req, dto);
        assetCategoryGroupService.updateGroupFields(dto);
    }

    @Override
    public PageInfo searchResultByGroupId(AssetCategoryGroupReq req) {
        return assetCategoryGroupService.searchResult(req.getGroupId(), req.getPage(), req.getPerPage());
    }

    @Override
    public PageInfo searchCategoriesByGroupId(AssetCategoryGroupReq req) {
        return assetCategoryGroupService.searchCategoriesByGroupId(req.getGroupId(), req.getPage(), req.getPerPage());
    }

    @Override
    public void bind(AssetCategoryGroupReq param) {
        assetCategoryGroupService.bind(param.getCurrentCategoryId(), param.getResultId());
    }
}
