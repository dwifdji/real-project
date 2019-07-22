package com._360pai.core.service.asset;

import com._360pai.core.condition.asset.AssetCategoryGroupCondition;
import com._360pai.core.dto.AssetCategoryGroupDto;
import com._360pai.core.model.asset.AssetCategoryGroup;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AssetCategoryGroupService {

    /**
     * 功能描述: 查询所有债券类型 分页类型
     *
     * @param:
     * @return:
     * @author: zxiao
     * @date: 2018/8/10 11:19
     */
    PageInfo selectAssetCategoryGroup(Integer pageNum, Integer pageSize);

    /**
     * 功能描述: 查询所有债券类型 分页类型
     *
     * @param:
     * @return:
     * @auther: zxiao
     * @date: 2018/8/10 11:19
     * @param categoryGroupCondition
     */
    Object selectAllAssetCategoryGroup(AssetCategoryGroupCondition categoryGroupCondition);

    /**
     * 功能描述:
     *
     * @param:
     * @return:
     * @author:
     * @date: 2018/8/10
     */
    List<AssetCategoryGroup> selectAssetCategoryGroupByCondition(AssetCategoryGroupCondition par);

    /**
     * 功能描述: 新增债券类型
     *
     * @param:
     * @return:
     * @author:
     * @date: 2018/8/13
     */
    int updateAssetCategoryGroup(AssetCategoryGroup params);

    /**
     * 功能描述:修改债券类型
     *
     * @param:
     * @return:
     * @author:
     * @date: 2018/8/13
     */
    int insertAssetCateGoryGroup(AssetCategoryGroup params);

    /**
     * 功能描述: 查询该债券类型下设置的模板筛选器
     *
     * @param:
     * @return:
     * @author:
     * @date: 2018/8/13
     */
    Object selectGroupFields(Integer groupId);

    /**
     * 功能描述:  修改该债券类型设置的模板筛选器 同时在模板文本映射表 asset_template_field_mapping 中进行增加或删除操作 ，同时生成相应的组合序列模板 插入asset_template_options_result
     *
     * @param:
     * @return:
     * @author:
     * @date: 2018/8/13
     */
    void updateGroupFields(AssetCategoryGroupDto params);

    /**
     * 功能描述:  获取该债券类型下的所有生成的模板
     *
     * @param: groupId
     * @param: page
     * @param: prePage
     * @return:
     * @auther: zxiao
     * @date: 2018/8/16 12:42
     */
    PageInfo searchResult(Integer groupId, Integer page, Integer prePage);

    PageInfo searchCategoriesByGroupId(Integer groupId, int page, int perPage);

    void bind(Integer currentCategoryId, Integer resultId);

    AssetCategoryGroup getById(Integer groupId);

}
