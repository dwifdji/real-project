package com._360pai.core.service.asset.impl;

import com._360pai.arch.common.enums.ApiCallResult;
import com._360pai.arch.common.utils.Descartes;
import com._360pai.core.condition.asset.*;
import com._360pai.core.dao.asset.*;
import com._360pai.core.dto.AssetCategoryGroupDto;
import com._360pai.core.exception.BusinessException;
import com._360pai.core.model.asset.*;
import com._360pai.core.service.asset.AssetCategoryGroupService;
import com._360pai.core.vo.asset.AssetCategoryGroupVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zxiao
 * @Title: AssetCategoryGroupServiceImpl
 * @ProjectName zeus-parent
 * @Description: 标的类型
 * @date 2018/8/10 11:12
 */
@Service
public class AssetCategoryGroupServiceImpl implements AssetCategoryGroupService {

    @Autowired
    private AssetCategoryGroupDao assetCategoryGroupDao;
    @Autowired
    private AssetTemplateFieldOptionDao assetTemplateFieldOptionDao;
    @Autowired
    private AssetTemplateFieldMappingDao assetTemplateFieldMappingDao;
    @Autowired
    private AssetTemplateFieldDao assetTemplateFieldDao;
    @Autowired
    private AssetCategoryDao assetCategoryDao;
    @Autowired
    private AssetTemplateOptionsResultDao assetTemplateOptionsResultDao;

    @Override
    public PageInfo selectAssetCategoryGroup(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<AssetCategoryGroup> assetCategoryGroups = assetCategoryGroupDao.selectAll();
        return new PageInfo<>(assetCategoryGroups);
    }

    @Override
    public Object selectAllAssetCategoryGroup(AssetCategoryGroupCondition categoryGroupCondition) {
        return assetCategoryGroupDao.selectList(categoryGroupCondition);
    }

    @Override
    public List<AssetCategoryGroup> selectAssetCategoryGroupByCondition(AssetCategoryGroupCondition params) {
        return assetCategoryGroupDao.selectList(params);
    }

    @Override
    public int updateAssetCategoryGroup(AssetCategoryGroup params) {
        AssetCategoryGroup assetCategoryGroup = findById(params);
        if (null == assetCategoryGroup) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的债券类型不存在");
        }
        return assetCategoryGroupDao.updateById(params);
    }

    @Override
    public int insertAssetCateGoryGroup(AssetCategoryGroup params) {
        AssetCategoryGroup assetCategoryGroup = findByName(params);
        if (null != assetCategoryGroup) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "债券类型名称重复");
        }
        if (params.getBusinessType().equals("1")) {
            params.setBusinessType(AssetCategoryGroup.AUCTION);
        } else {
            params.setBusinessType(AssetCategoryGroup.ENROLLING);
        }

        if (params.getDealMode().equals("1")) {
            params.setDealMode(AssetCategoryGroup.SELL);
        } else {
            params.setDealMode(AssetCategoryGroup.SERVICE);
        }
        return assetCategoryGroupDao.insert(params);
    }

    @Override
    public AssetCategoryGroupVo selectGroupFields(Integer groupId) {
        //通过id获取到指定的债券类型
        if (null == groupId) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "债券类型不存在");
        }
        AssetCategoryGroupCondition assetCategoryGroupCondition = new AssetCategoryGroupCondition();
        assetCategoryGroupCondition.setId(groupId);
        AssetCategoryGroup assetCategoryGroup = assetCategoryGroupDao.selectFirst(assetCategoryGroupCondition);
        if (null == assetCategoryGroup) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "债券类型不存在");
        }
        //先获取到所有的筛选模板
        List<AssetTemplateField> assetTemplateFields = assetTemplateFieldDao.selectAll();
        //封装模板中的字段
        for (AssetTemplateField assetTemplateField : assetTemplateFields) {
            AssetTemplateFieldOptionCondition condition = new AssetTemplateFieldOptionCondition();
            condition.setFieldId(assetTemplateField.getId());
            List<AssetTemplateFieldOption> assetTemplateFieldOptions = assetTemplateFieldOptionDao.selectList(condition);
            assetTemplateField.setOptions(assetTemplateFieldOptions);

            boolean inMapping = assetTemplateFieldMappingDao.isInMapping(assetCategoryGroup.getId(), assetTemplateField.getId());
            if (inMapping) {
                assetTemplateField.setSelected(true);
            } else {
                assetTemplateField.setSelected(false);
            }
        }

        AssetCategoryGroupVo vo = new AssetCategoryGroupVo();
        vo.setBusinessType(assetCategoryGroup.getBusinessType());
        vo.setDealMode(assetCategoryGroup.getDealMode());
        vo.setEnabled(assetCategoryGroup.getEnabled());
        vo.setFields(assetTemplateFields);
        vo.setId(assetCategoryGroup.getId());
        vo.setName(assetCategoryGroup.getName());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateGroupFields(AssetCategoryGroupDto params) {
        AssetCategoryGroup group = new AssetCategoryGroup();
        group.setId(params.getId());
        AssetCategoryGroup assetCategoryGroup = findById(group);
        if (null == assetCategoryGroup) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改的债券类型不存在");
        }
        Integer[] field_ids = params.getFieldIds();
        //没有设置模板
        if (field_ids.length == 0) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "请设置一个模板筛选器");
        }
        //首先删除映射表中的债券字段 asset_template_field_mapping
        AssetTemplateFieldMappingCondition condition = new AssetTemplateFieldMappingCondition();
        condition.setGroupId(params.getId());
        List<AssetTemplateFieldMapping> assetTemplateFieldMappings = assetTemplateFieldMappingDao.selectList(condition);
        //当不是第一次建立筛选器的时候对比删除和查询数据 保持一致
        if (!assetTemplateFieldMappings.isEmpty()) {
            int i = assetTemplateFieldMappingDao.deleteMapping(params.getId());
            if (i != assetTemplateFieldMappings.size()) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "修改筛选器失败");
            }
        }
        //将改变的值插入映射表
        for (Integer fieldId : field_ids) {
            AssetTemplateFieldCondition condition1 = new AssetTemplateFieldCondition();
            condition1.setId(fieldId);
            List<AssetTemplateField> assetTemplateFields = assetTemplateFieldDao.selectList(condition1);
            if (assetTemplateFields.isEmpty()) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), "保存的模板筛选器不存在");
            }

            AssetTemplateFieldMapping templateFieldMapping = new AssetTemplateFieldMapping();
            templateFieldMapping.setFieldId(fieldId);
            templateFieldMapping.setGroupId(assetCategoryGroup.getId());
            assetTemplateFieldMappingDao.insert(templateFieldMapping);
        }
        //在生成之前删除掉之前该债券字段对应的模板
        AssetTemplateOptionsResultCondition resultCondition = new AssetTemplateOptionsResultCondition();
        resultCondition.setCategoryGroupId(assetCategoryGroup.getId());
        List<AssetTemplateOptionsResult> assetTemplateOptionsResults = assetTemplateOptionsResultDao.selectList(resultCondition);
        if (!assetTemplateOptionsResults.isEmpty()) {
            int i = assetTemplateOptionsResultDao.deleteForGroupId(assetCategoryGroup.getId());
            if (i != assetTemplateOptionsResults.size()) {
                throw new BusinessException(ApiCallResult.FAILURE.getCode(), ApiCallResult.FAILURE.getDesc());
            }
        }
        //将生成的摆列组合模板插入数据库
        List<List<Integer>> dimValue = new ArrayList<>();
        //先查询组合
        for (Integer fieldId : field_ids) {
            List<Integer> integers = assetTemplateFieldOptionDao.selectIdsForFieldId(fieldId);
            dimValue.add(integers);
        }
        List<List<String>> circulateResult = new ArrayList<>();
        Descartes.circulate(dimValue, circulateResult);
        //查询一下是否有默认模板
        Integer DefaultId = assetCategoryDao.selectDefault(assetCategoryGroup.getId());
        for (List<String> list : circulateResult) {
            StringBuilder options1 = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    options1.append(list.get(i));
                } else {
                    options1.append(list.get(i)).append(",");
                }
            }
            AssetTemplateOptionsResult result = new AssetTemplateOptionsResult();
            result.setOptions(options1.toString());
            result.setCategoryGroupId(assetCategoryGroup.getId());
            result.setCurrentCategoryId(DefaultId);
            assetTemplateOptionsResultDao.insert(result);
        }
    }

    @Override
    public PageInfo searchResult(Integer groupId, Integer page, Integer prePage) {
        PageHelper.startPage(page, prePage);
        AssetTemplateOptionsResultCondition condition = new AssetTemplateOptionsResultCondition();
        condition.setCategoryGroupId(groupId);
        List<AssetTemplateOptionsResult> assetTemplateOptionsResults = assetTemplateOptionsResultDao.selectList(condition);
        return new PageInfo<>(assetTemplateOptionsResults);
    }

    @Override
    public PageInfo searchCategoriesByGroupId(Integer groupId, int page, int perPage) {
        PageHelper.startPage(page, perPage);
        AssetCategoryCondition category = new AssetCategoryCondition();
        category.setGroupId(groupId);
        List<AssetCategory> assetCategories = assetCategoryDao.selectList(category);
        return new PageInfo<>(assetCategories);
    }

    @Override
    public void bind(Integer currentCategoryId, Integer resultId) {
        AssetTemplateOptionsResultCondition condition = new AssetTemplateOptionsResultCondition();
        condition.setId(resultId);
        AssetTemplateOptionsResult result = assetTemplateOptionsResultDao.selectFirst(condition);
        if (result == null) {
            throw new BusinessException(ApiCallResult.FAILURE.getCode(), "模板不存在");
        }
        result.setCurrentCategoryId(currentCategoryId);
        assetTemplateOptionsResultDao.updateById(result);
    }

    public AssetCategoryGroup findByName(AssetCategoryGroup params) {
        AssetCategoryGroupCondition categoryGroupCondition = new AssetCategoryGroupCondition();
        categoryGroupCondition.setName(params.getName());
        return assetCategoryGroupDao.selectFirst(categoryGroupCondition);
    }

    public AssetCategoryGroup findById(AssetCategoryGroup params) {
        AssetCategoryGroupCondition categoryGroupCondition = new AssetCategoryGroupCondition();
        categoryGroupCondition.setId(params.getId());
        return assetCategoryGroupDao.selectFirst(categoryGroupCondition);
    }

    @Override
    public AssetCategoryGroup getById(Integer groupId) {
        AssetCategoryGroupCondition categoryGroupCondition = new AssetCategoryGroupCondition();
        categoryGroupCondition.setId(groupId);
        return assetCategoryGroupDao.selectFirst(categoryGroupCondition);
    }
}
