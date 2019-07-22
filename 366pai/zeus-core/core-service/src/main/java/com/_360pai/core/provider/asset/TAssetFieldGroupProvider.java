package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.TAssetFieldGroupFacade;
import com._360pai.core.facade.asset.req.TAssetFieldGroupReq;
import com._360pai.core.model.asset.TAssetFieldGroup;
import com._360pai.core.service.asset.TAssetFieldGroupService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: TAssetFieldGroupProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/4 15:40
 */
@Component
@Service(version = "1.0.0")
public class TAssetFieldGroupProvider implements TAssetFieldGroupFacade {

    @Resource
    private TAssetFieldGroupService tAssetFieldGroupService;

    @Override
    public int insertAssetFieldGroup(TAssetFieldGroupReq req) {
        return tAssetFieldGroupService.insertAssetFieldGroup(copyTAssetFieldGroup(req));
    }

    @Override
    public int updateAssetFieldGroup(TAssetFieldGroupReq req) {
        return tAssetFieldGroupService.updateAssetFieldGroup(copyTAssetFieldGroup(req));
    }

    @Override
    public PageInfo selectAllAssetFieldGroupList(TAssetFieldGroupReq req) {
        return tAssetFieldGroupService.selectAllAssetFieldGroupList(req.getPage(),req.getPerPage());
    }

    private TAssetFieldGroup copyTAssetFieldGroup(TAssetFieldGroupReq req) {
        TAssetFieldGroup tAssetFieldGroup = new TAssetFieldGroup();
        BeanUtils.copyProperties(req,tAssetFieldGroup);
        return tAssetFieldGroup;
    }
}
