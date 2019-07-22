
package com._360pai.core.dao.asset.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.AssetFieldItemCondition;
import com._360pai.core.model.asset.AssetFieldItem;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>AssetFieldItem数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetFieldItemMapper
 * @Description: AssetFieldItem数据层的基础操作
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetFieldItemMapper extends BaseMapper<AssetFieldItem, AssetFieldItemCondition> {
    int deleteItem(@Param("id") Integer id);
}
