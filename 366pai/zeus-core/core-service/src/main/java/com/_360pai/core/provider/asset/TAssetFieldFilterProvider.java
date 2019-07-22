package com._360pai.core.provider.asset;

import com._360pai.core.facade.asset.TAssetFieldFilterFacade;
import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionItemReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterOptionReq;
import com._360pai.core.facade.asset.req.TAssetFieldFilterReq;
import com._360pai.core.model.asset.TAssetFieldFilter;
import com._360pai.core.model.asset.TAssetFieldFilterOption;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;
import com._360pai.core.service.asset.TAssetFieldFilterOptionItemService;
import com._360pai.core.service.asset.TAssetFieldFilterOptionService;
import com._360pai.core.service.asset.TAssetFieldFilterService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zxiao
 * @Title: TAssetFieldFilterProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/3 18:44
 */
@Component
@Service(version = "1.0.0")
public class TAssetFieldFilterProvider implements TAssetFieldFilterFacade {

    @Resource
    private TAssetFieldFilterService assetFieldFilterService;
    @Resource
    private TAssetFieldFilterOptionService assetFieldFilterOptionService;
    @Resource
    private TAssetFieldFilterOptionItemService assetFieldFilterOptionItemService;

    @Override
    public PageInfo findAssetTemplateFieldList(int page, int perPage) {
        return assetFieldFilterService.findAssetTemplateFieldList(page, perPage);
    }

    @Override
    public Object detailAssetTemplateField(TAssetFieldFilterReq req) {
        return assetFieldFilterService.detailAssetTemplateField(copyTAssetFieldFilter(req));
    }

    @Override
    public int addAssetTemplateField(TAssetFieldFilterReq req) {
        return assetFieldFilterService.addAssetTemplateField(copyTAssetFieldFilter(req));
    }

    @Override
    public int editAssetTemplateField(TAssetFieldFilterReq req) {
        return assetFieldFilterService.editAssetTemplateField(copyTAssetFieldFilter(req));
    }

    @Override
    public int addAssetTemplateFieldOption(TAssetFieldFilterOptionReq req) {
        return assetFieldFilterOptionService.addAssetTemplateFieldOption(copyTAssetFieldFilterOption(req));
    }

    @Override
    public int editAssetTemplateFieldOption(TAssetFieldFilterOptionReq req) {
        return assetFieldFilterOptionService.editAssetTemplateFieldOption(copyTAssetFieldFilterOption(req));
    }

    @Override
    public int editWeightAssetTemplateFieldOption(TAssetFieldFilterOptionReq req) {
        return 0;
    }

    @Override
    public int deleteAssetTemplateFieldOption(TAssetFieldFilterOptionReq req) {
        return assetFieldFilterOptionService.deleteAssetTemplateFieldOption(copyTAssetFieldFilterOption(req));
    }

    @Override
    public int addAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req) {
        return assetFieldFilterOptionItemService.addTAssetFieldFilterOptionItem(copyTAssetFieldFilterOptionItem(req));
    }

    @Override
    public int editAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req) {
        return assetFieldFilterOptionItemService.editTAssetFieldFilterOptionItem(copyTAssetFieldFilterOptionItem(req));
    }

    @Override
    public int deleteAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req) {
        return assetFieldFilterOptionItemService.deleteTAssetFieldFilterOptionItem(copyTAssetFieldFilterOptionItem(req));
    }

    @Override
    public Object detailAssetTemplateFieldOptionItem(TAssetFieldFilterOptionItemReq req) {
        return assetFieldFilterOptionItemService.detailAssetTemplateFieldOptionItem(req.getFilterOptionId(),req.getFilterId());
    }

    public TAssetFieldFilter copyTAssetFieldFilter(TAssetFieldFilterReq req) {
        TAssetFieldFilter tAssetFieldFilter = new TAssetFieldFilter();
        BeanUtils.copyProperties(req, tAssetFieldFilter);
        return tAssetFieldFilter;
    }

    public TAssetFieldFilterOption copyTAssetFieldFilterOption(TAssetFieldFilterOptionReq req) {
        TAssetFieldFilterOption tAssetFieldFilter = new TAssetFieldFilterOption();
        BeanUtils.copyProperties(req, tAssetFieldFilter);
        return tAssetFieldFilter;
    }

    public TAssetFieldFilterOptionItem copyTAssetFieldFilterOptionItem(TAssetFieldFilterOptionItemReq req) {
        TAssetFieldFilterOptionItem tAssetFieldFilter = new TAssetFieldFilterOptionItem();
        BeanUtils.copyProperties(req, tAssetFieldFilter);
        return tAssetFieldFilter;
    }
}
