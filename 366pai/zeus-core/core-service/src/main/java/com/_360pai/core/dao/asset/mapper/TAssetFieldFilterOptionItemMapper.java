
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionItemCondition;
import com._360pai.core.model.asset.TAssetFieldFilterOptionItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TAssetFieldFilterOptionItem数据层的基础操作</p>
 * @ClassName: TAssetFieldFilterOptionItemMapper
 * @Description: TAssetFieldFilterOptionItem数据层的基础操作
 * @author Generator
 * @date 2018年08月30日 17时10分43秒
 */
@Mapper
public interface TAssetFieldFilterOptionItemMapper extends BaseMapper<TAssetFieldFilterOptionItem, TAssetFieldFilterOptionItemCondition>{

    int deleteTAssetFieldFilterOptionItem(@Param("paramsId") Integer id);
}
