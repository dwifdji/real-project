
package com._360pai.core.dao.asset.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.AssetTemplateFieldOptionCondition;
import com._360pai.core.model.asset.AssetTemplateFieldOption;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>AssetTemplateFieldOption数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetTemplateFieldOptionMapper
 * @Description: AssetTemplateFieldOption数据层的基础操作
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetTemplateFieldOptionMapper extends BaseMapper<AssetTemplateFieldOption, AssetTemplateFieldOptionCondition> {
    List<Integer> selectIdsForFieldId(@Param("fieldId") Integer fieldId);

    int deleteAssetTemplateFieldOption(@Param("id") Integer id);
}
