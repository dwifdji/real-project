package com._360pai.core.provider.asset;

import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.facade.asset.AssetFieldFacade;
import com._360pai.core.facade.asset.req.AssetFieldReq;
import com._360pai.core.model.asset.AssetField;
import com._360pai.core.service.asset.AssetFieldService;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zxiao
 * @Title: AssetFieldProvider
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/8/16 16:13
 */
@Component
@Service(version = "1.0.0")
public class AssetFieldProvider implements AssetFieldFacade {

    @Autowired
    private AssetFieldService assetFieldService;

    @Override
    public int insert(AssetFieldReq req) {
        AssetField field = new AssetField();
        BeanUtils.copyProperties(req, field);
        return assetFieldService.insertAssetField(field);
    }

    @Override
    public Object updateAssetField(AssetFieldReq req) {
        AssetField field = new AssetField();
        BeanUtils.copyProperties(req, field);
        return assetFieldService.updateAssetField(field);
    }

    @Override
    public PageInfo searchAssetFields(AssetFieldReq assetFieldReq) {
        AssetFieldDto dto = new AssetFieldDto();
        BeanUtils.copyProperties(assetFieldReq, dto);
        return assetFieldService.selectAssetFieldAndGroup(dto,assetFieldReq.getPage(),assetFieldReq.getPerPage());
    }
}
