
package com._360pai.core.dao.asset.mapper;

import com._360pai.core.dto.AssetFieldDto;
import com._360pai.core.vo.asset.AssetFieldVo;
import org.apache.ibatis.annotations.Mapper;

import com._360pai.core.condition.asset.AssetFieldCondition;
import com._360pai.core.model.asset.AssetField;
import com._360pai.arch.core.abs.BaseMapper;

import java.util.List;

/**
 * <p>AssetField数据层的基础操作</p>
 *
 * @author Generator
 * @ClassName: AssetFieldMapper
 * @Description: AssetField数据层的基础操作
 * @date 2018年08月10日 17时37分14秒
 */
@Mapper
public interface AssetFieldMapper extends BaseMapper<AssetField, AssetFieldCondition> {
    List<AssetFieldVo> selectAssetFieldAndGroup(AssetFieldDto params);
}
