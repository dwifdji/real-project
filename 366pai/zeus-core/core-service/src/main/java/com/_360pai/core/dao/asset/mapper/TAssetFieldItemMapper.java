
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldItemCondition;
import com._360pai.core.model.asset.TAssetFieldItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetFieldItem数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TAssetFieldItemMapper
 * @Description: TAssetFieldItem数据层的基础操作
 * @date 2018年08月30日 17时10分43秒
 */
@Mapper
public interface TAssetFieldItemMapper extends BaseMapper<TAssetFieldItem, TAssetFieldItemCondition> {

    List<Map> findFields(@Param("templateCategoryId") Integer templateCategoryId,
                         @Param("assetFieldGroupId") Integer assetFieldGroupId,
                         @Param("filterOptionId") Integer filterOptionId,
                         @Param("filterOptionItemId") Integer filterOptionItemId,
                         @Param("filterOptionItemDataId") Integer filterOptionItemDataId);

    int deleteTemplateGroupField(@Param("paramsId") Integer id);

    List<Map> findFieldsNotHaveOption(@Param("templateCategoryId") Integer templateCategoryId, @Param("assetFieldGroupId") int fieldGroupId);

    List<Map> findDisplayField(@Param("templateCategoryId") Integer templateCategoryId, @Param("assetFieldGroupId") int fieldGroupId);
}
