package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.AssetFieldGroupFacade;
import com._360pai.core.facade.asset.req.AssetFieldGroupReq;
import com._360pai.core.model.asset.AssetFieldGroup;
import com._360pai.core.service.asset.AssetFieldGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author zxiao
 * @Title: AssetFieldGroupProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 16:39
 */
@Component
@Service(version = "1.0.0")
public class AssetFieldGroupProvider implements AssetFieldGroupFacade {

    @Autowired
    private AssetFieldGroupService assetFieldGroupService;

    @Override
    public int updateAssetFieldGroup(AssetFieldGroupReq req) {
        AssetFieldGroup group = new AssetFieldGroup();
        BeanUtils.copyProperties(req, group);
        return assetFieldGroupService.updateAssetFieldGroup(group);
    }

    @Override
    public PageInfo selectAllAssetFieldGroupList(AssetFieldGroupReq req) {
        PageHelper.startPage(req.getPage(), req.getPerPage());
        List<AssetFieldGroup> assetFieldGroups = assetFieldGroupService.selectAllAssetFileGroupList();
        return new PageInfo<>(assetFieldGroups);
    }
}
