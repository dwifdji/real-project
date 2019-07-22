
package com._360pai.core.dao.asset.mapper;

import com._360pai.arch.core.abs.BaseMapper;
import com._360pai.core.condition.asset.TAssetFieldCondition;
import com._360pai.core.dto.TAssetFieldDto;
import com._360pai.core.model.asset.TAssetField;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>TAssetField数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: TAssetFieldMapper
 * @Description: TAssetField数据层的基础操作
 * @date 2018年08月30日 17时10分43秒
 */
@Mapper
public interface TAssetFieldMapper extends BaseMapper<TAssetField, TAssetFieldCondition> {

    List<Map> searchAssetFields(TAssetFieldDto dto);

    TAssetField findUnit(@Param("key") String key);
}
