
package com._360pai.core.dao.asset.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.AssetCategoryCondition;
import com._360pai.core.model.asset.AssetCategory;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>AssetCategory数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetCategoryMapper
 * @Description: AssetCategory数据层的基础操作
 * @date 2018年08月10日 17时37分13秒
 */
@Mapper
public interface AssetCategoryMapper extends BaseMapper<AssetCategory, AssetCategoryCondition> {

    Integer selectDefault(@Param("groupId") Integer id);
}
