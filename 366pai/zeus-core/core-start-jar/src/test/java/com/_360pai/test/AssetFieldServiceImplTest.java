package com._360pai.test;

import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.model.asset.AssetCategoryGroup;
import com._360pai.core.service.asset.AssetCategoryGroupService;
import com._360pai.core.service.asset.AssetFieldService;
import com._360pai.core.vo.asset.AssetCategoryGroupVo;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.junit.Test;

import javax.annotation.Resource;

public class AssetFieldServiceImplTest extends TestBase {

    @Resource
    private AssetFieldService assetFieldService;
    @Resource
    private AssetCategoryGroupService assetCategoryGroupService;

    @Test
    public void selectAssetFieldAndGroup() {
        AssetFieldDto dto = new AssetFieldDto();
        PageInfo pageInfo = assetFieldService.selectAssetFieldAndGroup(dto, 1, 10);
        System.out.println("assetFieldVos = " + JSON.toJSONString(pageInfo));
    }

    @Test
    public void selectGroupOptions() {
        AssetCategoryGroup assetCategoryGroup = new AssetCategoryGroup();
        assetCategoryGroup.setId(1);
        AssetCategoryGroupVo vo = (AssetCategoryGroupVo) assetCategoryGroupService.selectGroupFields(assetCategoryGroup.getId());
        System.out.println("vo = " + JSON.toJSONString(vo));
    }
}