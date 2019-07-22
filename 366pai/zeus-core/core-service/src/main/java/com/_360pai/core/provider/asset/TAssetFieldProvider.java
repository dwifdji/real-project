package com._360pai.core.provider.asset;

import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.facade.asset.TAssetFieldFacade;
import com._360pai.core.facade.asset.req.TAssetFieldReq;
import com._360pai.core.model.asset.TAssetField;
import com._360pai.core.service.asset.TAssetFieldService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: TAssetFieldProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/4 16:10
 */
@Component
@Service(version = "1.0.0")
public class TAssetFieldProvider implements TAssetFieldFacade {

    @Autowired
    private TAssetFieldService tAssetFieldService;

    @Override
    public int insert(TAssetFieldReq req) {
        return tAssetFieldService.insert(copyTAssetField(req));
    }

    @Override
    public int updateAssetField(TAssetFieldReq req) {
        return tAssetFieldService.updateAssetField(copyTAssetField(req));
    }

    @Override
    public PageInfo searchAssetFields(TAssetFieldReq assetFieldReq) {
        TAssetFieldDto dto = new TAssetFieldDto();
        BeanUtils.copyProperties(assetFieldReq,dto);
        return tAssetFieldService.searchAssetFields(dto,assetFieldReq.getPage(),assetFieldReq.getPerPage());
    }

    @Override
    public Object findAssetFieldsByGroupId(TAssetFieldReq req) {
        return tAssetFieldService.findAssetFieldsByGroupId(req.getFieldGroupId());
    }

    public TAssetField copyTAssetField(TAssetFieldReq req) {
        TAssetField tAssetField = new TAssetField();
        BeanUtils.copyProperties(req, tAssetField);
        return tAssetField;
    }
}
