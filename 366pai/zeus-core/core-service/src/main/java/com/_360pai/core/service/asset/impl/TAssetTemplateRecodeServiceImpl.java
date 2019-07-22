package com._360pai.core.service.asset.impl;

import com._360pai.core.condition.asset.TAssetTemplateRecodeCondition;
import com._360pai.core.dao.asset.TAssetTemplateRecodeDao;
import com._360pai.core.model.asset.TAssetTemplateRecode;
import com._360pai.core.service.asset.TAssetTemplateRecodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zxiao
 * @Title: TAssetTemplateRecodeServiceImpl
 * @ProjectName zeus-parent
 * @Description:
 * @date 2018/9/13 15:29
 */
@Service
public class TAssetTemplateRecodeServiceImpl implements TAssetTemplateRecodeService {

    @Resource
    private TAssetTemplateRecodeDao tAssetTemplateRecodeDao;

    @Override
    public String addRecode(TAssetTemplateRecode recode) {
        return getCode(recode);
    }

    @Override
    public int addFilter(TAssetTemplateRecode recode) {
        TAssetTemplateRecodeCondition condition = new TAssetTemplateRecodeCondition();
        condition.setVersion(recode.getVersion());
        condition.setFilterId(recode.getFilterId());
        condition.setGroupId(recode.getGroupId());
        TAssetTemplateRecode recode1 = tAssetTemplateRecodeDao.selectOneResult(condition);
        if (recode1 == null) {
            return tAssetTemplateRecodeDao.insert(recode);
        } else {
            recode1.setFilterOptionId(recode.getFilterOptionId());
            recode1.setFilterOptionItemId(recode.getFilterOptionItemId());
            recode1.setFilterOptionItemDataId(recode.getFilterOptionItemDataId());
            return tAssetTemplateRecodeDao.updateById(recode1);
        }
    }

    public String getCode(TAssetTemplateRecode recode) {
        TAssetTemplateRecodeCondition condition = new TAssetTemplateRecodeCondition();
        condition.setVersion(recode.getVersion());
        condition.setModelId(recode.getModelId());
        TAssetTemplateRecode recode2 = tAssetTemplateRecodeDao.selectOneResult(condition);
        if (null == recode2) {
            String modelTitle = recode.getModelTitle();
            String modelOptionTitle = recode.getModelOptionTitle();
            //添加的是一级模块
            String version = "";
            if (StringUtils.isNotBlank(modelTitle) && StringUtils.isBlank(modelOptionTitle)) {
                version = modelTitle + "-" + 1 + "-" + 0;
                recode.setVersion(version);
                tAssetTemplateRecodeDao.insert(recode);
            }
            return version;
        } else {
            String versionArr = recode2.getVersion();
            String modelOptionTitle = recode.getModelOptionTitle();
            String version;
            //添加二级模块的version
            if (StringUtils.isNotBlank(modelOptionTitle)) {
                String[] split = versionArr.split("-");
                String modelTitle = split[0];
                String modelIdStr = split[1];
                String modelOptionStr = split[2];
                int modelId = Integer.parseInt(modelIdStr);
                int modelOptionId = Integer.parseInt(modelOptionStr) + 1; //二级序号递增
                version = modelTitle + modelOptionTitle + "-" + modelId + "-" + modelOptionId;
                recode.setVersion(version);
                tAssetTemplateRecodeDao.insert(recode);
            } else {
                String[] split = versionArr.split("-");
                String modelTitle = split[0];
                String modelIdStr = split[1];
                String modelOptionStr = split[2];
                int modelId = Integer.parseInt(modelIdStr) + 1; //一级序号递增
                int modelOptionId = Integer.parseInt(modelOptionStr);
                version = modelTitle + "-" + modelId + "-" + modelOptionId;
                recode.setVersion(version);
                tAssetTemplateRecodeDao.insert(recode);
            }
            return version;
        }
    }

    public List<TAssetTemplateRecode> findTAssetTemplateRecodeByPartyIdAndTempIdAndmodeId(TAssetTemplateRecode recode) {
        TAssetTemplateRecodeCondition condition = new TAssetTemplateRecodeCondition();
        condition.setPartyId(recode.getPartyId());
        condition.setTemplateId(recode.getTemplateId());
        condition.setModelId(recode.getModelId());
        return tAssetTemplateRecodeDao.selectList(condition);
    }
}
