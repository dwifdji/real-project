
package com._360pai.core.dao.asset.mapper;

import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.TAssetPropertyActivityCondition;
import com._360pai.core.model.asset.TAssetPropertyActivity;
import com._360pai.arch.core.abs.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetPropertyActivity数据层的基础操作</p>
 * @ClassName: TAssetPropertyActivityMapper
 * @Description: TAssetPropertyActivity数据层的基础操作
 * @author Generator
 * @date 2018年09月18日 20时18分03秒
 */
@Mapper
public interface TAssetPropertyActivityMapper extends BaseMapper<TAssetPropertyActivity, TAssetPropertyActivityCondition>{

    List<Map> propertySearch(@Param("assetPropertyId") Integer assetPropertyId);
}
