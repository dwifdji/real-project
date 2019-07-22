
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.AssetPropertyCondition;
import com._360pai.core.model.asset.AssetProperty;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>AssetProperty数据层的基础操作</p>
 * @ClassName: AssetPropertyMapper
 * @Description: AssetProperty数据层的基础操作
 * @author Generator
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetPropertyMapper extends BaseMapper<AssetProperty, AssetPropertyCondition>{

    List<AssetProperty> getProperties();
}
