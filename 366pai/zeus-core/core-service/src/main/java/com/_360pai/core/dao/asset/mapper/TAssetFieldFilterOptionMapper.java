
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldFilterOptionCondition;
import com._360pai.core.model.asset.TAssetFieldFilterOption;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>TAssetFieldFilterOption数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TAssetFieldFilterOptionMapper
 * @Description: TAssetFieldFilterOption数据层的基础操作
 * @date 2018年08月30日 17时10分43秒
 */
@Mapper
public interface TAssetFieldFilterOptionMapper extends BaseMapper<TAssetFieldFilterOption, TAssetFieldFilterOptionCondition> {

    int deleteAssetTemplateFieldOption(@Param("paramsId") Integer id);
}
