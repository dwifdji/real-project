
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.AssetTemplateFieldMappingCondition;
import com._360pai.core.model.asset.AssetTemplateFieldMapping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>AssetTemplateFieldMapping数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateFieldMappingMapper
 * @Description: AssetTemplateFieldMapping数据层的基础操作
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetTemplateFieldMappingMapper extends BaseMapper<AssetTemplateFieldMapping, AssetTemplateFieldMappingCondition> {
    Boolean isInMapping(@Param("groupId") Integer groupId, @Param("fieldId") Integer fieldId);

    int deleteMapping(@Param("groupId") Integer groupId);
}
